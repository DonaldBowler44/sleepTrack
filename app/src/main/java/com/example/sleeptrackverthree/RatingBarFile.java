package com.example.sleeptrackverthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RatingBarFile extends AppCompatActivity {
    RatingBar rb;
    Button button;
    Button doneButton;

    // init Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //date init
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
    String formattedDate = now.format(formatter);
    Map<String, Object> starRate = new HashMap<>();
    Map<String, Object> radioDesc = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);

        rb = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.RBButton);
        doneButton = (Button) findViewById(R.id.doneButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rated = "Rate :" + rb.getRating();
                Toast.makeText(RatingBarFile.this, rated, Toast.LENGTH_SHORT).show();

                starRate.put(formattedDate, rated);

                db.collection("starRatings").document("rate")
                        .update(starRate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("RatingBarFile", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("RatingBarFile", "Error writing document", e);
                            }
                        });
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), StopWatch.class);
                        startActivity(i);

            }
        });

        // Init the RadioGroup
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RGRB);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (radioButton != null && radioButton.isChecked()) {
                    onRadioButtonClicked(radioButton);
                }
            }
        });




    }

    public void onRadioButtonClicked(View view){
        String pOneText="Well Rested";
        String pTwoText="Woke Up Drowsy";
        String pThreeText="Did Not Sleep";
        String pFourText="Slept Only Few Hours";

        // Is button checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        //well Rested, Woke Up Drowsy, Did Not Sleep, Slept only few hours
        switch (view.getId()) {
            case R.id.pickOne:
                if (checked) {
                    Toast.makeText(RatingBarFile.this, pOneText, Toast.LENGTH_SHORT).show();
                    Log.d("RatingBarFile", "Radio button 1 clicked");

                    radioDesc.put(formattedDate, pOneText);

                    db.collection("sleepradioB").document("Desc")
                            .update(radioDesc)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("RatingBarFile", "DocumentSnapshot successfully written!");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("RatingBarFile", "Error writing document", e);
                                }
                            });
                }
                break;
            case R.id.pickTwo:
                if (checked) {
                    Toast.makeText(RatingBarFile.this, pTwoText, Toast.LENGTH_SHORT).show();
                    Log.d("RatingBarFile", "Radio button 2 clicked");

                    radioDesc.put(formattedDate, pTwoText);

                    db.collection("sleepradioB").document("Desc")
                            .update(radioDesc)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("RatingBarFile", "DocumentSnapshot successfully written!");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("RatingBarFile", "Error writing document", e);
                                }
                            });
                }
                break;
            case R.id.pickThree:
                if (checked) {
                    //options
                    Toast.makeText(RatingBarFile.this, pThreeText, Toast.LENGTH_SHORT).show();
                    Log.d("RatingBarFile", "Radio button 3 clicked");

                    radioDesc.put(formattedDate, pThreeText);

                    db.collection("sleepradioB").document("Desc")
                            .update(radioDesc)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("RatingBarFile", "DocumentSnapshot successfully written!");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("RatingBarFile", "Error writing document", e);
                                }
                            });
                }
                break;
            case R.id.pickFour:
                if (checked) {
                    //options
                    Toast.makeText(RatingBarFile.this, pFourText, Toast.LENGTH_SHORT).show();
                    Log.d("RatingBarFile", "Radio button 4 clicked");

                    radioDesc.put(formattedDate, pFourText);

                    db.collection("sleepradioB").document("Desc")
                            .update(radioDesc)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("RatingBarFile", "DocumentSnapshot successfully written!");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("RatingBarFile", "Error writing document", e);
                                }
                            });
                }
                break;
        }
    }


}