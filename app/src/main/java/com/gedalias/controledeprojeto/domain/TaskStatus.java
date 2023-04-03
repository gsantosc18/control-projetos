package com.gedalias.controledeprojeto.domain;

import com.gedalias.controledeprojeto.R;

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
}
