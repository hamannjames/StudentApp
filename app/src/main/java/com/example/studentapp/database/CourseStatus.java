package com.example.studentapp.database;

public enum CourseStatus {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    DROPPED("Dropped"),
    PLAN_TO_TAKE("Plan to Take");

    private String friendlyName;

    private CourseStatus(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString() {
        return this.friendlyName;
    }

    public static CourseStatus fromString(String friendlyName) {
        if (friendlyName == null || friendlyName.equals("")) {
            throw new IllegalArgumentException("Bad friendly name for course status " + friendlyName);
        }

        for(CourseStatus cs : CourseStatus.values()) {
            if(friendlyName.equals(cs.toString())) {
                return cs;
            }
        }

        throw new IllegalArgumentException("Bad friendly name for course status " + friendlyName);
    }
}
