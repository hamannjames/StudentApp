package com.example.studentapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapp.R;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Course;
import com.example.studentapp.ui.adapters.CourseAdapter;

import java.util.ArrayList;

public class CourseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Repository repo = new Repository(getApplication());
        ArrayList<Course> courses = repo.getAllCourses();
        RecyclerView recycler = findViewById(R.id.activityCourseRecycler);
        CourseAdapter adapter = new CourseAdapter(this, repo);
        adapter.setCourses(courses);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void actionAddCourse(View view) {
        Intent intent = new Intent(this, SingleCourseActivity.class);
        startActivity(intent);
    }
}