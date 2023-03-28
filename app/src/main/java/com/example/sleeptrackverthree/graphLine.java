package com.example.sleeptrackverthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.graphics.Color;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class graphLine extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // to show log and star rate
    TextView sleepLogV;

    //String sleepLogRTextOne;
    //String sleepLogRTextTwo;
    //String sleepLogRTextThree = "";

    private ArrayList<String> sleepLogRTextOneList = new ArrayList<>();
    private ArrayList<String> sleepLogRTextTwoList = new ArrayList<>();
    //private ArrayList<String> getSleepLogRTextThreeList = new ArrayList<>();
    private ArrayList<String> getFieldDates = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_line);

        //init text view objects
        sleepLogV = (TextView) findViewById(R.id.StarRateText);



        db.collection("Records").document("time").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            List<String> fieldNames = new ArrayList<>(documentSnapshot.getData().keySet());
                            Collections.sort(fieldNames);
                            float i = 0.0f;
                            ArrayList<BarEntry> entries = new ArrayList<>();
                            for(String fieldName : fieldNames ) {
                                Object value = documentSnapshot.get(fieldName);
                                if( value instanceof String) {
                                    String stringValue = (String) value;
                                    Log.d("graphLine", fieldName + ": (this is value >) " + stringValue);
                                    entries.add(new BarEntry(i, Float.parseFloat(stringValue)));
                                    i++;

                                }
                            }

                            BarDataSet dataSet = new BarDataSet(entries, "Recorded Sleep Times");
                            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            BarData data = new BarData(dataSet);

                            // Set up the chart view
                            BarChart chart = findViewById(R.id.idBarChart);
                            chart.setData(data);
                            chart.invalidate();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("graphLine", "Error getting document", e);
                    }
                });

        sleepLogVFun();

        //sleepLogRTextThree = sleepLogRTextOne + " " + sleepLogRTextTwo;



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_GraphLi);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.graphLine);

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
                    case R.id.reminder:
                        startActivity(new Intent(getApplicationContext(),ReminderSection.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }

    void sleepLogVFun() {
        // get data from database and then store it into a string


        db.collection("sleepradioB").document("Desc").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            List<String> fieldNames = new ArrayList<>(documentSnapshot.getData().keySet());
                            Collections.sort(fieldNames);
                            for(String fieldName : fieldNames ) {
                                Object value = documentSnapshot.get(fieldName);
                                if( value instanceof String) {
                                    String stringValue = (String) value;
                                    Log.d("graphLine", fieldName + ": (this is desc value >) " + stringValue);
                                    //sleepLogRTextOne = stringValue;
                                    //Log.d("graphLine", fieldName + ": (this is sLRT1 value >) " + sleepLogRTextOne);
                                    sleepLogRTextOneList.add(stringValue);
                                    sleepLogVFuncTwo();


                                }
                            }

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("graphLine", "Error getting document", e);
                    }
                });



    }

    void sleepLogVFuncTwo() {
        db.collection("starRatings").document("rate").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            List<String> fieldNames = new ArrayList<>(documentSnapshot.getData().keySet());
                            Collections.sort(fieldNames);
                            for(String fieldName : fieldNames ) {
                                Object value = documentSnapshot.get(fieldName);
                                if( value instanceof String) {
                                    String stringValueSR = (String) value;
                                    Log.d("graphLine", fieldName + ": (this is StarRate value >) " + stringValueSR);
                                    //sleepLogRTextTwo = stringValueSR;
                                    //Log.d("graphLine", fieldName + ": (this is sLRT2 value >) " + sleepLogRTextTwo);
                                    //sleepLogRTextThree = fieldName + ": " + sleepLogRTextOne + "| " + sleepLogRTextTwo;
                                    //Log.d("graphLine", " this is a new value : " + sleepLogRTextThree);
                                    sleepLogRTextTwoList.add(stringValueSR);
                                    getFieldDates.add(fieldName);
                                    //sleepLogV.setText(sleepLogRTextThree);

                                }
                            }

                        }
                        updateSleepLogTextView();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("graphLine", "Error getting document", e);
                    }
                });

    }

    private void updateSleepLogTextView() {
        HashSet<String> getSleepLogRTextThreeList = new HashSet<>();
        for(int i = 0; i < sleepLogRTextOneList.size(); i++) {
            String sLRTO = sleepLogRTextOneList.get(i);
            String sLRTT = sleepLogRTextTwoList.get(i);
            String fNS = getFieldDates.get(i);
            String sLRTThree = fNS + ": " + sLRTO + " | " + sLRTT;
            getSleepLogRTextThreeList.add(sLRTThree);
        }
        String sleepLogText = TextUtils.join("\n\n", getSleepLogRTextThreeList);
        Log.d("graphLine", ": (this is SleepLogText value >) " + sleepLogText);
        sleepLogV.setText(sleepLogText);

    }



}