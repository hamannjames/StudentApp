package com.example.studentapp.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.studentapp.entity.CourseNote;

import java.util.List;

@Dao
public abstract class CourseNoteDAO extends BaseDAO<CourseNote> {

    @Query("SELECT * FROM coursenotes")
    public abstract List<CourseNote> getAllCourseNotes();

    @Query("SELECT * FROM coursenotes WHERE courseId = :courseId")
    public abstract List<CourseNote> getCourseNotesByCourse(Long courseId);
}
