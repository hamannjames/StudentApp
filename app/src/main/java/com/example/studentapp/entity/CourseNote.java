package com.example.studentapp.entity;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "coursenotes",
        foreignKeys = {@ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE
        )}
)
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private Long courseNoteId;
    private String content;
    private String title;
    private Long courseId;

    public CourseNote(String content, Long courseId, String title) {
        this.content = content;
        this.courseId = courseId;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public Long getCourseNoteId() {
        return courseNoteId;
    }

    public void setCourseNoteId(Long courseNoteId) {
        this.courseNoteId = courseNoteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
