package com.example.studentapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapp.R;
import com.example.studentapp.database.CourseStatus;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Course;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    public static int notificationNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repo = new Repository(getApplication());
    }

    public void enterApp(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}