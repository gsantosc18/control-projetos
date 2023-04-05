package com.gedalias.controledeprojeto.persistence.database;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gedalias.controledeprojeto.persistence.dao.ProjectDao;
import com.gedalias.controledeprojeto.persistence.dao.TaskDao;
import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;
import com.gedalias.controledeprojeto.persistence.entity.TaskEntity;

@RequiresApi(api = Build.VERSION_CODES.O)
@Database(exportSchema = false, entities = {ProjectEntity.class, TaskEntity.class}, version = 1)
public abstract class ProjectDatabase extends RoomDatabase {
    public static final String NAME = "project-database";
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    private static ProjectDatabase instance;

    public static ProjectDatabase getDatabase(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, ProjectDatabase.class, ProjectDatabase.NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
