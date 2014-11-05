package com.thenewcircle.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
    }
}
