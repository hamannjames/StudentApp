package com.example.studentapp.helpers;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public static void createAndShowTextToast(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
