package com.gedalias.controledeprojeto.domain;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private int projectId;
    private String name;
    private String description;
    private TaskStatus status;

    public Task() {
    }

    public Task(int projectId, String name, String description, TaskStatus status) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, int projectId, String name, String description, TaskStatus status) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
