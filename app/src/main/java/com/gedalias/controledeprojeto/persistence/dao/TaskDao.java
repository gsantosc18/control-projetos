package com.gedalias.controledeprojeto.persistence.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gedalias.controledeprojeto.domain.Task;
import com.gedalias.controledeprojeto.persistence.entity.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task WHERE projectId = :projectId")
    List<TaskEntity> findByProject(int projectId);

    @Insert
    void insert(TaskEntity taskEntity);

    @Query("DELETE FROM task where id = :taskId")
    void delete(int taskId);

    @Update
    void update(TaskEntity taskEntity);
}
