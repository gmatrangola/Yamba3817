package com.thenewcircle.yamba;

import android.app.IntentService;
import android.content.Intent;
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

    public YambaPostService() {
        super("YambaPostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String status = intent.getStringExtra(STATUS);
        Log.d(TAG, "onHandleIntent " + this);
        final YambaClient client = new YambaClient("student", "password");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Log.wtf(TAG, "interrupt");
        }
        try {
            client.postStatus(status);
        } catch (YambaClientException e) {
            Log.e(TAG, "Unable to post " + status, e);
        }

    }
}
