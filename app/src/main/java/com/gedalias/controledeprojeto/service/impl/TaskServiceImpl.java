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

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;

    public TaskServiceImpl(Context context) {
        ProjectDatabase projectDatabase = ProjectDatabase.getDatabase(context);
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
        TaskEntity toSaveTask = TaskMapper.toEntity(task);
        taskDao.insert(toSaveTask);
    }

    @Override
    public void deleteById(int taskId) {
        taskDao.delete(taskId);
    }
}
