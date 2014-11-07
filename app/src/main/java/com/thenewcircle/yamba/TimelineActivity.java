package com.thenewcircle.yamba;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class TimelineActivity extends Activity {

    private TimelineDetailsFragment detailsFragment;

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
        FrameLayout detailsContainer = (FrameLayout) findViewById(R.id.details_container);
        if(detailsContainer != null) {
            detailsFragment = new TimelineDetailsFragment();
            tx.replace(R.id.details_container, detailsFragment, "details");
        }
        else {
            detailsFragment = null;
        }
        tx.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateDetails(Long id) {
        if(detailsFragment != null) detailsFragment.updateView(id);
        else {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            TimelineDetailsFragment details = new TimelineDetailsFragment();
            ft.replace(R.id.fragment_container, details);
            details.updateView(id);
            ft.addToBackStack("details");
            ft.commit();
        }
    }
}
