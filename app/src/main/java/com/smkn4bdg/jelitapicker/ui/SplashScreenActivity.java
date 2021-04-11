package com.smkn4bdg.jelitapicker.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smkn4bdg.jelitapicker.R;

public class SplashScreenActivity extends AppCompatActivity{
    private int waktu_loading = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashScreenActivity.this, WelcomePageActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);
    }
}
