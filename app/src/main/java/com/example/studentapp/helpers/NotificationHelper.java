package com.example.studentapp.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.example.studentapp.ui.MainActivity;
import com.example.studentapp.ui.receivers.DateReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class NotificationHelper {

    public static void setDateAlert(Context context, EditText dateField, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        Date start = null;
        try {
            start = sdf.parse(dateField.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long trigger = start.getTime();
        Intent intent = new Intent(context, DateReceiver.class);
        intent.putExtra("key", message);
        PendingIntent sender = PendingIntent.getBroadcast(context, MainActivity.notificationNumber++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }
}
