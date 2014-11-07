package com.thenewcircle.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by geoff on 11/7/14.
 */
public class OnBoot extends BroadcastReceiver {
    private static final String TAG = "yamba." + OnBoot.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive On Boot");
    }
}
