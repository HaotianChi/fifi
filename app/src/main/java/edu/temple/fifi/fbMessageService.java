package edu.temple.fifi;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class fbMessageService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent("fbMessage");
        intent.putExtra("Message", remoteMessage.getNotification().getBody());
        intent.putExtra("Time", (Long.toString(remoteMessage.getSentTime())));
        broadcaster.sendBroadcast(intent);

        //log message to log file
        Context context = getApplicationContext();
        edu.temple.fifi.loghandler log = new edu.temple.fifi.loghandler();
        log.appendLog(context, remoteMessage.getNotification().getBody(), (Long.toString(remoteMessage.getSentTime())));


        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }
}
