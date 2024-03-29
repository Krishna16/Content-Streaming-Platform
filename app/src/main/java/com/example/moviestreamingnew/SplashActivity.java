package com.example.moviestreamingnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestreamingnew.account.Login;
import com.example.moviestreamingnew.account.NewUserForm;
import com.example.moviestreamingnew.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private int splashTime = 1500;
    private FirebaseAuth mAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null){
                    User.getInstance().setUid(mAuth.getCurrentUser().getUid());

                    DatabaseReference uidReference = firebaseDatabase.child("users").child(User.getInstance().getUid());

                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                Intent i = new Intent(SplashActivity.this, NewUserForm.class);
                                startActivity(i);
                                finish();
                            }

                            else{
                                Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };

                    uidReference.addListenerForSingleValueEvent(eventListener);
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