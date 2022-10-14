package com.example.studentapp.ui.adapters;

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
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class AddAssessmentAdapter extends RecyclerView.Adapter<AddAssessmentAdapter.AddAssessmentViewHolder> {

    private Long courseId;
    private List<Assessment> assessments;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Repository repo;
    private TextView aField;

    public AddAssessmentAdapter(Long courseId, Context context, Repository repo, TextView aField) {
        this.courseId = courseId;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.repo = repo;
        this.aField = aField;
    }

    @NonNull
    @Override
    public AddAssessmentAdapter.AddAssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.add_remove_list_item, parent, false);
        return new AddAssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAssessmentAdapter.AddAssessmentViewHolder holder, int position) {
        if (assessments != null) {
            Assessment assessment = assessments.get(position);
            holder.title.setText(assessment.getTitle());
            if (assessment.getCourseId() != null && assessment.getCourseId().longValue() == courseId.longValue()) {
                holder.button.setText("Remove");
            } else {
                holder.button.setText("Add");
            }
        }
    }

    @Override
    public int getItemCount() {
        return assessments != null ? assessments.size() : 0;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    private void modifyTextField() {
        this.aField.setText("");
        String aString = "";
        for(Assessment a : assessments) {
            if (a.getCourseId() != null && a.getCourseId().longValue() == courseId) {
                aString += a.getTitle() + ", ";
            }
        }

        if(aString.length() > 0) {
            aString = aString.substring(0, aString.length() - 2);
        }

        aField.setText(aString);
    }

    public class AddAssessmentViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private Button button;

        public AddAssessmentViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.addRemoveTitle);
            this.button = itemView.findViewById(R.id.buttonAddRemove);

            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Assessment assessment = assessments.get(position);

                    if (assessment.getCourseId() != null && assessment.getCourseId().longValue() == courseId.longValue()) {
                        assessment.setCourseId(null);
                        repo.updateAssessment(assessment);
                        button.setText("Add");
                    } else {
                        assessment.setCourseId(courseId);
                        repo.updateAssessment(assessment);
                        button.setText("Remove");
                    }

                    modifyTextField();
                }
            });
        }
    }
}
