package com.example.cpen321_m5;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private LocalBroadcastManager broadcastManager = null;

    @Override
    public void onCreate() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        handleMessage(remoteMessage);
    }

    private void handleMessage(final RemoteMessage remoteMessage) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent("MyData");
                intent.putExtra("message", remoteMessage.getData().get("text"));
                broadcastManager.sendBroadcast(intent);

                Toast.makeText(MyFirebaseMessagingService.this, getString(R.string.handle_notification_now),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
