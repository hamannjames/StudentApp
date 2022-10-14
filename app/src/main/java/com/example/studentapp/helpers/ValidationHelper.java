package com.example.studentapp.helpers;

import android.content.Context;
import android.widget.Toast;

import com.example.studentapp.database.AssessmentType;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;
import com.example.studentapp.entity.Term;

import java.util.List;

public class ValidationHelper {

    public static boolean validateTerm(Context context, Term term) {
        boolean valid = true;

        if (term.getTitle().equals("") || term.getTitle() == null) {
            ToastHelper.createAndShowTextToast(context, "Please enter a title", Toast.LENGTH_SHORT);
            valid = false;
        }

        if (term.getStart().isAfter(term.getEnd())) {
            ToastHelper.createAndShowTextToast(context, "Start Date cannot come after End Date", Toast.LENGTH_SHORT);
            valid = false;
        }

        return valid;
    }

    public static boolean validateCourse(Context context, Course course) {
        boolean valid = true;

        if (course.getTitle().equals("") || course.getTitle() == null) {
            ToastHelper.createAndShowTextToast(context, "Please enter a title", Toast.LENGTH_SHORT);
            valid = false;
        }

        if (course.getStart().isAfter(course.getEnd())) {
            ToastHelper.createAndShowTextToast(context, "Start Date cannot come after End Date", Toast.LENGTH_SHORT);
            valid = false;
        }

        if (
                course.getInstructorName().equals("") || course.getInstructorName() == null
                || course.getInstructorEmail().equals("") || course.getInstructorEmail() == null
                || course.getInstructorPhone().equals("") || course.getInstructorPhone() == null
        ) {
            ToastHelper.createAndShowTextToast(context, "Please enter all instructor info", Toast.LENGTH_SHORT);
            valid = false;
        }

        return valid;
    }

    public static boolean canDeleteTerm(Context context, Term term, Repository repo) {
        if (term.getTermId() == null) {
            return true;
        }

        List<Course> courses = repo.getCoursesByTerm(term);

        if (courses == null || courses.size() > 0) {
            ToastHelper.createAndShowTextToast(context, "Cannot delete. Term has associated courses", Toast.LENGTH_LONG);
            return false;
        }

        return true;
    }

    public static boolean validateAssessment(Context context, Assessment a) {
        boolean valid = true;

        if (a.getTitle() == null || a.getTitle().equals("")) {
            ToastHelper.createAndShowTextToast(context, "Assessment needs a title", Toast.LENGTH_SHORT);
            valid = false;
        }

        if (a.getStart().isAfter(a.getEnd())) {
            ToastHelper.createAndShowTextToast(context, "Start date cannot be after End date", Toast.LENGTH_SHORT);
            valid = false;
        }

        if (a.getType() == null) {
            ToastHelper.createAndShowTextToast(context, "Assessment needs a type", Toast.LENGTH_SHORT);
            valid = false;
        }

        return valid;
    }
}
