package com.example.studentapp.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.studentapp.database.Converters;

import java.time.LocalDate;

public class DateHelper {

    public static void createAndShowDateDialog(Context context, EditText textField) {
        LocalDate currentStartDate = LocalDate.parse(textField.getText(), Converters.formatter);
        DatePickerDialog dateDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        LocalDate newDate = LocalDate.of(i, i1 + 1, i2);
                        textField.setText(newDate.format(Converters.formatter));
                    }
                },
                currentStartDate.getYear(),
                currentStartDate.getMonthValue() - 1,
                currentStartDate.getDayOfMonth()
        );
        dateDialog.show();
    }
}
