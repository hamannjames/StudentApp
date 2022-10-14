package com.example.studentapp.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.database.Converters;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.Term;
import com.example.studentapp.helpers.DateHelper;
import com.example.studentapp.helpers.ToastHelper;
import com.example.studentapp.helpers.ValidationHelper;
import com.example.studentapp.ui.adapters.AddCourseAdapter;
import com.example.studentapp.ui.adapters.CourseAdapter;

import java.time.LocalDate;
import java.util.ArrayList;

public class SingleTermActivity extends SaveDeleteActivity {

    private Term mTerm;
    private TextView attachedCourses;
    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private RecyclerView courseRecycler;
    private Repository repo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_term);
        this.attachedCourses = findViewById(R.id.textViewAttachedCourses);
        this.attachedCourses.setText("No courses attached");
        this.editTextTitle = findViewById(R.id.editTextTitle);
        this.editTextStartDate = findViewById(R.id.editTextStartDate);
        this.editTextEndDate = findViewById(R.id.editTextEndDate);
        this.courseRecycler = findViewById(R.id.courseRecycler);

        repo = new Repository(getApplication());
        mTerm = (Term) getIntent().getSerializableExtra("term");

        if (mTerm != null) {
            this.editTextTitle.setText(mTerm.getTitle());
            this.editTextStartDate.setText(mTerm.getStart().format(Converters.formatter));
            this.editTextEndDate.setText(mTerm.getEnd().format(Converters.formatter));
            this.setCourseRecycler();
        } else {
            LocalDate now = LocalDate.now();
            this.editTextStartDate.setText(now.format(Converters.formatter));
            this.editTextEndDate.setText(now.plusMonths(2).format(Converters.formatter));
        }

        courseRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void actionEditStartDate(View view) {
        DateHelper.createAndShowDateDialog(this, this.editTextStartDate);
    }

    public void actionEditEndDate(View view) {
        DateHelper.createAndShowDateDialog(this, this.editTextEndDate);
    }

    public void actionSave() {
        this.setFields();

        if (!ValidationHelper.validateTerm(this, mTerm)) {
            return;
        }

        if (this.mTerm.getTermId() == null) {
            repo.insert(mTerm);
        } else {
            repo.updateTerm(mTerm);
        }

        ToastHelper.createAndShowTextToast(this, "Term was saved!", Toast.LENGTH_LONG);
        this.setCourseRecycler();
    }

    public void actionDelete() {
        if (mTerm == null || mTerm.getTermId() == null) {
            goBack();
            this.finish();
            return;
        }

        if (!ValidationHelper.canDeleteTerm(this, mTerm, repo)) {
            //ToastHelper.createAndShowTextToast(this, "Please remove all associated courses", Toast.LENGTH_LONG);
            return;
        }

        repo.deleteTerm(mTerm);
        ToastHelper.createAndShowTextToast(this, "Term " + mTerm.getTitle() + " was deleted", Toast.LENGTH_LONG);
        goBack();
        this.finish();
    }

    private void setFields() {
        if (this.mTerm == null) {
            mTerm = new Term(null, null, null);
        }

        mTerm.setTitle(this.editTextTitle.getText().toString());
        mTerm.setStart(LocalDate.parse(this.editTextStartDate.getText(), Converters.formatter));
        mTerm.setEnd(LocalDate.parse(this.editTextEndDate.getText(), Converters.formatter));
    }

    private void setCourseRecycler() {
        AddCourseAdapter courseAdapter = new AddCourseAdapter(repo, this, mTerm.getTermId(), this.attachedCourses);
        courseRecycler.setAdapter(courseAdapter);
        ArrayList<Course> courses = repo.getCoursesForTerm(mTerm);
        courseAdapter.setmCourses(courses);

        this.attachedCourses.setText("");
        String courseString = "";
        for(Course c : courses) {
            if (c.getTermId() != null && c.getTermId().longValue() == mTerm.getTermId().longValue()) {
                courseString += c.getTitle() + ", ";
            }
        }

        if(courseString.length() > 0) {
            courseString = courseString.substring(0, courseString.length() - 2);
        }

        this.attachedCourses.setText(courseString);

    }

    private void goBack() {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }
}