package com.gedalias.controledeprojeto.service;

public interface ServiceFactory {
    ProjectService createProjectService();
    ConfigService createConfigService();

    TaskService createTaskService();
}
