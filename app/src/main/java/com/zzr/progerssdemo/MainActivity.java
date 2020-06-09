package com.zzr.progerssdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyProgressBar progressBar;
    private int totalProgress;
    private int step;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.my_progress);
        totalProgress = 70;
        progressBar.setMaxProgress(100);
        progressBar.setCurProgress(0);
        handler = new Handler(getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < totalProgress; i++) {
                    progressBar.setCurProgress(i);
                    SystemClock.sleep(100);
                }
            }
        }).start();

//        handler.postDelayed(runnable, 1000);
    }

    private void startProgress() {
        if (step >= totalProgress) {
            handler.removeCallbacks(runnable);
            return;
        }
        step += 5;
        progressBar.setCurProgress(step);
        progressBar.postInvalidate();
        handler.postDelayed(runnable, 500);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startProgress();
        }
    };
}