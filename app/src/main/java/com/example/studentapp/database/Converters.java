package com.example.studentapp.database;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converters {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    @TypeConverter
    public static LocalDate fromString(String date) {
        return date == null ? null : LocalDate.parse(date, formatter);
    }

    @TypeConverter
    public static String toString(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }
}
