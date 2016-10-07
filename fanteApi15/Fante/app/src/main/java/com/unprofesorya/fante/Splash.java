package com.unprofesorya.fante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                intent = new Intent(Splash.this, Actividad0.class);
                startActivity(intent);
                Splash.this.finish();

            }

        };

        long splashRetraso = 3000;
        Timer timer = new Timer();
        timer.schedule(task, splashRetraso);

    }
}