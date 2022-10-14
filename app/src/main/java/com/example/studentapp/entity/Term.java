package com.example.studentapp.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.studentapp.database.Converters;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(tableName = "terms")
@TypeConverters({Converters.class})
public class Term implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long termId;
    private LocalDate start;
    private LocalDate end;
    private String title;

    public Term(LocalDate start, LocalDate end, String title) {
        this.start = start;
        this.end = end;
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long id) {
        this.termId = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
