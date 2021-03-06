package com.thenewcircle.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static com.thenewcircle.yamba.TimelineContract.Columns.*;
/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {


    private SimpleCursorAdapter adapter;
    // Mapping between the Database and the Row Layout
    private static final String[] FROM = {MESSAGE,      USER};
    private static final int[] TO =      {R.id.message, R.id.userName};

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_timeline, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                Intent refreshIntent = new Intent(getActivity(), TimelineService.class);
                getActivity().startService(refreshIntent);
                return true;
            case R.id.postActivity:
                Intent postIntent = new Intent(getActivity(), PostActivity.class);
                getActivity().startActivity(postIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Adapt cursor between the database columns and the fields in the row layout
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.row_friend_status, null, FROM, TO,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // This is what I forgot to do. Set the adapter to the list
        setListAdapter(adapter);
        // Initlize access to the database/content provider
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Loader Manager tells us to create a loader - providing the CONTENT_URI so that it can
        // find our content provider
        return new CursorLoader(getActivity(), TimelineContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // this is called every time data is changed
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // called when connection to content provider is lost - should not happen
        adapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TimelineActivity activity = (TimelineActivity) getActivity();
        activity.updateDetails(id);
    }
}
