package com.example.studentapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentapp.database.dao.AssessmentDAO;
import com.example.studentapp.database.dao.CourseDAO;
import com.example.studentapp.database.dao.CourseNoteDAO;
import com.example.studentapp.database.dao.TermDAO;
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.CourseNote;
import com.example.studentapp.entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, CourseNote.class}, version = 9, exportSchema = false)
public abstract class AppDatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseNoteDAO courseNoteDAO();

    private static volatile AppDatabaseBuilder INSTANCE;

    static AppDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabaseBuilder.class,
                            "appDatabase"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }

        return INSTANCE;
    }
}
