package com.gedalias.controledeprojeto.view;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static java.util.Optional.ofNullable;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.domain.Task;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskViewActivity extends BaseActivity {

    private final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .appendValue(DAY_OF_MONTH,2)
            .appendLiteral("/")
            .appendValue(MONTH_OF_YEAR,2)
            .appendLiteral("/")
            .appendValue(YEAR,4)
            .appendLiteral(" ")
            .appendValue(HOUR_OF_DAY,2)
            .appendLiteral(":")
            .appendValue(MINUTE_OF_HOUR,2)
            .appendLiteral(":")
            .appendValue(SECOND_OF_MINUTE,2)
            .toFormatter();

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        final Bundle extras = this.getIntent().getExtras();
        final String projectName = extras.getString("projectName");
        final Task task = (Task) extras.getSerializable("task");

        setTitle(String.format("%s - %s", projectName, task.getName()));

        final TextView name = findViewById(R.id.taskNameView);
        final TextView description = findViewById(R.id.taskDescriptionView);
        final TextView status = findViewById(R.id.taskStatusView);
        final TextView createdAt = findViewById(R.id.taskCreatedAtView);
        final TextView updatedAt = findViewById(R.id.taskUpdatedAtView);

        name.setText(task.getName());
        description.setText(task.getDescription());
        status.setText(getString(task.getStatus().value));


        ofNullable(task.getCreatedAt())
            .ifPresent(
                c -> createdAt.setText(c.format(dateFormat))
            );
        ofNullable(task.getUpdatedAt())
            .ifPresent(
                u -> updatedAt.setText(u.format(dateFormat))
            );
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}