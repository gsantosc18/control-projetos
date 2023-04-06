package com.gedalias.controledeprojeto.service.impl;

import static java.util.stream.Collectors.toList;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.persistence.dao.TaskDao;
import com.gedalias.controledeprojeto.persistence.database.ProjectDatabase;
import com.gedalias.controledeprojeto.persistence.entity.TaskEntity;
import com.gedalias.controledeprojeto.persistence.mapper.TaskMapper;
import com.gedalias.controledeprojeto.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;

    public TaskServiceImpl(Context context) {
        final ProjectDatabase projectDatabase = ProjectDatabase.getDatabase(context);
        taskDao = projectDatabase.taskDao();
    }

    @Override
    public List<Task> findByProject(int projectId) {
        return taskDao.findByProject(projectId)
                .stream()
                .map(TaskMapper::toDomain)
                .collect(toList());
    }

    @Override
    public void save(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        taskDao.insert(TaskMapper.toEntity(task));
    }

    @Override
    public void deleteById(int taskId) {
        taskDao.delete(taskId);
    }

    @Override
    public Task update(Task task) {
        task.setUpdatedAt(LocalDateTime.now());
        taskDao.update(TaskMapper.toEntity(task));
        return task;
    }
}
