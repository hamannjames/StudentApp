package com.example.studentapp.database;

public enum AssessmentType {
    PERFORMANCE("Performance"),
    OBJECTIVE("Objective");

    private String friendlyName;

    private AssessmentType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString() {
        return this.friendlyName;
    }

    public static AssessmentType fromString(String friendlyName) {
        if (friendlyName == null || friendlyName.equals("")) {
            throw new IllegalArgumentException("No Assessment type with name of " + friendlyName);
        }

        for(AssessmentType at : AssessmentType.values()) {
            if (friendlyName.equals(at.toString())) {
                return at;
            }
        }

        throw new IllegalArgumentException("No Assessment type with name of " + friendlyName);
    }
}
