package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Animacio extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacio);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user==null){
                    startActivity(new Intent(Animacio.this,Login.class));
                    finish();
                }else{
                    startActivity(new Intent(Animacio.this,Proba.class));
                    finish();
                }

            }
        },3000);
    }
}