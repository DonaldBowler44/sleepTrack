package com.example.sleeptrackverthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //first textview
        TextView textView = findViewById(R.id.CDJMTitle);

        // Set the alpha value of TextView to 0
        textView.setAlpha(0f);

        // create an ObjectAnimator to animate the alpha value to 0 to 1
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
        fadeIn.setDuration(2000);

        // Add a listener to set the visibility of the  TextView to VISIBLE when the animation is done
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textView.setVisibility(View.VISIBLE);
            }
        });

        // Start the animation
        fadeIn.start();

        //second textview
        TextView textViewTwo = findViewById(R.id.textView6);

        // Set the alpha value of TextView to 0
        textViewTwo.setAlpha(0f);

        // create an ObjectAnimator to animate the alpha value to 0 to 1
        ObjectAnimator fadeInTwo = ObjectAnimator.ofFloat(textViewTwo, "alpha", 0f, 1f);
        fadeInTwo.setDuration(2000);

        // Add a listener to set the visibility of the  TextView to VISIBLE when the animation is done
        fadeInTwo.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textViewTwo.setVisibility(View.VISIBLE);
            }
        });

        // Start the animation
        fadeInTwo.start();

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);

        // set Home selected
        bottomNavigationView.setSelectedItemId(R.id.stopwatch);

        //perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stopwatch:
                        startActivity(new Intent(getApplicationContext(),StopWatch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.alarmclock:
                        startActivity(new Intent(getApplicationContext(),AlarmClock.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.graphLine:
                        startActivity(new Intent(getApplicationContext(),graphLine.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}