package com.gedalias.controledeprojeto.persistence.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gedalias.controledeprojeto.domain.ProjectType;
import com.gedalias.controledeprojeto.domain.TimeType;
import com.gedalias.controledeprojeto.persistence.converter.LocalDateTimeConverter;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
@Entity(tableName = "project")
public class ProjectEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @TypeConverters(LocalDateTimeConverter.class)
    @ColumnInfo(name = "createdAt")
    private LocalDateTime createdAt;
    @TypeConverters(LocalDateTimeConverter.class)
    @ColumnInfo(name = "updatedATime")
    private LocalDateTime updatedATime;
    @ColumnInfo(name = "type")
    private ProjectType type;
    @ColumnInfo(name = "timeOption")
    private TimeType timeOption;
    @ColumnInfo(name = "duration")
    private int duration;
    @ColumnInfo(name = "alreadyFinished")
    private boolean alreadyFinished;

    public ProjectEntity(int id, String name, String description, ProjectType type, TimeType timeOption, int duration, boolean alreadyFinished) {
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
}
