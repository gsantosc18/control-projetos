package com.gedalias.controledeprojeto.domain;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Project implements Serializable {
    private int id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedATime;
    private ProjectType type;
    private TimeType timeOption;
    private int duration;
    private boolean alreadyFinished;

    public Project() {
    }

    public Project(String name,
                   String description,
                   ProjectType type,
                   TimeType timeOption,
                   int duration,
                   boolean alreadyFinished) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.timeOption = timeOption;
        this.duration = duration;
        this.alreadyFinished = alreadyFinished;
    }

    public Project(int id,
                   String name,
                   String description,
                   ProjectType type,
                   TimeType timeOption,
                   int duration,
                   boolean alreadyFinished) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.timeOption = timeOption;
        this.duration = duration;
        this.alreadyFinished = alreadyFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedATime() {
        return updatedATime;
    }

    public void setUpdatedATime(LocalDateTime updatedATime) {
        this.updatedATime = updatedATime;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public TimeType getTimeOption() {
        return timeOption;
    }

    public void setTimeOption(TimeType timeOption) {
        this.timeOption = timeOption;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isAlreadyFinished() {
        return alreadyFinished;
    }

    public void setAlreadyFinished(boolean alreadyFinished) {
        this.alreadyFinished = alreadyFinished;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public String toString() {
        return "Nome: "+getName()
                +" | Descrição: "+getDescription()
                +" | Tipo: "+getType().getType()
                +" | Duração: "+getDuration()
                +" ("+getTimeOption().getTimeOption()+")"
                +" | Já finalizado? "+(isAlreadyFinished()? "Sim": "Não");
    }
}
