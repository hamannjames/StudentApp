package com.example.studentapp.ui.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Course;

import java.util.List;

public class AddCourseAdapter extends RecyclerView.Adapter<AddCourseAdapter.AddCourseViewHolder> {

    private List<Course> mCourses;
    private Long termId;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Repository repo;
    private TextView courseTextView;

    public AddCourseAdapter(Repository repo, Context context, Long termId, TextView courseTextView) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.termId = termId;
        this.repo = repo;
        this.courseTextView = courseTextView;
    }

    @NonNull
    @Override
    public AddCourseAdapter.AddCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.add_remove_list_item, parent, false);
        return new AddCourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddCourseAdapter.AddCourseViewHolder holder, int position) {
        if (this.mCourses != null) {
            Course course = mCourses.get(position);
            holder.courseTitle.setText(course.getTitle());
            if (course.getTermId() != null && course.getTermId().longValue() == termId.longValue()) {
                holder.courseButton.setText("Remove");
            } else {
                holder.courseButton.setText("Add");
            }
        } else {
            holder.courseTitle.setText("No courses available! Either remove them from another course or add a new one.");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setmCourses(List<Course> courses) {
        this.mCourses = courses;
        notifyDataSetChanged();
    }

    private void modifyTextView() {
        courseTextView.setText("");
        String courseString = "";
        for(Course c : mCourses) {
            if (c.getTermId() != null && c.getTermId().longValue() == termId.longValue()) {
                courseString += c.getTitle() + ", ";
            }
        }

        if(courseString.length() > 0) {
            courseString = courseString.substring(0, courseString.length() - 2);
        }

        this.courseTextView.setText(courseString);
    }

    public class AddCourseViewHolder extends RecyclerView.ViewHolder {
        private TextView courseTitle;
        private Button courseButton;

        public AddCourseViewHolder(@NonNull View itemView) {
            super(itemView);

            this.courseTitle = itemView.findViewById(R.id.addRemoveTitle);
            this.courseButton = itemView.findViewById(R.id.buttonAddRemove);

            this.courseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    if (current.getTermId() != null && current.getTermId().longValue() == termId.longValue()) {
                        current.setTermId(null);
                        courseButton.setText("Add");
                    } else {
                        current.setTermId(termId);
                        courseButton.setText("Remove");
                    }
                    repo.updateCourse(current);
                    modifyTextView();
                }
            });
        }
    }
}
