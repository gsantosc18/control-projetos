package com.gedalias.controledeprojeto.view;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.adapter.ProjectAdapter;
import com.gedalias.controledeprojeto.domain.Project;
import com.gedalias.controledeprojeto.service.ConfigService;
import com.gedalias.controledeprojeto.service.ProjectService;
import com.gedalias.controledeprojeto.service.ServiceFactory;
import com.gedalias.controledeprojeto.service.impl.ServiceFactoryImpl;
import com.gedalias.controledeprojeto.util.AppFactory;
import com.gedalias.controledeprojeto.util.Notification;
import com.gedalias.controledeprojeto.util.impl.AppFactoryImpl;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class HomeActivity extends AppCompatActivity {
    private static final int CREATE_PROJECT_ACTIVITY = 11;
    private static final int UPDATE_PROJECT_ACTIVITY = 12;
    private ProjectService projectService;
    private ConfigService configService;
    private List<Project> projectList;
    private ProjectAdapter adapter;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppFactory factory = new AppFactoryImpl(this);
        notification = factory.createNotification();

        ServiceFactory serviceFactory = new ServiceFactoryImpl(this);
        projectService = serviceFactory.createProjectService();
        configService = serviceFactory.createConfigService();

        projectList = projectService.all();

        toggleDarkMode();

        setTitle(R.string.app_name_main);

        setContentView(R.layout.activity_home);

        ListView listProjects = (ListView) findViewById(R.id.listProjects);

        adapter = new ProjectAdapter(this,projectService.all());

        listProjects.setAdapter(adapter);

        listProjects.setOnItemClickListener((parent, view, position, id) -> {
            Project project = (Project) listProjects.getItemAtPosition(position);
            final String message = getString(R.string.project_click, project.getName());
            Toast.makeText(this, message, LENGTH_SHORT).show();
        });

        registerForContextMenu(listProjects);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", "request: "+requestCode+ " result: "+ resultCode);
        switch(requestCode){
             case CREATE_PROJECT_ACTIVITY:
                createProjectPerformOnResultCode(resultCode, data); break;
            case UPDATE_PROJECT_ACTIVITY:
                updateProjectPerformOnResultCode(resultCode, data); break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.nightModeOpt);
        menuItem.setChecked(configService.isNightMode());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int optionId = item.getItemId();
        Log.i("optionSelected", "Item: "+optionId);
        switch (optionId) {
            case CREATE_NEW_PROJECT:
                startActivityForResult(
                    new Intent(this, CreateProjectActivity.class),
                    CREATE_PROJECT_ACTIVITY
                ); break;
            case ABOUT:
                startActivity(
                    new Intent(this, AboutBaseActivity.class)
                ); break;
            case NIGHT_MODE:
                final boolean enableDarkMode = !configService.isNightMode();
                item.setChecked(enableDarkMode);
                configService.setNightMode(enableDarkMode);
                toggleDarkMode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context_project, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.i("selectedItemContext", "Selecionado o item: "+info.position);

        final Project projectSelected = projectList.get(info.position);

        switch (item.getItemId()) {
            case DELETE_PROJECT:
                projectService.delete(projectSelected.getId());
                loadProjectsAndUpdateAdapter();
                break;
            case UPDATE_PROJECT:
                startUpdateProjectActivity(info.position, projectSelected);
                break;
            case TASK_PROJECT:
                startTaskProjectActivity(projectSelected);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void startUpdateProjectActivity(int position, Project projectSelected) {
        final Intent intent = new Intent(this, CreateProjectActivity.class);
        intent.putExtra("project", projectSelected);
        intent.putExtra("projectId", position);
        startActivityForResult(
            intent,
            UPDATE_PROJECT_ACTIVITY
        );
    }

    private void startTaskProjectActivity(Project projectSelected) {
        final Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("projectId", projectSelected.getId());
        intent.putExtra("projectName", projectSelected.getName());
        startActivity(intent);
    }

    private void loadProjectsAndUpdateAdapter() {
        if(adapter != null) {
            projectList = projectService.all();
            adapter.reloadData(projectList);
            adapter.notifyDataSetChanged();
            Log.i("load", String.valueOf(adapter.getCount()));
        }
    }

    private void createProjectPerformOnResultCode(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                final Project project = (Project) data.getExtras().getSerializable("project");
                projectService.save(project);
                loadProjectsAndUpdateAdapter();
                notification.success(
                    getString(R.string.create_project_title),
                    getString(R.string.project_created, project.getName())
                );
                Log.i("onProjectSaved",
                        "The project was saved: "+project.getName()); break;
            case RESULT_CANCELED:
                notification.onlyText(getString(R.string.project_creation_cancelled));
                Log.i("onProjectCancelled",
                        "The project creation was cancelled"); break;
        }
    }

    private void updateProjectPerformOnResultCode(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                final Project project = (Project) data.getExtras().getSerializable("project");
                final int projectId = data.getExtras().getInt("projectId");
                final Project projectFoundInList = projectList.get(projectId);
                projectService.updateById(projectFoundInList.getId(), project);
                loadProjectsAndUpdateAdapter();
                notification.success(
                        getString(R.string.updated_project_title),
                        getString(R.string.project_updated, project.getName())
                );
                break;
            case RESULT_CANCELED:
                notification.onlyText(getString(R.string.project_update_cancelled));
                Log.i("onProjectCancelled",
                        "The project update was cancelled"); break;
        }
    }

    private void toggleDarkMode() {
        if(configService.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private final int CREATE_NEW_PROJECT = R.id.create_new;
    private final int ABOUT = R.id.about;
    private final int NIGHT_MODE = R.id.nightModeOpt;

    private final int DELETE_PROJECT = R.id.delete_project;
    private final int UPDATE_PROJECT = R.id.update_project;
    private final int TASK_PROJECT = R.id.task_project;
}