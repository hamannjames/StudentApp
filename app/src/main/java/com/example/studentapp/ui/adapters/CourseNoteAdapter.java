package com.example.studentapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;
import com.example.studentapp.database.Repository;
import com.example.studentapp.entity.CourseNote;

import java.util.List;

public class CourseNoteAdapter extends RecyclerView.Adapter<CourseNoteAdapter.CourseNoteViewHolder> {

    private List<CourseNote> courseNotes;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private Repository repo;

    public CourseNoteAdapter(Context context, Repository repo) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.repo = repo;
    }

    @NonNull
    @Override
    public CourseNoteAdapter.CourseNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.course_note_list_item, parent, false);
        return new CourseNoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseNoteAdapter.CourseNoteViewHolder holder, int position) {
        if (courseNotes != null) {
            CourseNote note = courseNotes.get(position);
            holder.title.setText(note.getTitle());
        } else {
            holder.title.setText("No Course Notes");
        }
    }

    @Override
    public int getItemCount() {
        return courseNotes != null ? courseNotes.size() : 0;
    }

    public void setCourseNotes(List<CourseNote> courseNotes) {
        this.courseNotes = courseNotes;
        notifyDataSetChanged();
    }

    public void removeCourseNote(CourseNote courseNote) {
        this.courseNotes.remove(courseNote);
        notifyDataSetChanged();
    }

    public void addCourseNote(CourseNote courseNote) {
        this.courseNotes.add(courseNote);
        notifyDataSetChanged();
    }

    public class CourseNoteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private Button shareButton;
        private Button deleteButton;

        public CourseNoteViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.textViewNote);
            this.shareButton = itemView.findViewById(R.id.buttonShareNote);
            this.deleteButton = itemView.findViewById(R.id.buttonDeleteNote);

            this.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    CourseNote note = courseNotes.get(position);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TITLE, "Course Note: " + note.getTitle());
                    intent.putExtra(Intent.EXTRA_TEXT, note.getContent());
                    intent.setType("text/plain");
                    Intent share = Intent.createChooser(intent, null);
                    context.startActivity(share);
                }
            });

            this.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    CourseNote note = courseNotes.get(position);
                    repo.deleteCourseNote(note);
                    removeCourseNote(note);
                }
            });
        }
    }
}
