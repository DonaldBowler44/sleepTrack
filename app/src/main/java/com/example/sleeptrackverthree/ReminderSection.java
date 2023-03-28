package com.example.sleeptrackverthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.sleeptrackverthree.models.ReminderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReminderSection extends AppCompatActivity {

    private ImageButton pickDateBtn;
    private ListView listView;
    private ArrayList<ReminderItem> reminderItems = new ArrayList<>();
    private ReminderAdapter reminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_section);

        //remind functionality section-------
        listView = findViewById(R.id.list_view);
        reminderAdapter = new ReminderAdapter(this, reminderItems);
        listView.setAdapter(reminderAdapter);

        pickDateBtn = findViewById(R.id.button_add);
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_remind);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.reminder);

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
                    case R.id.reminder:
                        startActivity(new Intent(getApplicationContext(),ReminderSection.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void showAddItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_add_item, null);

        final EditText inputEditText = view.findViewById(R.id.edit_text_input);
        final TextView dateTextView = view.findViewById(R.id.text_view_date);
        final TimePicker timePicker = view.findViewById(R.id.time_picker);
        final Switch alarmSwitch = view.findViewById(R.id.switch_alarm);

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormat.format(calendar.getTime());
                        dateTextView.setText(date);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Add Item");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = inputEditText.getText().toString();
                String date = dateTextView.getText().toString();
                if (!input.isEmpty() && !date.isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(datePickerDialog.getDatePicker().getMinDate());
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                    calendar.set(Calendar.SECOND, 0);
                    String item = date + " - " + input;
                    ReminderItem reminderItem = new ReminderItem(item, calendar.getTimeInMillis(), alarmSwitch.isChecked());
                    reminderItems.add(reminderItem);
                    reminderAdapter.notifyDataSetChanged();

                    if (alarmSwitch.isChecked()) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        setAlarm(calendar, hour, minute, item);
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setAlarm(Calendar calendar, int hour, int minute, String item) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("item", item);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}