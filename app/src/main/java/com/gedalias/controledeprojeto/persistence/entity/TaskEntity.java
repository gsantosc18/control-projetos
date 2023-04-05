package com.gedalias.controledeprojeto.persistence.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gedalias.controledeprojeto.domain.TaskStatus;
import com.gedalias.controledeprojeto.persistence.converter.LocalDateTimeConverter;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
@Entity(tableName = "task", foreignKeys = @ForeignKey(
        entity = ProjectEntity.class, parentColumns = "id", childColumns = "projectId"
))
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(index = true)
    private int projectId;
    private String name;
    private String description;
    private TaskStatus status;
    @TypeConverters(LocalDateTimeConverter.class)
    private LocalDateTime createdAt;
    @TypeConverters(LocalDateTimeConverter.class)
    private LocalDateTime updatedAt;

    public TaskEntity(Integer id, int projectId, String name, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
