package com.example.studentapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Course;
import com.example.studentapp.helpers.BuildHelper;
import com.example.studentapp.helpers.ToastHelper;
import com.example.studentapp.ui.SingleCourseActivity;
import com.example.studentapp.ui.SingleTermActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private Repository repo;

    public CourseAdapter(Context context, Repository repo) {
        this.context = context;
        this.repo = repo;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.term_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (courses == null) {
            holder.courseTitle.setText("No courses set");
        } else {
            Course course = courses.get(position);
            holder.courseTitle.setText(course.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return courses != null ? courses.size() : 0;
    }

    public void setCourses(List courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public void deleteCourse(Course course) {
        repo.deleteCourse(course);
        this.courses.remove(course);
        ToastHelper.createAndShowTextToast(context, "Course " + course.getTitle() + " was deleted", Toast.LENGTH_LONG);
        notifyDataSetChanged();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTitle;
        private Button button;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.courseTitle);
            button = itemView.findViewById(R.id.deleteTerm);

            courseTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Course course = courses.get(position);
                    Intent intent = new Intent(context, SingleCourseActivity.class);
                    intent.putExtra("course", course);
                    BuildHelper.addNewTaskFlag(intent);
                    context.startActivity(intent);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Course course = courses.get(position);
                    deleteCourse(course);
                }
            });
        }
    }
}
