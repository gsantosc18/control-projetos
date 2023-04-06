package com.gedalias.controledeprojeto.view;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static java.util.Optional.ofNullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.persistence.database.ProjectDatabase;
import com.gedalias.controledeprojeto.service.ServiceFactory;
import com.gedalias.controledeprojeto.service.TaskService;
import com.gedalias.controledeprojeto.service.impl.ServiceFactoryImpl;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskViewActivity extends BaseActivity {

    private static final int DELETE_TASK_LIST = R.id.deleteTaskList;
    private static final int EDIT_TASK_LIST = R.id.editTaskList;
    private static final int EDIT_TASK_CODE = 1;
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

    private TaskService taskService;
    private Task task;

    private TextView name;
    private TextView description;
    private TextView status;
    private TextView createdAt;
    private TextView updatedAt;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        taskService = new ServiceFactoryImpl(this).createTaskService();

        final Bundle extras = this.getIntent().getExtras();
        final String projectName = extras.getString("projectName");
        task = (Task) extras.getSerializable("task");

        setTitle(String.format("%s - %s", projectName, task.getName()));

        name = findViewById(R.id.taskNameView);
        description = findViewById(R.id.taskDescriptionView);
        status = findViewById(R.id.taskStatusView);
        createdAt = findViewById(R.id.taskCreatedAtView);
        updatedAt = findViewById(R.id.taskUpdatedAtView);

        setValues(task);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case EDIT_TASK_CODE:
                editTaskPerformOnResult(resultCode, data); break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void editTaskPerformOnResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                final Task task = (Task) data.getSerializableExtra("task");
                Objects.requireNonNull(task, "The task can not be empty");
                taskService.update(task);
                setValues(task);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int option = item.getItemId();
        switch (option) {
            case DELETE_TASK_LIST:
                final Intent intent = new Intent();
                intent.putExtra("action", "delete");
                intent.putExtra("taskId", task.getId());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case EDIT_TASK_LIST:
                final Intent editTaskIntent = new Intent(this, CreateTaskActivity.class);
                editTaskIntent.putExtra("task", task);
                startActivityForResult(editTaskIntent, EDIT_TASK_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setValues(Task task) {
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
}