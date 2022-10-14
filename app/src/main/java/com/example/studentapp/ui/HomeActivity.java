package com.example.studentapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startTerms(View view) {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }

    public void startCourses(View view) {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void startAssessments(View view) {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}