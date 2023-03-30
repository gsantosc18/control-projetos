package com.gedalias.controledeprojeto.persistence.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gedalias.controledeprojeto.persistence.dao.ProjectDao;
import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;

@Database(entities = {ProjectEntity.class}, version = 1)
public abstract class ProjectDatabase extends RoomDatabase {
    public static final String NAME = "project-database";
    public abstract ProjectDao projectDao();
}
