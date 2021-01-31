package com.nymbus.testrail;


import com.nymbus.core.utils.Constants;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TestListener extends TestListenerAdapter implements IInvokedMethodListener {

    private Map<String, Long> cycleNames = new HashMap<>();
    TestRailTool testRailTool;

//    @Override
//    public void onTestSkipped(ITestResult tr) {
//        super.onTestSkipped(tr);
//        Method method = tr.getMethod().getConstructorOrMethod().getMethod();
//      //TODO adaptation for test rail
//        setUpTestCycle(method, TestResult.BLOCKED);
//    }

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (Constants.SEND_RESULT_TO_TESTRAIL) {
            Method method = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
            setUpTestCycle(method);
            addTestCaseToTestRun(method);
        }
    }

    private void setUpTestCycle(Method method) {
        for (Annotation a : method.getDeclaredAnnotations()) {
            if (a instanceof TestRailIssue) {
                TestRailIssue annotation = (TestRailIssue) a;
                String cycleName = "Regression Q4";
                if (cycleNames.get(cycleName) == null) {
                    testRailTool = new TestRailTool();
//                    testRailTool.createRun(cycleName, Constants.PROJECT_ID);
                    cycleNames.put(cycleName, testRailTool.getIdRun());
                }
            }
        }

    }

    private void addTestCaseToTestRun(Method method){
        for (Annotation a : method.getDeclaredAnnotations()) {
            if (a instanceof TestRailIssue) {
                TestRailIssue annotation = (TestRailIssue) a;
                int caseId = annotation.issueID();
                testRailTool.updateTestRun(caseId);
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (Constants.SEND_RESULT_TO_TESTRAIL) {
            Method method = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod();
            for (Annotation a : method.getDeclaredAnnotations()) {
                if (a instanceof TestRailIssue) {
                    TestRailIssue annotation = (TestRailIssue) a;
                    TestResult executionStatus;
                    switch (iTestResult.getStatus()) {
                        case (ITestResult.SUCCESS):
                            executionStatus = TestResult.PASS;
                            break;
                        case (ITestResult.FAILURE):
                            executionStatus = TestResult.FAIL;
                            break;
                        case (ITestResult.SKIP):
                            executionStatus = TestResult.BLOCKED;
                            break;
                        default:
                            executionStatus = TestResult.UNTESTED;
                            break;
                    }

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (annotation.customResults.size() > 0) {
                        testRailTool.setTestExecutionStatus(testRailTool.getIdRun(), annotation.issueID(), executionStatus.getValue(), annotation.customResults);
                    } else {
                        testRailTool.setTestExecutionStatus(testRailTool.getIdRun(), annotation.issueID(), executionStatus.getValue());
                    }

                }
            }

            TestRailIssue.customResults.clear();
        }
    }

    public enum TestResult {
        PASS(1), BLOCKED(2), UNTESTED(3), RETEST(4), FAIL(5);

        private int value;

        TestResult(int statusExecution) {
            this.value = statusExecution;
        }

        public int getValue() {
            return value;
        }
    }

}
