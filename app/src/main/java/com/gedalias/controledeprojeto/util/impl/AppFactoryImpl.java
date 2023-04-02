package com.gedalias.controledeprojeto.util.impl;

import android.content.Context;

import com.gedalias.controledeprojeto.util.AppFactory;
import com.gedalias.controledeprojeto.util.Launcher;
import com.gedalias.controledeprojeto.util.Notification;

public class AppFactoryImpl implements AppFactory {
    private final Context context;

    public AppFactoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Notification createNotification() {
        return new NotificationImpl(context);
    }

    @Override
    public Launcher createLauncher() {
        return new LauncherImpl(context);
    }
}
