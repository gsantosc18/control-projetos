package com.gedalias.controledeprojeto.service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gedalias.controledeprojeto.domain.Project;
import com.gedalias.controledeprojeto.domain.ProjectType;
import com.gedalias.controledeprojeto.domain.TimeType;
import com.gedalias.controledeprojeto.persistence.dao.ProjectDao;
import com.gedalias.controledeprojeto.persistence.database.ProjectDatabase;
import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;
import com.gedalias.controledeprojeto.persistence.mapper.ProjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiresApi(api = Build.VERSION_CODES.N)
public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(Context context) {
        ProjectDatabase database = Room.databaseBuilder(context, ProjectDatabase.class, ProjectDatabase.NAME)
                .allowMainThreadQueries()
                .build();
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