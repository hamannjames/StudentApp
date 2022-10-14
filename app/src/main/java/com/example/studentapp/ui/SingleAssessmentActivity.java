package com.example.studentapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.database.AssessmentType;
import com.example.studentapp.database.Converters;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;
import com.example.studentapp.helpers.DateHelper;
import com.example.studentapp.helpers.NotificationHelper;
import com.example.studentapp.helpers.ToastHelper;
import com.example.studentapp.helpers.ValidationHelper;
import com.example.studentapp.ui.receivers.DateReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SingleAssessmentActivity extends AlertActivity {

    private Assessment mAssessment;
    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private Spinner type;
    private Spinner course;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_assessment);

        this.mAssessment = (Assessment) getIntent().getSerializableExtra("assessment");
        this.title = findViewById(R.id.editTextAssessmentTitle);
        this.startDate = findViewById(R.id.editTextAssessmentStartDate);
        this.endDate = findViewById(R.id.editTextAssessmentEndDate);
        this.type = findViewById(R.id.spinnerAssessmentType);
        this.course = findViewById(R.id.spinnerCourseForAssessment);
        this.repo = new Repository(getApplication());

        ArrayList<Course> courses = repo.getAllCourses();
        courses.add(0, new Course(null, null, null, "None", null, null, null, null));
        ArrayAdapter<AssessmentType> typeAdapter = new ArrayAdapter<AssessmentType>(this, android.R.layout.simple_spinner_item, AssessmentType.values());
        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, courses);
        type.setAdapter(typeAdapter);
        course.setAdapter(courseAdapter);

        LocalDate now = LocalDate.now();
        this.startDate.setText(now.format(Converters.formatter));
        this.endDate.setText(now.plusMonths(2).format(Converters.formatter));

        if (mAssessment != null) {
            this.title.setText(mAssessment.getTitle());
            this.startDate.setText(mAssessment.getStart().format(Converters.formatter));
            this.endDate.setText(mAssessment.getEnd().format(Converters.formatter));
            this.type.setSelection(typeAdapter.getPosition(mAssessment.getType()));

            Long selectedCourseId = mAssessment.getCourseId();
            if (selectedCourseId != null) {
                Course selectedCourse = null;
                for(Course course : courses) {
                    if (course.getCourseId() != null && course.getCourseId().longValue() == selectedCourseId.longValue()) {
                        selectedCourse = course;
                        break;
                    }
                }
                this.course.setSelection(courseAdapter.getPosition(selectedCourse));
            }
        }
    }

    @Override
    protected void handleStartAlert() {
        if (mAssessment == null || mAssessment.getAssessmentId() == null) {
            ToastHelper.createAndShowTextToast(this, "Please save assessment first", Toast.LENGTH_SHORT);
            return;
        }

        NotificationHelper.setDateAlert(this, this.startDate, "Your Assessment " + mAssessment.getTitle() + " starts today!");
    }

    @Override
    protected void handleEndAlert() {
        if (mAssessment == null || mAssessment.getAssessmentId() == null) {
            ToastHelper.createAndShowTextToast(this, "Please save assessment first", Toast.LENGTH_SHORT);
            return;
        }

        NotificationHelper.setDateAlert(this, this.endDate, "Your Assessment " + mAssessment.getTitle() + " ends today!");
    }

    @Override
    public void actionSave() {
        this.setFields();

        if (!ValidationHelper.validateAssessment(this, mAssessment)) {
            return;
        }

        if (mAssessment.getAssessmentId() == null) {
            repo.insert(mAssessment);
        } else {
            repo.updateAssessment(mAssessment);
        }

        ToastHelper.createAndShowTextToast(this, "Assessment saved!", Toast.LENGTH_SHORT);
    }

    @Override
    public void actionDelete() {
        if (mAssessment == null || mAssessment.getAssessmentId() == null) {
            goBack();
            this.finish();
            return;
        }

        repo.deleteAssessment(mAssessment);
        ToastHelper.createAndShowTextToast(this, "Assessment " + mAssessment.getTitle() + "deleted", Toast.LENGTH_SHORT);
        goBack();
        this.finish();
    }

    public void actionEditEndDate(View view) {
        DateHelper.createAndShowDateDialog(this, this.endDate);
    }

    public void actionEditStartDate(View view) {
        DateHelper.createAndShowDateDialog(this, this.startDate);
    }

    private void setFields() {
        if (this.mAssessment == null) {
            this.mAssessment = new Assessment(null, null, null, null, null);
        }

        this.mAssessment.setType((AssessmentType) this.type.getSelectedItem());
        this.mAssessment.setTitle(this.title.getText().toString());
        this.mAssessment.setStart(LocalDate.parse(this.startDate.getText().toString(), Converters.formatter));
        this.mAssessment.setEnd(LocalDate.parse(this.endDate.getText().toString(), Converters.formatter));
        this.mAssessment.setCourseId(((Course) this.course.getSelectedItem()).getCourseId());
    }

    private void goBack() {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}