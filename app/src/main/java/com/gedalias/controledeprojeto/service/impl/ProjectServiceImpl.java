package com.gedalias.controledeprojeto.service.impl;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.domain.Project;
import com.gedalias.controledeprojeto.persistence.dao.ProjectDao;
import com.gedalias.controledeprojeto.persistence.database.ProjectDatabase;
import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;
import com.gedalias.controledeprojeto.persistence.mapper.ProjectMapper;
import com.gedalias.controledeprojeto.service.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;

    public ProjectServiceImpl(Context context) {
        ProjectDatabase database = ProjectDatabase.getDatabase(context);
        projectDao = database.projectDao();
    }

    public List<Project> all() {
        return projectDao.all()
            .stream()
            .map(ProjectMapper::toDomain)
            .collect(Collectors.toList());
    }

    public void save(Project project) {
        final ProjectEntity entity = ProjectMapper.toEntity(project);
        projectDao.insert(entity);
    }

    public void delete(int id) {
        projectDao.delete(id);
    }

    public Project findById(int id) {
        final ProjectEntity entity = projectDao.findById(id);
        return ProjectMapper.toDomain(entity);
    }

    public void updateById(int id, Project project) {
        final ProjectEntity entity = ProjectMapper.toEntity(project);
        entity.setId(id);
        projectDao.update(entity);
    }
}