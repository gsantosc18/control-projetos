package com.gedalias.controledeprojeto.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.adapter.TaskAdapter;
import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.service.ServiceFactory;
import com.gedalias.controledeprojeto.service.TaskService;
import com.gedalias.controledeprojeto.service.impl.ServiceFactoryImpl;
import com.gedalias.controledeprojeto.util.AppFactory;
import com.gedalias.controledeprojeto.util.Notification;
import com.gedalias.controledeprojeto.util.impl.AppFactoryImpl;

import java.util.List;
import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskActivity extends BaseActivity {
    private TaskService taskService;
    private TaskAdapter adapter;
    private Notification notification;
    private Integer projectId;
    private String projectName;
    private List<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        final ServiceFactory factory = new ServiceFactoryImpl(this);
        taskService = factory.createTaskService();

        final AppFactory appFactory = new AppFactoryImpl(this);
        notification = appFactory.createNotification();

        final Bundle extras = this.getIntent().getExtras();
        projectId = extras.getInt("projectId");
        projectName = extras.getString("projectName");

        setTitle(projectName);

        tasks = taskService.findByProject(projectId);

        ListView listView = (ListView) findViewById(R.id.taskList);
        adapter = new TaskAdapter(this, tasks);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Task task = tasks.get(i);
        final Intent intent = new Intent(this, TaskViewActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("projectName", projectName);
        startActivityForResult(intent, TASK_VIEW);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_context_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int option = item.getItemId();

        switch (option) {
            case NEW_TASK:
                final Intent intent = new Intent(this, CreateTaskActivity.class);
                intent.putExtra("projectId", projectId);
                startActivityForResult(intent, CREATE_NEW_TASK);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CREATE_NEW_TASK:
                createTaskPerformOnResult(resultCode, data);
                break;
            case TASK_VIEW:
                taskViewPerformOnResult(resultCode, data);
        }
    }

    private void taskViewPerformOnResult(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                final String action = data.getStringExtra("action");
                final int taskId = data.getIntExtra("taskId", 0);
                if(action.equals("delete") && taskId > 0) {
                    taskService.deleteById(taskId);
                    reloadTaskList();
                    notification.success(getString(R.string.delete_task), getString(R.string.delete_task_success));
                }
        }
    }

    private void createTaskPerformOnResult(int resultCode, Intent data) {
        if (resultCode ==  RESULT_OK) {
            final Task task = (Task) data.getSerializableExtra("task");
            Objects.requireNonNull(task, "The task is required");
            taskService.save(task);
            reloadTaskList();
            notification.success(getString(R.string.create_new_task), getString(R.string.create_new_task_sucess));
        }
    }

    private void reloadTaskList() {
        tasks = taskService.findByProject(projectId);
        adapter.setTasks(tasks);
        adapter.notifyDataSetChanged();
    }

    private final int NEW_TASK = R.id.new_task;
    private final int CREATE_NEW_TASK = 101;
    private final int TASK_VIEW = 102;
}