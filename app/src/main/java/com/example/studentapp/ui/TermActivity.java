package com.example.studentapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapp.R;
import com.example.studentapp.database.Repository;
import com.example.studentapp.ui.adapters.TermAdapter;

public class TermActivity extends BaseActivity {

    private RecyclerView termRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        this.termRecycler = findViewById(R.id.termRecycler);
        Repository repo = new Repository(getApplication());
        TermAdapter adapter = new TermAdapter(getApplicationContext(), repo);
        adapter.setTerms(repo.getAllTerms());
        termRecycler.setAdapter(adapter);
        termRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addTerm(View view) {
        Intent intent = new Intent(this, SingleTermActivity.class);
        startActivity(intent);
    }
}