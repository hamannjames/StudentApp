package com.example.studentapp.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.studentapp.R;

public abstract class AlertActivity extends SaveDeleteActivity {

    protected abstract void handleStartAlert();
    protected abstract void handleEndAlert();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_alert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuStartDateAlert:
                this.handleStartAlert();
                return true;
            case R.id.menuEndDateAlert:
                this.handleEndAlert();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
