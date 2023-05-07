package com.example.gym;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class Stopwatch extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fab;

    private int seconds;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if(savedInstanceState != null) {
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("running");
            savedInstanceState.getBoolean("wasRunning");

        }

        stopwatchTimer();

        nav = findViewById(R.id.bottomNaqvigationView);
        fab = findViewById(R.id.fab);

        nav.getMenu().findItem(R.id.stopwatch).setChecked(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
                finish();
            }
        });

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.home:
                        Intent intent3 = new Intent(getApplicationContext(), Proba.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.food:
                        Intent intent4 = new Intent(getApplicationContext(), Food.class);
                        startActivity(intent4);
                        finish();
                        break;
                    case R.id.profil:
                        Intent intent = new Intent(getApplicationContext(), Logout.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.stopwatch:
                        Intent intent2 = new Intent(getApplicationContext(), Stopwatch.class);
                        startActivity(intent2);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
    public void onStart(View view) {
        running = true;
    }

    public void onStop(View view) {
        running = false;
    }

    public void onReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    private void stopwatchTimer() {

        TextView timeView = findViewById(R.id.textView);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if(running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}