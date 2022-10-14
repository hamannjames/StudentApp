package com.example.studentapp.entity;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.studentapp.database.Converters;
import com.example.studentapp.database.CourseStatus;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(
        tableName = "courses",
        foreignKeys = {@ForeignKey(
                entity = Term.class,
                parentColumns = "termId",
                childColumns = "termId",
                onDelete = ForeignKey.SET_NULL
        )}
)
@TypeConverters({Converters.class})
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long courseId;
    private LocalDate start;
    private LocalDate end;
    private CourseStatus status;
    private String title;
    private Long termId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;

    public Course(LocalDate start, LocalDate end, CourseStatus status, String title, Long termId, String instructorName, String instructorEmail, String instructorPhone) {
        this.start = start;
        this.end = end;
        this.status = status;
        this.title = title;
        this.termId = termId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }
}
