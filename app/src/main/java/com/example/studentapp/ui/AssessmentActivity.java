package com.example.studentapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapp.R;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.ui.adapters.AssessmentAdapter;

import java.util.ArrayList;

public class AssessmentActivity extends BaseActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        Repository repo = new Repository(getApplication());
        ArrayList<Assessment> assessments = repo.getAllAssessments();
        RecyclerView recycler = findViewById(R.id.recyclerAssessments);
        AssessmentAdapter adapter = new AssessmentAdapter(this, repo);
        adapter.setmAssessments(assessments);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void actionAddAssessment(View view) {
        Intent intent = new Intent(this, SingleAssessmentActivity.class);
        startActivity(intent);
    }
}