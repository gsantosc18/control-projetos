package com.gedalias.controledeprojeto.persistence.mapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.domain.Project;
import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;

public final class ProjectMapper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ProjectEntity toEntity(final Project project) {
        return new ProjectEntity(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getType(),
            project.getTimeOption(),
            project.getDuration(),
            project.isAlreadyFinished()
        );
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Project toDomain(final ProjectEntity entity) {
        return new Project(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getType(),
            entity.getTimeOption(),
            entity.getDuration(),
            entity.isAlreadyFinished()
        );
    }
}
