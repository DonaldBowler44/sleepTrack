package com.example.sleeptrackverthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class graphLine extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_line);

        db = FirebaseFirestore.getInstance();

        // Retrieve data from Firestore and load it into variables for the chart
        db.collection("Records").document("time").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Retrieve the data from document
                            String positionStr = documentSnapshot.getString("1");
                            String positionStrTwo = documentSnapshot.getString("2");
                            String positionStrThree = documentSnapshot.getString("3");
                            int positionInt = Integer.parseInt(positionStr);
                            int positionIntTwo = Integer.parseInt(positionStrTwo);
                            int positionIntThree = Integer.parseInt(positionStrThree);


                            //Create the chart entries
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            entries.add(new BarEntry(1f, positionInt));
                            entries.add(new BarEntry(2f, positionIntTwo));
                            entries.add(new BarEntry(3f, positionIntThree));

                            //Set up the chart data
                            BarDataSet dataSet = new BarDataSet(entries, "label");
                            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            BarData data = new BarData(dataSet);

                            // Set up the chart view
                            BarChart chart = findViewById(R.id.idBarChart);
                            chart.setData(data);
                            chart.invalidate();
                        } else {
                            // Handle case if document doesn't exist
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("graphLine", "Error getting document" + e);
                    }
                });






        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_GraphLi);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.stopwatch);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.stopwatch:
                        startActivity(new Intent(getApplicationContext(),StopWatch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.graphLine:
                        startActivity(new Intent(getApplicationContext(),graphLine.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.alarmclock:
                        startActivity(new Intent(getApplicationContext(),AlarmClock.class));
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



}