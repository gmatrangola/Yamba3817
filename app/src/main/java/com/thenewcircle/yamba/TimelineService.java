package com.thenewcircle.yamba;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;
import static com.thenewcircle.yamba.TimelineContract.Columns.*;

import java.util.List;

/**
 * Created by geoff on 11/5/14.
 */
public class TimelineService extends IntentService {
    private static final String TAG = "yamba." + TimelineService.class.getSimpleName();

    public TimelineService() {
        super("com.thenewcircle.yamba.TimelineService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Yamba");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("userName", null);
        String password = prefs.getString("password", null);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(username == null || password == null || username.length() ==0 || password.length() == 0) {
            builder.setContentText("No username/password. Press here.");
            builder.setAutoCancel(true);
            Intent settingsActivity = new Intent(this, SettingsActivity.class);
            PendingIntent pending = PendingIntent.getActivity(this, 0, settingsActivity, 0);
            builder.setContentIntent(pending);
            manager.notify(YambaPostService.NOTIFICATION_ID, builder.getNotification());
        }
        else {
            final YambaClient client = new YambaClient(username, password);
            try {
                List<YambaClient.Status> posts = client.getTimeline(30);
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();
                for(YambaClient.Status post : posts) {
                    values.put(ID, post.getId());
                    values.put(MESSAGE, post.getMessage());
                    values.put(TIME_CREATED, post.getCreatedAt().getTime());
                    values.put(USER, post.getUser());
                    Uri uri = resolver.insert(TimelineContract.CONTENT_URI, values);
                    Log.i(TAG, "Message: " + post.getMessage() + " User:" + post.getUser() +
                            " Uri: " + uri);

                }
            } catch (YambaClientException e) {
                Log.e(TAG, "unable to get posts");
            }
        }
    }
}
