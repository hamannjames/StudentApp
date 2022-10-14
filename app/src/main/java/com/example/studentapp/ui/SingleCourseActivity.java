package com.example.studentapp.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentapp.R;
import com.example.studentapp.database.Converters;
import com.example.studentapp.database.CourseStatus;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.CourseNote;
import com.example.studentapp.entity.Term;
import com.example.studentapp.helpers.DateHelper;
import com.example.studentapp.helpers.NotificationHelper;
import com.example.studentapp.helpers.ToastHelper;
import com.example.studentapp.helpers.ValidationHelper;
import com.example.studentapp.ui.adapters.AddAssessmentAdapter;
import com.example.studentapp.ui.adapters.CourseNoteAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SingleCourseActivity extends AlertActivity {

    private Course mCourse;
    private TextView attachedAssessments;
    private EditText title;
    private EditText startDate;
    private EditText endDate;
    private Spinner statusSpinner;
    private EditText instructorName;
    private EditText instructorEmail;
    private EditText instructorPhone;
    private RecyclerView assessmentRecycler;
    private RecyclerView noteRecycler;
    private Spinner termSpinner;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_course);

        this.mCourse = (Course) getIntent().getSerializableExtra("course");
        this.attachedAssessments = findViewById(R.id.textViewAttachedAssessments);
        this.attachedAssessments.setText("No attached assessments");
        this.title = findViewById(R.id.editTextCourseTitle);
        this.startDate= findViewById(R.id.editTextCourseStartDate);
        this.endDate = findViewById(R.id.editTextCourseEndDate);
        this.statusSpinner = findViewById(R.id.spinnerCourseStatus);
        this.instructorName = findViewById(R.id.editTextInstructorName);
        this.instructorEmail = findViewById(R.id.editTextInstructorEmail);
        this.instructorPhone = findViewById(R.id.editTextInstructorPhone);
        this.assessmentRecycler = findViewById(R.id.recyclerCourseAssessments);
        this.noteRecycler = findViewById(R.id.recyclerCourseNotes);
        this.termSpinner = findViewById(R.id.spinnerTermForCourse);
        this.repo = new Repository(getApplication());
        this.noteRecycler.setHasFixedSize(false);

        ArrayList<Term> terms = repo.getAllTerms();
        terms.add(0, new Term(null, null, "None"));
        ArrayAdapter<Term> termAdapter = new ArrayAdapter<Term>(this, android.R.layout.simple_spinner_item, terms);
        termSpinner.setAdapter(termAdapter);

        ArrayAdapter<CourseStatus> statusAdapter = new ArrayAdapter<CourseStatus>(this, android.R.layout.simple_spinner_item, CourseStatus.values());
        statusSpinner.setAdapter(statusAdapter);

        LocalDate now = LocalDate.now();
        this.startDate.setText(now.format(Converters.formatter));
        this.endDate.setText(now.plusMonths(2).format(Converters.formatter));

        if (mCourse != null) {
            this.title.setText(mCourse.getTitle());
            this.startDate.setText(mCourse.getStart().format(Converters.formatter));
            this.endDate.setText(mCourse.getEnd().format(Converters.formatter));
            this.statusSpinner.setSelection(statusAdapter.getPosition(mCourse.getStatus()));
            this.instructorName.setText(mCourse.getInstructorName());
            this.instructorEmail.setText(mCourse.getInstructorEmail());
            this.instructorPhone.setText(mCourse.getInstructorPhone());

            Long termId = mCourse.getTermId();
            if (termId != null) {
                Term selectedTerm = null;
                for(Term term : terms) {
                    if (term.getTermId() != null && term.getTermId().longValue() == termId.longValue()) {
                        selectedTerm = term;
                        break;
                    }
                }
                termSpinner.setSelection(termAdapter.getPosition(selectedTerm));
            }

            this.setAssessmentsAndCourseNotes();
        }

    }

    public void actionEditStartDate(View view) {
        DateHelper.createAndShowDateDialog(this, this.startDate);
    }

    public void actionEditEndDate(View view) {
        DateHelper.createAndShowDateDialog(this, this.endDate);
    }

    public void actionAddNote(View view) {
        if (mCourse == null || this.mCourse.getCourseId() == null) {
            ToastHelper.createAndShowTextToast(this, "Please save course first", Toast.LENGTH_LONG);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewInflated = this.getLayoutInflater().inflate(R.layout.dialog_course_note, null);
        final EditText inputTitle = viewInflated.findViewById(R.id.editTextCourseNoteTitle);
        final EditText inputContent = viewInflated.findViewById(R.id.editTextCourseNoteContent);

        builder.setView(viewInflated)
                .setPositiveButton("Add Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = inputTitle.getText().toString();
                        String content = inputContent.getText().toString();
                        if (title.equals("") || content.equals("")) {
                            ToastHelper.createAndShowTextToast(view.getContext(), "Needs title and content", Toast.LENGTH_SHORT);
                            return;
                        }
                        CourseNote note = new CourseNote(content, mCourse.getCourseId(), title);
                        dialog.dismiss();
                        repo.insert(note);
                        CourseNoteAdapter adapter = (CourseNoteAdapter) noteRecycler.getAdapter();
                        adapter.addCourseNote(note);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }

    @Override
    protected void handleStartAlert() {
        if (mCourse == null || mCourse.getCourseId() == null) {
            ToastHelper.createAndShowTextToast(this, "Please save course first", Toast.LENGTH_SHORT);
            return;
        }

        NotificationHelper.setDateAlert(this, this.startDate, "Your course " + mCourse.getTitle() + " starts today!");
    }

    @Override
    protected void handleEndAlert() {
        if (mCourse == null || mCourse.getCourseId() == null) {
            ToastHelper.createAndShowTextToast(this, "Please save course first", Toast.LENGTH_SHORT);
            return;
        }

        NotificationHelper.setDateAlert(this, this.endDate, "Your course " + mCourse.getTitle() + " ends today!");
    }

    @Override
    public void actionSave() {
        this.setFields();

        if (!ValidationHelper.validateCourse(this, mCourse)) {
            return;
        }

        if (mCourse.getCourseId() == null) {
            repo.insert(mCourse);
        } else {
            repo.updateCourse(mCourse);
        }

        ToastHelper.createAndShowTextToast(this, "Course Saved!", Toast.LENGTH_SHORT);
        this.setAssessmentsAndCourseNotes();
    }

    @Override
    public void actionDelete() {
        if (mCourse == null || mCourse.getCourseId() == null) {
            goBack();
            this.finish();
            return;
        }

        repo.deleteCourse(mCourse);
        ToastHelper.createAndShowTextToast(this, "Course " + mCourse.getTitle() + " was deleted", Toast.LENGTH_LONG);
        goBack();
        this.finish();
    }

    private void setAssessmentsAndCourseNotes() {
        ArrayList<CourseNote> courseNotes = repo.getCourseNotesForCourse(mCourse);
        ArrayList<Assessment> availableAssessments = repo.getAssessmentsForCourse(mCourse);
        AddAssessmentAdapter assessmentAdapter = new AddAssessmentAdapter(mCourse.getCourseId(), this, repo, this.attachedAssessments);
        CourseNoteAdapter courseNoteAdapter = new CourseNoteAdapter(this, repo);
        assessmentAdapter.setAssessments(availableAssessments);
        courseNoteAdapter.setCourseNotes(courseNotes);
        assessmentRecycler.setAdapter(assessmentAdapter);
        assessmentRecycler.setLayoutManager(new LinearLayoutManager(this));
        noteRecycler.setAdapter(courseNoteAdapter);
        noteRecycler.setLayoutManager(new LinearLayoutManager(this));

        this.attachedAssessments.setText("");
        String aString = "";
        for(Assessment a : availableAssessments) {
            if (a.getCourseId() != null && a.getCourseId().longValue() == mCourse.getCourseId().longValue()) {
                aString += a.getTitle() + ", ";
            }
        }

        if(aString.length() > 0) {
            aString = aString.substring(0, aString.length() - 2);
        }

        this.attachedAssessments.setText(aString);
    }

    private void setFields() {
        if (this.mCourse == null) {
            this.mCourse = new Course(null, null, null, null, null, null, null, null);
        }

        this.mCourse.setStart(LocalDate.parse(this.startDate.getText().toString(), Converters.formatter));
        this.mCourse.setEnd(LocalDate.parse(this.endDate.getText().toString(), Converters.formatter));
        this.mCourse.setStatus((CourseStatus) statusSpinner.getSelectedItem());
        this.mCourse.setTitle(this.title.getText().toString());
        this.mCourse.setTermId(((Term) this.termSpinner.getSelectedItem()).getTermId());
        this.mCourse.setInstructorName(this.instructorName.getText().toString());
        this.mCourse.setInstructorEmail(this.instructorEmail.getText().toString());
        this.mCourse.setInstructorPhone(this.instructorPhone.getText().toString());
    }

    private void goBack() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }
}