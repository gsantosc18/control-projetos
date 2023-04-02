package com.gedalias.controledeprojeto.view;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.util.AppFactory;
import com.gedalias.controledeprojeto.util.Launcher;
import com.gedalias.controledeprojeto.util.impl.AppFactoryImpl;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {
    private final Launcher launchActivity;

    public MainActivity() {
        AppFactory factory = new AppFactoryImpl(this);
        launchActivity = factory.createLauncher();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            launchActivity.start(HomeActivity.class);
            finish();
        }, 1500);
    }
}