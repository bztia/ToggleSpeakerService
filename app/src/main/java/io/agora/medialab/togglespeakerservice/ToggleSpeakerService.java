package io.agora.medialab.togglespeakerservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ToggleSpeakerService extends Service {
    private static final String TAG = "ToggleSpeakerService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ToggleSpeakerService.onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "ToggleSpeakerService.onCreate()");
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "service_toggle_speaker_notification_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "ToggleSpeakerServiceNotificationChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Toggle Speaker Status Service")
                    .setContentText("ToggleSpeaderStatus Service is running...").build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "ToggleSpeakerService.onStartCommand()");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        boolean is_on = audioManager.isSpeakerphoneOn();
        audioManager.setSpeakerphoneOn(!is_on);
        return super.onStartCommand(intent, flags, startId);
    }
}
