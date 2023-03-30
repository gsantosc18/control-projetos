package com.gedalias.controledeprojeto.view;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gedalias.controledeprojeto.LauncherActivity;
import com.gedalias.controledeprojeto.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    private final LauncherActivity launchActivity;

    public MainActivity() {
        launchActivity = new LauncherActivity(this);
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