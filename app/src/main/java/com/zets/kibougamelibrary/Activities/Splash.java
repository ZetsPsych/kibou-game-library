package com.zets.kibougamelibrary.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zets.kibougamelibrary.Helper.DBHelper;
import com.zets.kibougamelibrary.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        init();
    }

    private void init(){
        DBHelper.getInstance().init(this);

        new Timer().schedule(new TimerTask(){
            public void run() {
                Splash.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                        finish();
                    }
                });
            }
        }, 1000);
    }
}
