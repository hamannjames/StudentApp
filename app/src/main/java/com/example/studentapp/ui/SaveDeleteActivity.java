package com.example.studentapp.ui;

import android.view.Menu;
import android.view.MenuItem;

import com.example.studentapp.R;

public abstract class SaveDeleteActivity extends BaseActivity {

    public abstract void actionSave();
    public abstract void actionDelete();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menuSave:
                this.actionSave();
                return true;
            case R.id.menuDelete: {
                this.actionDelete();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
