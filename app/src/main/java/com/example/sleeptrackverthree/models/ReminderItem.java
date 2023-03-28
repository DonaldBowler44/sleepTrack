package com.example.sleeptrackverthree.models;

public class ReminderItem {
    private String text;
    private long time;
    private boolean alarmOn;

    public ReminderItem(String text, long time, boolean alarmOn) {
        this.text = text;
        this.time = time;
        this.alarmOn = alarmOn;
    }

    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }
}
