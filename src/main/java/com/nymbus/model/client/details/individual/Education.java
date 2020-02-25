package com.nymbus.model.client.details.individual;

public enum Education {
    ASSOCIATES_DEGREE("Associates Degree"),
    BACHELORS_DEGREE("Bachelors Degree"),
    DOCTORATE("Doctorate"),
    GED("GED"),
    HIGH_SCHOOL_DIPLOMA("High School Diploma"),
    MASTERS_DEGREE("Masters Degree"),
    NO_HIGH_SCHOOL("No High School"),
    TECHNICAL_SCHOOL("Technical School");

    private final String education;

    Education(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }
}
