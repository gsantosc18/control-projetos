package com.gedalias.controledeprojeto.persistence.mapper;

import com.gedalias.controledeprojeto.domain.Project;
import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;

public final class ProjectMapper {
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
