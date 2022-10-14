package com.example.studentapp.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.studentapp.database.Converters;
import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.relationships.CoursesWithAssessments;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public abstract class CourseDAO extends BaseDAO<Course> {

    @Query("SELECT * FROM courses")
    public abstract List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE termId = :termId")
    public abstract List<Course> getCoursesByTerm(Long termId);

    @Query("SELECT * FROM courses WHERE termId = null OR termId != :termId")
    public abstract List<Course> getCoursesNotInTerm(Long termId);

    @Query("SELECT * FROM courses WHERE termId IS NULL OR termId = :termId")
    public abstract List<Course> getCoursesForTerm(Long termId);

    @Transaction
    @Query("SELECT * FROM courses")
    public abstract List<CoursesWithAssessments> getCoursesWithAssessments();
}
