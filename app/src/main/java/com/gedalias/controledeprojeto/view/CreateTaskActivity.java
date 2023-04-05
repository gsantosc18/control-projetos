package com.gedalias.controledeprojeto.view;

import static java.util.Objects.requireNonNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.domain.TaskStatus;

public class CreateTaskActivity extends BaseActivity {
    private Integer projectId;

    private TextView name;
    private EditText description;
    private Spinner status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        final Bundle extras = getIntent().getExtras();
        projectId = extras.getInt("projectId");

        requireNonNull(projectId, "Project Id is required");

        setTitle(R.string.create_new_task);

        name = (TextView) findViewById(R.id.taskNameInput);
        description = (EditText) findViewById(R.id.taskDescriptionInput);
        status = (Spinner) findViewById(R.id.taskStatusOptions);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creation_update, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int option = item.getItemId();

        switch (option) {
            case R.id.save_option:
                final String name = this.name.getText().toString();
                final String description = this.description.getText().toString();
                final String selectedStatusOption = (String) this.status.getSelectedItem();
                final TaskStatus taskStatus = TaskStatus.findById(selectedStatusOption, this);

                Log.i("Selected Status", String.valueOf(selectedStatusOption));

                final Task task = new Task(projectId, name, description, taskStatus);
                final Intent intent = new Intent();
                intent.putExtra("task", task);
                intent.putExtra("projectId", projectId);

                setResult(RESULT_OK, intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}