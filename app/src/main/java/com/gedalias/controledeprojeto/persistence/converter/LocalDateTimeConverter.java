package com.gedalias.controledeprojeto.persistence.converter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import androidx.room.util.StringUtil;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LocalDateTimeConverter {
    @TypeConverter
    public static LocalDateTime toLocalDateTime(String dateTime) {
        if(dateTime == null || dateTime.isEmpty()) return null;
        return LocalDateTime.parse(dateTime, ISO_LOCAL_DATE_TIME);
    }

    @TypeConverter
    public static String toString(LocalDateTime dateTime) {
        if(dateTime == null) return null;
        return dateTime.format(ISO_LOCAL_DATE_TIME);
    }
}
