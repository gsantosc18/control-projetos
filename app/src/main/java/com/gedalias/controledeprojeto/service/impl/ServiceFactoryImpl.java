package com.gedalias.controledeprojeto.service.impl;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.service.ConfigService;
import com.gedalias.controledeprojeto.service.ProjectService;
import com.gedalias.controledeprojeto.service.ServiceFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ServiceFactoryImpl implements ServiceFactory {
    private final Context context;

    public ServiceFactoryImpl(Context context) {
        requireNonNull(context, "The context can not be empty");
        this.context = context;
    }

    @Override
    public ProjectService createProjectService() {
        return new ProjectServiceImpl(context);
    }

    @Override
    public ConfigService createConfigService() {
        return new ConfigServiceImpl(context);
    }
}
