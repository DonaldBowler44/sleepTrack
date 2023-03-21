package com.example.sleeptrackverthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

public class StopWatch extends AppCompatActivity {

    // integers to store hours, minutes, seconds, ms
    int hours, minutes, secs, ms;

    // integer to store seconds
    private int seconds = 0;

    // boolean to check if stopwatch is running or not
    private boolean running;

    // simple count variable to count number of laps
    int lapCount = 0;

    // creating object of ImageView and Text View
    ImageView playBtn, pauseBtn, stopBtn, timeLapseBtn;
    TextView timeView;
    TextView timeViewms;
    TextView timeLapse;

    //Initializing firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        // initializing the Image view objects
        playBtn = (ImageView) findViewById(R.id.playBtn);
        pauseBtn = (ImageView) findViewById(R.id.pauseBtn);
        stopBtn = (ImageView) findViewById(R.id.stopBtn);

        timeLapseBtn = (ImageView) findViewById(R.id.timeLapseBtn);

        // initializing the text view objects
        timeView = (TextView) findViewById(R.id.time_view);
        timeViewms = (TextView) findViewById(R.id.time_view_ms);
        timeLapse = (TextView) findViewById(R.id.timeLapse);

        //play button click listener
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showing simple toast message to user
                Toast.makeText(StopWatch.this, "started", Toast.LENGTH_SHORT).show();

                // hide the play an stop button
                playBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.GONE);

                //show the pause and time lapse button
                pauseBtn.setVisibility(View.VISIBLE);
                timeLapseBtn.setVisibility(View.VISIBLE);

                // set running true
                running = true;
            }
        });

        // pause button click listener
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showing simple toast message to user
                Toast.makeText(StopWatch.this, "Paused", Toast.LENGTH_SHORT).show();

                //show the play and stop button
                playBtn.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.VISIBLE);

                // hide the pause and time lapse button
                timeLapseBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.GONE);

                running = false;

            }
        });

        //stop button click listener
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showing simple toast message to user
                Toast.makeText(StopWatch.this, "Stopped", Toast.LENGTH_SHORT).show();

                // set running to false
                running = false;
                seconds = 0;
                lapCount = 0;

                // setting the text view to zero
                timeView.setText("00:00:00");
                timeViewms.setText("00");
                timeLapse.setText("");

                // show the play
                playBtn.setVisibility(View.VISIBLE);

                // hide the pause, stop and time lapse button
                pauseBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.GONE);
                timeLapseBtn.setVisibility(View.GONE);

            }
        });

        // lap button click listener
        timeLapseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling timelpase function
                timeLapseFun();

                Intent i = new Intent(getApplicationContext(), RatingBarFile.class);
                startActivity(i);
            }
        });

        runTimer();

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.stopwatch);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.alarmclock:
                        startActivity(new Intent(getApplicationContext(),AlarmClock.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.graphLine:
                        startActivity(new Intent(getApplicationContext(),graphLine.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stopwatch:
                        startActivity(new Intent(getApplicationContext(),StopWatch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void runTimer() {

        // creating handler
        final Handler handlertime = new Handler();

        // creatimg handler
        final Handler handlerMs = new Handler();

        handlertime.post(new Runnable() {
            @Override
            public void run() {
                hours = seconds / 3600;
                minutes = (seconds % 3600) / 60;
                secs = seconds % 60;

                // if running increment the seconds
                if (running) {
                    String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);

                    timeView.setText(time);

                    getGraphTime();

                    seconds++;
                }

                handlertime.postDelayed(this, 1000);

            }
        });

        handlerMs.post(new Runnable() {
            @Override
            public void run() {

                    if (ms >= 99) {
                        ms = 0;
                    }

                    // if running increment the ms
                    if (running) {
                        String msString = String.format(Locale.getDefault(), "%02d", ms);
                        timeViewms.setText(msString);

                        ms++;
                    }
                    handlerMs.postDelayed(this, 1);

            }
        });
    }

    void timeLapseFun() {

        // increase lap count when function is called
        lapCount++;

        String laptext = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        String msString = String.format(Locale.getDefault(), "%02d", ms);
        String laptime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);

        // adding ms to lap next
        laptext = laptext + ":" + msString;

        laptext = " Nap " + lapCount + " ------------->       " + laptext + " \n " + timeLapse.getText();

//        if(lapCount >= 10) {
//            laptext = " Lap " + lapCount + " ------------->       " + laptext + " \n " + timeLapse.getText();
//        } else {
//            laptext = " Lap " + lapCount + " --------------->       " + laptext + " \n " + timeLapse.getText();
//        }

        String minData = String.valueOf(minutes);
        String lapData = String.valueOf(lapCount);
        String secTime = String.valueOf(seconds);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        String formattedDate = now.format(formatter);
        Map<String, Object> fire = new HashMap<>();


        //fire.put(lapData, secTime);
        fire.put(formattedDate, secTime);
        // for fire.put its key first, then value like key, secTime


        db.collection("Records").document("time")
                .update(fire)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("StopWatch", "DocumentSnapshot successfully written!");
                        //.update(fire) use this when you want to add aleady existing data,
                        //.set(fire) to add data
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("StopWatch", "Error writing document", e);
                    }
                });



        //showing simple toast message to user
        Toast.makeText(StopWatch.this, "Nap " + lapCount, Toast.LENGTH_SHORT).show();

        // showing the lap text
        timeLapse.setText(laptext);
    }

    public int getGraphTime(){
        int graphTime = minutes;

        return graphTime;
    }
}