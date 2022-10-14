package com.example.studentapp.database;

import android.app.Application;

import com.example.studentapp.database.dao.AssessmentDAO;
import com.example.studentapp.database.dao.CourseDAO;
import com.example.studentapp.database.dao.CourseNoteDAO;
import com.example.studentapp.database.dao.TermDAO;
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.CourseNote;
import com.example.studentapp.entity.Term;
import com.example.studentapp.entity.relationships.CoursesWithAssessments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;
    private CourseNoteDAO courseNoteDAO;

    private ArrayList<Term> allTerms;
    private ArrayList<Course> allCourses;
    private ArrayList<Assessment> allAssessments;
    private ArrayList<CoursesWithAssessments> allCoursesWithAssessments;
    private ArrayList<CourseNote> courseNotes;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application app) {
        AppDatabaseBuilder db = AppDatabaseBuilder.getDatabase(app);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        courseNoteDAO = db.courseNoteDAO();
    }

    public void insert(Term term) {
        dbExecutor.execute(() -> {
            term.setTermId(termDAO.insert(term));
        });

        wait(1000);
    }

    public void insert(Course course) {
        dbExecutor.execute(() -> {
            course.setCourseId(courseDAO.insert(course));
        });

        wait(1000);
    }

    public void insert(Assessment assessment) {
        dbExecutor.execute(() -> {
            assessment.setAssessmentId(assessmentDAO.insert(assessment));
        });

        wait(1000);
    }

    public void insert(CourseNote courseNote) {
        dbExecutor.execute(() -> {
            courseNote.setCourseNoteId(courseNoteDAO.insert(courseNote));
        });

        wait(1000);
    }

    public ArrayList<Term> getAllTerms() {
        dbExecutor.execute(() -> {
            allTerms = new ArrayList<>(termDAO.getAllTerms());
        });

        wait(1000);
        return allTerms;
    }

    public ArrayList<Course> getAllCourses() {
        dbExecutor.execute(() -> {
            allCourses = new ArrayList<>(courseDAO.getAllCourses());
        });

        wait(1000);
        return allCourses;
    }

    public ArrayList<Course> getCoursesByTerm(Term term) {
        dbExecutor.execute(() -> {
            allCourses = new ArrayList<>(courseDAO.getCoursesByTerm(term.getTermId()));
        });

        wait(1000);
        return allCourses;
    }

    public ArrayList<CoursesWithAssessments> getCoursesWithAssessments() {
        dbExecutor.execute(() -> {
            allCoursesWithAssessments = new ArrayList<>(courseDAO.getCoursesWithAssessments());
        });

        wait(1000);
        return allCoursesWithAssessments;
    }

    public ArrayList<CourseNote> getCourseNotesForCourse(Course course) {
        dbExecutor.execute(() -> {
            courseNotes = new ArrayList<>(courseNoteDAO.getCourseNotesByCourse(course.getCourseId()));
        });

        wait(1000);
        return courseNotes;
    }

    public void updateCourse(Course course) {
        dbExecutor.execute(() -> {
            courseDAO.update(course);
        });

        wait(1000);
    }

    public void insertTerm(Term term) {
        dbExecutor.execute(() -> {
            term.setTermId(termDAO.insert(term));
        });

        wait(1000);
        this.allTerms.add(term);
    }

    public void updateTerm(Term term) {
        dbExecutor.execute(() -> {
            termDAO.update(term);
        });

        wait(1000);
    }

    public List<Course> getCoursesNotInTerm(Term term) {
        dbExecutor.execute(() -> {
            this.allCourses = new ArrayList<>(courseDAO.getCoursesNotInTerm(term.getTermId()));
        });

        wait(1000);
        return this.allCourses;
    }

    public void deleteTerm(Term term) {
        dbExecutor.execute(() -> {
            termDAO.delete(term);
        });

        wait(1000);
    }

    public void deleteAssessment(Assessment assessment) {
        dbExecutor.execute(() -> {
            assessmentDAO.delete(assessment);
        });

        wait(1000);
    }

    public void updateAssessment(Assessment assessment) {
        dbExecutor.execute(() -> {
            assessmentDAO.update(assessment);
        });

        wait(1000);
    }

    public ArrayList<Assessment> getAllAssessments() {
        dbExecutor.execute(() -> {
            this.allAssessments = new ArrayList<>(assessmentDAO.getAllAssessments());
        });

        wait(1000);
        return this.allAssessments;
    }

    public ArrayList<Assessment> getAssessmentsForCourse(Course course) {
        dbExecutor.execute(() -> {
            this.allAssessments = new ArrayList<>(assessmentDAO.getAssessmentsForCourse(course.getCourseId()));
        });

        wait(1000);
        return this.allAssessments;
    }

    public void deleteCourseNote(CourseNote note) {
        dbExecutor.execute(() -> {
            courseNoteDAO.delete(note);
        });

        wait(500);
    }

    public void deleteCourse(Course course) {
        dbExecutor.execute(() -> {
            courseDAO.delete(course);
        });

        wait(500);
    }

    public ArrayList<Course> getCoursesForTerm(Term term) {
        dbExecutor.execute(() -> {
            this.allCourses = new ArrayList<>(courseDAO.getCoursesForTerm(term.getTermId()));
        });

        wait(1000);
        return this.allCourses;
    }

    private void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
