package com.thenewcircle.yamba;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.thenewcircle.yamba.TimelineContract.Columns.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class TimelineDetailsFragment extends Fragment {
    private static final String TAG = "yamba." + TimelineDetailsFragment.class.getSimpleName();
    private TextView userNameText;
    private TextView messageText;
    private TextView createdAtText;

    public TimelineDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline_details, container, false);
        userNameText = (TextView) view.findViewById(R.id.userName);
        messageText = (TextView) view.findViewById(R.id.messageDetails);
        createdAtText = (TextView) view.findViewById(R.id.createdAt);
        return view;
    }

    public void updateView(Long id) {
        Log.d(TAG, "updateView " + id);
        if(id < 0 ) return;
        Uri uri = ContentUris.withAppendedId(TimelineContract.CONTENT_URI, id);
        Log.d(TAG, "uri = " + uri);
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

        if(!cursor.moveToFirst()) return;

        String user = cursor.getString(cursor.getColumnIndex(USER));
        String message = cursor.getString(cursor.getColumnIndex(MESSAGE));
        Long createdAt = cursor.getLong(cursor.getColumnIndex(TIME_CREATED));
        userNameText.setText(user);
        messageText.setText(message);
        createdAtText.setText(DateUtils.getRelativeTimeSpanString(createdAt));

    }
}
