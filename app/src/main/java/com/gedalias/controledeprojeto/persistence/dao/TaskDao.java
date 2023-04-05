package com.gedalias.controledeprojeto.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gedalias.controledeprojeto.persistence.entity.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task WHERE projectId = :projectId")
    List<TaskEntity> findByProject(int projectId);

    @Insert
    void insert(TaskEntity taskEntity);
}
