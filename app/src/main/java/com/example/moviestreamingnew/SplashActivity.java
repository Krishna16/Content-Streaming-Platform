package com.example.moviestreamingnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestreamingnew.account.Login;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private int splashTime = 1500;
    private Handler myHandler;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null){
                    Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                    startActivity(i);
                    finish();
                }

                else {
                    Intent i = new Intent(SplashActivity.this, Login.class);
                    startActivity(i);
                    finish();
                }
            }
        }, splashTime);
    }
}