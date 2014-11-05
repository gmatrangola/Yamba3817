package com.thenewcircle.yamba;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by geoff on 11/4/14.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new YambaPrefsFragment()).commit();
    }
}
