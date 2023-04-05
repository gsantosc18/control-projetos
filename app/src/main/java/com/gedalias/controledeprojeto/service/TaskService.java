package com.gedalias.controledeprojeto.service;

import com.gedalias.controledeprojeto.domain.Task;

import java.util.List;

public interface TaskService {
    List<Task> findByProject(int projectId);
    void save(Task task);

    void deleteById(int taskId);
}
