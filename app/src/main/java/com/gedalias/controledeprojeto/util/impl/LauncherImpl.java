package com.gedalias.controledeprojeto.util.impl;

import android.content.Context;
import android.content.Intent;

import com.gedalias.controledeprojeto.util.Launcher;

public class LauncherImpl implements Launcher {
    private final Context context;

    public LauncherImpl(Context context) {
        this.context = context;
    }

    @Override
    public void start(Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }
}
