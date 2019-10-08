package com.wave_chtj.example.screenadaptation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chtj.base_iotutils.ScreenUtils;

import com.wave_chtj.example.R;

public class ScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Log.e("TAG>>>", ScreenUtils.getScreenInfo(this));
    }
}