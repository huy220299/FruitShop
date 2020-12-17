package com.example.nvhuy.navdrawer.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.nvhuy.navdrawer.MainActivity;
import com.example.nvhuy.navdrawer.R;

public class SplashActivity extends AppCompatActivity {
    int PRIVATE_MODE = 0;
   SharedPreferences mSharePref;
    static int Splash_time = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharePref = getSharedPreferences("SharePref",PRIVATE_MODE);
                boolean isFirstTime = mSharePref.getBoolean("firstTime",true);
                if(isFirstTime){

                    SharedPreferences.Editor editor = mSharePref.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, introductoryActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },Splash_time);
    }
    
}
