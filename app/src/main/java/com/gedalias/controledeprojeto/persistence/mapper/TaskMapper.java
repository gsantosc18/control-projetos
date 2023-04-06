package com.gedalias.controledeprojeto.persistence.mapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.persistence.entity.TaskEntity;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskMapper {
    public static TaskEntity toEntity(Task task) {
        return new TaskEntity(
            task.getId(),
            task.getProjectId(),
            task.getName(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }

    public static Task toDomain(TaskEntity task) {
        return new Task(
            task.getId(),
            task.getProjectId(),
            task.getName(),
            task.getDescription(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }
}
