package com.example.studentapp.entity;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.studentapp.database.Converters;
import com.example.studentapp.database.AssessmentType;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(
        tableName = "assessments",
        foreignKeys = {@ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.SET_NULL
        )}
)
@TypeConverters({Converters.class})
public class Assessment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long assessmentId;
    private AssessmentType type;
    private String title;
    private LocalDate start;
    private LocalDate end;
    private Long courseId;

    public Assessment(AssessmentType type, String title, LocalDate start, LocalDate end, Long courseId) {
        this.type = type;
        this.title = title;
        this.start = start;
        this.end = end;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public AssessmentType getType() {
        return type;
    }

    public void setType(AssessmentType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
