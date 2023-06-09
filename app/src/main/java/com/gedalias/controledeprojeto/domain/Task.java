package com.gedalias.controledeprojeto.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Task implements Serializable {
    private Integer id;
    private int projectId;
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {
    }

    public Task(int projectId, String name, String description, TaskStatus status) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(Integer id, int projectId, String name, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
