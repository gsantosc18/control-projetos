package com.gedalias.controledeprojeto.util;

public interface Notification {
    void onlyText(String message);
    void success(String title, String message);

    void error(String title, String message);
}
