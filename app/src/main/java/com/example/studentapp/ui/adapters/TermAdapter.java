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
import com.example.studentapp.database.Converters;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.Term;
import com.example.studentapp.helpers.BuildHelper;
import com.example.studentapp.helpers.ToastHelper;
import com.example.studentapp.helpers.ValidationHelper;
import com.example.studentapp.ui.SingleTermActivity;
import com.example.studentapp.ui.TermActivity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> terms;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Repository repo;

    public TermAdapter(Context context, Repository repo) {
        this.context = context;
        this.repo = repo;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (terms != null) {
            Term current = terms.get(position);
            holder.termTitle.setText(current.getTitle());
        } else {
            holder.termTitle.setText("No product name");
        }
    }

    @Override
    public int getItemCount() {
        return terms != null ? terms.size() : 0;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public void deleteTerm(Term term) {
        if (!ValidationHelper.canDeleteTerm(context, term, repo)) {
            return;
        }
        repo.deleteTerm(term);
        this.terms.remove(term);
        notifyDataSetChanged();
        ToastHelper.createAndShowTextToast(context, "Term " + term.getTitle() + " was deleted", Toast.LENGTH_SHORT);
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termTitle;
        private final Button deleteButton;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);

            termTitle = itemView.findViewById(R.id.courseTitle);
            deleteButton = itemView.findViewById(R.id.deleteTerm);

            termTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = terms.get(position);
                    Intent intent = new Intent(context, SingleTermActivity.class);
                    intent.putExtra("term", current);
                    BuildHelper.addNewTaskFlag(intent);
                    context.startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = terms.get(position);
                    deleteTerm(current);
                }
            });
        }
    }
}
