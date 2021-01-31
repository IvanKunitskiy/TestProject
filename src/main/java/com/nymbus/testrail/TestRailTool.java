package com.nymbus.testrail;

import com.nymbus.core.utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestRailTool {

    // URL for creating new Run
    private final String CREATE_RUN_URL = "add_run/%s";

    // URL for setting test execution status
    private final String SET_TEST_EXECUTION_STATUS = "add_result_for_case/%s/%s";
    private final String UPDATE_TEST_RUN = "update_run/%s";
    private final String GET_TESTS = "get_tests/%s";

    //URl for getting project's sections
    private final String GET_PROJECT_SECTIONS_URL = "get_sections/%s/&suite_id=%s";

    //URL for getting section's cases
    private final String GET_CASES_SECTION_BY_ID_URL = "get_cases/1/&suite_id=1&&section_id=%s";

    Map<String, Long> listSections = new HashMap<String, Long>();

    //Id of run created in the testrail
    private long idRun = 1153;

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
    public long getIdRun() {
        return idRun;
    }

    /**
     * The method sets status statusExecution to the test with id caseId
     * <p>
     * //     * @param caseId         case id
     *
     * @param statusExecution status execution (1 - PASS, 2 - BLOCKED, 3 - UNTESTED, 4 - RETEST, 5 - FAIL)
     */
    public void setTestExecutionStatus(long idRun, int caseId, int statusExecution) {
        initRequest();
        try {

            Map data = new HashMap();
            data.put("status_id", statusExecution);

            JSONObject r = (JSONObject) client.sendPost(String.format(SET_TEST_EXECUTION_STATUS, idRun, caseId), data);


        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    public void setTestExecutionStatus(long idRun, int caseId, int statusExecution, ArrayList<CustomStepResult> customStepResults) {

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

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method create Run by name "runName" in project with id is equal projectID
     *
     * @param runName   Run name
     * @param projectID project id
     */
    public void createRun(String runName, int projectID) {

        getProjectSections(projectID, Constants.SUITE_ID);

//        ArrayList<Long> listCases = getCasesSectionById(listSections.get(runName));

        Map createTestRunBody = new HashMap();
        createTestRunBody.put("suite_id", Constants.SUITE_ID);
        createTestRunBody.put("name", runName + " (" + Constants.BROWSER + ") ".toUpperCase() + Constants.CURRENT_TIME);
        createTestRunBody.put("assignedto_id", "55");
        createTestRunBody.put("include_all", false);
//        createTestRunBody.put("case_ids", listCases);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initRequest();
        try {

            JSONObject responseBody = (JSONObject) client.sendPost(String.format(CREATE_RUN_URL, projectID), createTestRunBody);

            idRun = (Long) responseBody.get("id");

        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    public void updateTestRun(long caseID){
        initRequest();
        try {

            JSONArray getTestsByRunId = (JSONArray) client.sendGet(String.format(GET_TESTS, idRun));

            ArrayList<Long> listOfTestCases = getListOfTestCasesIDFromTestRun(getTestsByRunId);
            listOfTestCases.add(caseID);

            Map data = new HashMap();
            data.put("include_all", false);
            data.put("case_ids", listOfTestCases);

            JSONObject r = (JSONObject) client.sendPost(String.format(UPDATE_TEST_RUN, idRun), data);

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

    private ArrayList<Long> getListOfTestCasesIDFromTestRun(JSONArray array){
        ArrayList<Long> listOfTestCasesID = new ArrayList<>();

        if(array.size() == 0)
            return new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JSONObject testCase = (JSONObject) array.get(i);
            listOfTestCasesID.add((Long)testCase.get("case_id"));
        }

        return listOfTestCasesID;
    }
}