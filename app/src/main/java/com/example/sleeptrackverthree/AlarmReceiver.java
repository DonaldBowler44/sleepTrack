package com.example.sleeptrackverthree;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.VibratorManager;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {
    private static final long[] VIBRATE_PATTERN = {4000}; // vibrate pattern

    @Override
    public void onReceive(Context context, Intent intent) {
        VibratorManager vibratorManager = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
        if (vibratorManager != null && vibratorManager.getDefaultVibrator().hasAmplitudeControl()) {
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(VIBRATE_PATTERN, -1); // -1 means to vibrate only once
            vibratorManager.getDefaultVibrator().vibrate(vibrationEffect);
        }

        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();
    }
}

