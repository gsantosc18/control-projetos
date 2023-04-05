package com.gedalias.controledeprojeto.domain;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;

import java.util.Arrays;

public enum TaskStatus {
    BACKLOG(R.string.taskstatus_backlog),
    TO_DO(R.string.taskstatus_to_do),
    IN_PROGRESS(R.string.taskstatus_in_progress),
    TEST(R.string.taskstatus_test),
    DONE(R.string.taskstatus_done);

    public final int value;
    TaskStatus(int value) {
        this.value = value;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static TaskStatus findById(String value, Context context) {
        return Arrays.stream(TaskStatus.values())
                .filter(t -> context.getString(t.value).equals(value))
                .findFirst()
                .orElse(null);
    }
}
