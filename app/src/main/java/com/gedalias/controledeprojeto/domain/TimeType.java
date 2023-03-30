package com.gedalias.controledeprojeto.domain;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;

import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.N)
public enum TimeType {
    HOUR(R.string.hour), DAY(R.string.day), MOUTH(R.string.month), YEAR(R.string.year);

    private final int timeOption;
    TimeType(int timeOption) {
        this.timeOption = timeOption;
    }

    public int getTimeOption() {
        return timeOption;
    }

    public static TimeType of(String timeOption, Context context) {
        return Arrays.stream(values())
                .filter(timeType -> context.getString(timeType.timeOption).equals(timeOption))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("A medida de tempo não existe"));
    }
}
