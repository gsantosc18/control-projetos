package com.gedalias.controledeprojeto.view;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.util.Objects.requireNonNull;

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
import com.gedalias.controledeprojeto.domain.ProjectType;
import com.gedalias.controledeprojeto.domain.TimeType;
import com.gedalias.controledeprojeto.service.ConfigService;
import com.gedalias.controledeprojeto.service.ProjectService;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class HomeActivity extends AppCompatActivity {
    private static final int CREATE_PROJECT_ACTIVITY = 11;
    private static final int UPDATE_PROJECT_ACTIVITY = 12;
    private ProjectService projectService;
    private ConfigService configService;

    private List<Project> projectList;

    private ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectService = new ProjectService(this);
        projectList = projectService.all();
        configService = new ConfigService(this);

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

        switch (item.getItemId()) {
            case R.id.delete_project:
                final Project projectToDelete = projectList.get(info.position);
                projectService.delete(projectToDelete.getId());
                loadProjectsAndUpdateAdapter();
                break;
            case R.id.update_project:
                Project project = projectService.findById(info.position);
                Intent intent = new Intent(this, CreateProjectActivity.class);
                intent.putExtra("project", project);
                intent.putExtra("projectId", info.position);
                startActivityForResult(
                    intent,
                    UPDATE_PROJECT_ACTIVITY
                );
                break;
        }
        return super.onContextItemSelected(item);
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
                makeText(
                        this,
                        getString(R.string.project_created, project.getName()),
                        LENGTH_SHORT
                ).show();
                Log.i("onProjectSaved",
                        "The project was saved: "+project.getName()); break;
            case RESULT_CANCELED:
                makeText(
                    this,
                    getString(R.string.project_creation_cancelled),
                    LENGTH_SHORT
                ).show();
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
                makeText(
                    this,
                    getString(R.string.project_updated, project.getName()),
                    LENGTH_SHORT
                ).show();
                break;
            case RESULT_CANCELED:
                makeText(
                    this,
                    getString(R.string.project_update_cancelled),
                    LENGTH_SHORT
                ).show();
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
}