package com.example.studentapp.entity.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;

import java.util.List;

public class CoursesWithAssessments {

    @Embedded public Course course;
    @Relation(
            parentColumn = "courseId",
            entityColumn = "courseId"
    )
    public List<Assessment> assessments;
}
