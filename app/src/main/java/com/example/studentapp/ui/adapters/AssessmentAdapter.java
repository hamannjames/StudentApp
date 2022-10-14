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
import com.example.studentapp.entity.Assessment;
import com.example.studentapp.helpers.BuildHelper;
import com.example.studentapp.helpers.ToastHelper;
import com.example.studentapp.ui.SingleAssessmentActivity;

import java.util.ArrayList;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private ArrayList<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private Repository repo;

    public AssessmentAdapter(Context context, Repository repo) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.repo = repo;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.term_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment a = mAssessments.get(position);
            holder.title.setText(a.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments != null ? mAssessments.size() : 0;
    }

    public void setmAssessments(ArrayList<Assessment> assessments) {
        this.mAssessments = assessments;
        notifyDataSetChanged();
    }

    public void removeAssessment(Assessment a) {
        this.mAssessments.remove(a);
        notifyDataSetChanged();
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private Button button;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.courseTitle);
            this.button = itemView.findViewById(R.id.deleteTerm);

            this.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Assessment a = mAssessments.get(position);
                    Intent intent = new Intent(context, SingleAssessmentActivity.class);
                    intent.putExtra("assessment", a);
                    BuildHelper.addNewTaskFlag(intent);
                    context.startActivity(intent);
                }
            });

            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Assessment a = mAssessments.get(position);
                    repo.deleteAssessment(a);
                    ToastHelper.createAndShowTextToast(context, "Assessment " + a.getTitle() + " was deleted", Toast.LENGTH_LONG);
                    removeAssessment(a);
                }
            });
        }
    }
}
