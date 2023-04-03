package com.gedalias.controledeprojeto.view;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.adapter.TaskAdapter;
import com.gedalias.controledeprojeto.service.ServiceFactory;
import com.gedalias.controledeprojeto.service.TaskService;
import com.gedalias.controledeprojeto.service.impl.ServiceFactoryImpl;


@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskActivity extends BaseActivity {
    private TaskService taskService;
    private TaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        final ServiceFactory factory = new ServiceFactoryImpl(this);
        taskService = factory.createTaskService();

        final Bundle extras = this.getIntent().getExtras();
        final int projectId = extras.getInt("projectId");
        final String projectName = extras.getString("projectName");

        setTitle(projectName);

        ListView listView = (ListView) findViewById(R.id.taskList);
        adapter = new TaskAdapter(this, taskService.findByProject(projectId));

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_context_task, menu);
        return true;
    }
}