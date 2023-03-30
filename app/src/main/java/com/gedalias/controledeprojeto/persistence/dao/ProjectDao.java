package com.gedalias.controledeprojeto.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gedalias.controledeprojeto.persistence.entity.ProjectEntity;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert
    void insert(ProjectEntity project);

    @Query("SELECT * FROM project")
    List<ProjectEntity> all();

    @Query("DELETE FROM project WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM project WHERE id = :id")
    ProjectEntity findById(int id);

    @Update
    void update(ProjectEntity projectEntity);
}
