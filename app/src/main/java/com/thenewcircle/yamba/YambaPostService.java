package com.thenewcircle.yamba;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class YambaPostService extends IntentService {

    private static final String TAG = "yamba." + YambaPostService.class.getSimpleName();
    public static final String STATUS = "status";
    public static final int NOTIFICATION_ID = 100;

    public YambaPostService() {
        super("YambaPostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String status = intent.getStringExtra(STATUS);
        Log.d(TAG, "onHandleIntent " + this);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Yamba");
        builder.setContentText("Sending " + status);
        builder.setLights(Color.rgb(255, 0,0), 500, 200);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.getNotification());
        final YambaClient client = new YambaClient("student", "passwordX");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.wtf(TAG, "interrupt");
        }
        try {
            client.postStatus(status);
            manager.cancel(NOTIFICATION_ID);
        } catch (YambaClientException e) {
            Log.e(TAG, "Unable to post " + status, e);
            builder.setContentText("Error posting: " + status + " " + e.getMessage());
            Intent activity = new Intent(this, PostActivity.class);
            activity.putExtra("status", status);
            PendingIntent pending = PendingIntent.getActivity(this, 0, activity, 0);
            builder.setContentIntent(pending);
            manager.notify(NOTIFICATION_ID, builder.getNotification());
        }

    }
}
