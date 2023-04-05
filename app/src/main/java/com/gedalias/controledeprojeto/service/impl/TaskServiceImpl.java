package com.gedalias.controledeprojeto.service.impl;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.domain.TaskStatus;
import com.gedalias.controledeprojeto.service.TaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskServiceImpl implements TaskService {
    private final Context context;

    private static List<Task> tasks;

    static {
        tasks = new ArrayList<>();

        tasks.add(new Task(1, 1, "Task teste", "Minha atividade de teste", TaskStatus.TO_DO, LocalDateTime.now(), null));
    }

    public TaskServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Task> findByProject(int projectId) {
        return tasks;
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }
}
