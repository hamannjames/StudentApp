package com.example.studentapp.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.studentapp.database.Converters;
import com.example.studentapp.entity.Term;
import com.example.studentapp.entity.relationships.TermsWithCourses;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public abstract class TermDAO extends BaseDAO<Term> {

    @Query("SELECT * FROM terms")
    public abstract List<Term> getAllTerms();

    @Transaction
    @Query("SELECT * FROM terms")
    public abstract List<TermsWithCourses> getTermsWithCourses();
}
