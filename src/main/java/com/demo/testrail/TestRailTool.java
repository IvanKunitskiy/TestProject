package com.demo.testrail;

import com.demo.core.utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestRailTool {

    // URL for creating new Run
    private final String CREATE_RUN_URL = "add_run/%s";

    // URL for setting test execution status
    private final String SET_TEST_EXECUTION_STATUS = "add_result_for_case/%s/%s";
    private final String GET_TEST_RESULTS_CASE = "get_results_for_case/%s/%s";
    private final String UPDATE_TEST_RUN = "update_run/%s";
    private final String GET_TESTS = "get_tests/%s";

    //URl for getting project's sections
    private final String GET_PROJECT_SECTIONS_URL = "get_sections/%s/&suite_id=%s";

    //URL for getting section's cases
    private final String GET_CASES_SECTION_BY_ID_URL = "get_cases/1/&suite_id=1&&section_id=%s";

    Map<String, Long> listSections = new HashMap<String, Long>();

    private long runId = Constants.TEST_RAIL_RUN_ID;
    private String runName = Constants.TEST_RAIL_RUN_NAME;

    private APIClient client;

    /**
     * Initialization request (set url, request header, media type)
     */
    private APIClient initRequest() {
        client = new APIClient(Constants.TEST_RAIL_URL);
        client.setUser(Constants.TEST_RAIL_USER);
        client.setPassword(Constants.TEST_RAIL_PASSWORD);

        return client;
    }

    /**
     * The method returns id created Run which was created by method createRun. Default value equal - 0
     *
     * @return id test cycle
     */
    public long getRunId() {
        return runId;
    }

    /**
     * The method returns name of created Run which was created by method createRun. Default value equal - Test Run
     *
     * @return id test cycle
     */
    public String getRunName() {
        return runName;
    }

    /**
     * The method sets status statusExecution to the test with id caseId
     * <p>
     * //     * @param caseId         case id
     *
     * @param statusExecution status execution (1 - PASS, 2 - BLOCKED, 3 - UNTESTED, 4 - RETEST, 5 - FAIL)
     */
    public void setTestExecutionStatus(long idRun, int caseId, int statusExecution, String imageName) {
        initRequest();
        try {

            System.out.println("Screenshot name: " + imageName);
            Map data = new HashMap();
            data.put("status_id", statusExecution);

            JSONObject r = (JSONObject) client.sendPost(String.format(SET_TEST_EXECUTION_STATUS, idRun, caseId), data);

            //attachScreenshot(idRun, caseId, imageName);

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    public void attachScreenshot(long idRun, int caseId, String imageName){
        initRequest();

        int idResult = getLastResultForCase(idRun, caseId);

        File image = new File(System.getProperty("report.dir") +
                File.separator + "html" +
                File.separator + imageName + ".png");
        try {
            JSONObject r = (JSONObject) client.sendPost("add_attachment_to_result/" + idResult, image.getAbsoluteFile().toString());
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }

    }

    public int getLastResultForCase(long idRun, int caseId){
        initRequest();
        int lastResultId = -1;
        try {
            JSONObject r = (JSONObject) client.sendGet(String.format(GET_TEST_RESULTS_CASE, idRun, caseId));

            JSONArray caseResults = (JSONArray) r.get("results");
            JSONObject lastTestResult = (JSONObject) caseResults.get(0);

            lastResultId = Integer.parseInt(lastTestResult.get("id").toString());

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
        System.out.println("ID: " + lastResultId);
        return lastResultId;
    }

    public void setTestExecutionStatus(long idRun, int caseId, int statusExecution, ArrayList<CustomStepResult> customStepResults, String imageName) {

        int i = 1;
        ArrayList<Map> customSteps = new ArrayList<>();

        for (CustomStepResult customStep : customStepResults) {
            Map customStepData = new HashMap();
            customStepData.put("content", String.format("Step %s", i++));
            customStepData.put("expected", customStep.getExpected());
            customStepData.put("actual", customStep.getActual());
            customStepData.put("status_id", customStep.getStatus_id());
            customSteps.add(customStepData);
        }

        initRequest();
        try {

            Map data = new HashMap();
            data.put("status_id", statusExecution);
            data.put("custom_step_results", customSteps);

            JSONObject r = (JSONObject) client.sendPost(String.format(SET_TEST_EXECUTION_STATUS, idRun, caseId), data);

            //attachScreenshot(idRun, caseId, imageName);

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method create Run by name "runName" in project with id is equal projectID
     *
     * @param projectID project id
     */
    public void createRun(int projectID) {

        getProjectSections(projectID, Constants.SUITE_ID);

        runName = String.format("[Automated %s]  %s", Constants.SUITE_NAME,
                Constants.CURRENT_TIME);

        Map createTestRunBody = new HashMap();
        createTestRunBody.put("suite_id", Constants.SUITE_ID);
        createTestRunBody.put("name", runName);
        createTestRunBody.put("assignedto_id", "1");
        createTestRunBody.put("include_all", false);


        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initRequest();
        try {

            JSONObject responseBody = (JSONObject) client.sendPost(String.format(CREATE_RUN_URL, projectID), createTestRunBody);

            runId = (Long) responseBody.get("id");
            Constants.TEST_RAIL_RUN_ID = runId;

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    public void updateTestRun(long caseID) {
        initRequest();
        try {
            JSONArray getTestsByRunId = (JSONArray) client.sendGet(String.format(GET_TESTS, runId));

            ArrayList<Long> listOfTestCases = getListOfTestCasesIDFromTestRun(getTestsByRunId);
            listOfTestCases.add(caseID);

            Map data = new HashMap();
            data.put("include_all", false);
            data.put("case_ids", listOfTestCases);

            JSONObject r = (JSONObject) client.sendPost(String.format(UPDATE_TEST_RUN, runId), data);

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }


    public void getProjectSections(int projectId, int suiteId) {
        initRequest();

        try {
            JSONArray jsonArray = (JSONArray) client.sendGet(String.format(GET_PROJECT_SECTIONS_URL, projectId, suiteId));

            for (Object jsonObject : jsonArray) {
                listSections.put((String) (((JSONObject) jsonObject).get("name")), (Long) (((JSONObject) jsonObject).get("id")));
            }
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Long> getCasesSectionById(long sectionID) {
        initRequest();

        ArrayList<Long> casesId = new ArrayList<Long>();

        try {
            JSONArray jsonArray = (JSONArray) client.sendGet(String.format(GET_CASES_SECTION_BY_ID_URL, sectionID));

            for (Object jsonObject : jsonArray) {
                casesId.add((Long) (((JSONObject) jsonObject).get("id")));
            }
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }

        return casesId;
    }

    private ArrayList<Long> getListOfTestCasesIDFromTestRun(JSONArray array) {
        ArrayList<Long> listOfTestCasesID = new ArrayList<>();

        if (array.size() == 0)
            return new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JSONObject testCase = (JSONObject) array.get(i);
            listOfTestCasesID.add((Long) testCase.get("case_id"));
        }

        return listOfTestCasesID;
    }
}