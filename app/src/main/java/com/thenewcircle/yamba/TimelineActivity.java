package com.thenewcircle.yamba;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class TimelineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // simple layout with fragment
        setContentView(R.layout.activity_timeline);
        // Update the database from the web service
        Intent refreshIntent = new Intent(this, TimelineService.class);
        startService(refreshIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set up the fragment held by the activity layout
        TimelineFragment timelineFragment = new TimelineFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.fragment_container, timelineFragment);
        tx.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent preferencesIntent = new Intent(this, SettingsActivity.class);
                startActivity(preferencesIntent);
                return true;
            case R.id.refresh:
                Intent refreshIntent = new Intent(this, TimelineService.class);
                startService(refreshIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
