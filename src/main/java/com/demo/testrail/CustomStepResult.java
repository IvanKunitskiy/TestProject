package com.demo.testrail;

import java.io.UnsupportedEncodingException;

public class CustomStepResult {

    private String actual;
    private String expected;
    private int status_id = 5;

    public CustomStepResult() {
    }

    public CustomStepResult(String actual, String expected) {
        try {
            this.actual = new String(actual.getBytes("Cp1251"));
            this.expected = new String(expected.getBytes("Cp1251"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "CustomStepResult{" +
                "expected='" + expected + '\'' +
                ", actual='" + actual + '\'' +
                ", status_id=" + status_id +
                '}';
    }
}
