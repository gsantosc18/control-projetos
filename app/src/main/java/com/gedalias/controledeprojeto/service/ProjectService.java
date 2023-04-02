package com.gedalias.controledeprojeto.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.domain.Project;

import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.N)
public interface ProjectService {

    List<Project> all();

    void save(Project project);

    void delete(int id);

    Project findById(int id);

    void updateById(int id, Project project);
}