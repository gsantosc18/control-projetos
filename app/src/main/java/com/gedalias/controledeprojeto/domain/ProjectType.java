package com.gedalias.controledeprojeto.domain;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;

import java.util.Arrays;


@RequiresApi(api = Build.VERSION_CODES.N)
public enum ProjectType {
    PERSONAL(R.string.option_personal_radiobtn_label), WORK(R.string.option_work_radiobtn_label);

    private final int type;

    ProjectType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ProjectType of(String type, Context context) {
        return Arrays.stream(values())
                .filter(t -> context.getString(t.type).equals(type))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tipo não encontrado"));
    }
}
