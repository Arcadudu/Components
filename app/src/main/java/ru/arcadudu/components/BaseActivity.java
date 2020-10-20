package ru.arcadudu.components;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    float toPx(Context context, int dp) {
        return dp*context.getResources().getDisplayMetrics().density;
    }

    int toDp(Context context, int px) {
        return (int) (px/context.getResources().getDisplayMetrics().density);
    }

}
