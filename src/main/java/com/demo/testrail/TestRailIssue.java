package com.demo.testrail;


import com.demo.core.utils.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestRailIssue {

    int issueID();
    int projectID() default Constants.PROJECT_ID;
    ArrayList<CustomStepResult> customResults = new ArrayList<>();

}
