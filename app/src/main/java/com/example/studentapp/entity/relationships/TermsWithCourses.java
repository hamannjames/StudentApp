package com.example.studentapp.entity.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.Term;

import java.util.List;

public class TermsWithCourses {
    @Embedded public Term term;
    @Relation(
            parentColumn = "termId",
            entityColumn = "termId"
    )
    public List<Course> courses;
}
