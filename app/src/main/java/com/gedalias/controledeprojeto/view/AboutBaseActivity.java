package com.gedalias.controledeprojeto.view;

import android.os.Bundle;

import com.gedalias.controledeprojeto.R;

public class AboutBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.about);
        setContentView(R.layout.activity_about);
    }
}