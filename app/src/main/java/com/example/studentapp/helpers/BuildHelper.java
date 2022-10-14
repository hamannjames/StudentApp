package com.example.studentapp.helpers;

import android.content.Intent;
import android.os.Build;

public class BuildHelper {

    public static void addNewTaskFlag(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }
}
