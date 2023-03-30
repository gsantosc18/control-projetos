package com.gedalias.controledeprojeto;

import android.content.Context;
import android.content.Intent;

public class LauncherActivity {
    private final Context context;

    public LauncherActivity(Context context) {
        this.context = context;
    }

    public void start(Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }
}
