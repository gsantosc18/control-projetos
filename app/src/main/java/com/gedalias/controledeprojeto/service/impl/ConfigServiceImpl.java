package com.gedalias.controledeprojeto.service.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.gedalias.controledeprojeto.service.ConfigService;

public class ConfigServiceImpl implements ConfigService {
    private final SharedPreferences sharedPreferences;
    private final String APP_ID = "com.gedalias.controledeprojeto";

    private final String NIGHT_MODE = "NIGHT_MODE";

    public ConfigServiceImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
    }

    private String getSharedName(String name) {
        return String.format("%s.%s", APP_ID, name);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public boolean isNightMode() {
        return sharedPreferences.getBoolean(getSharedName(NIGHT_MODE), false);
    }
    public void setNightMode(boolean nightMode) {
        getEditor()
                .putBoolean(getSharedName(NIGHT_MODE), nightMode)
                .commit();
    }
}
