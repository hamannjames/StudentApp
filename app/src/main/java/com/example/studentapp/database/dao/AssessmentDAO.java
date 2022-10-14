package com.example.studentapp.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.studentapp.database.Converters;
import com.example.studentapp.entity.Assessment;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public abstract class AssessmentDAO extends BaseDAO<Assessment> {

    @Query("SELECT * FROM assessments")
    public abstract List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseId IS NULL OR courseId = :courseId")
    public abstract List<Assessment> getAssessmentsForCourse(Long courseId);
}
