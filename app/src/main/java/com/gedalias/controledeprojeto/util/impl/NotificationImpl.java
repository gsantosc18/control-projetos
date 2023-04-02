package com.gedalias.controledeprojeto.util.impl;

import static java.util.Objects.requireNonNull;

import android.app.AlertDialog;
import android.content.Context;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.util.Notification;

public class NotificationImpl implements Notification {
    private final AlertDialog.Builder builder;

    private final String OK_LABEL = "OK";

    public NotificationImpl(Context context) {
        requireNonNull(context, "The context can not be empty");
        builder = new AlertDialog.Builder(context);
    }

    @Override
    public void onlyText(String message) {
        builder.setMessage(message)
            .setNeutralButton(OK_LABEL, null)
            .create()
            .show();
    }

    @Override
    public void success(String title, String message) {
        baseDialog(title, message, R.drawable.baseline_success_circle_24);
    }

    @Override
    public void error(String title, String message) {
        baseDialog(title, message, R.drawable.baseline_error_24);
    }

    private void baseDialog(String title, String message, int icon) {
        builder.setMessage(message)
            .setIcon(icon)
            .setTitle(title)
            .setNeutralButton(OK_LABEL, null)
            .create()
            .show();
    }
}
