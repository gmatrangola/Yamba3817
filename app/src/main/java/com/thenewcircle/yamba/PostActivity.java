package com.thenewcircle.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;


public class PostActivity extends Activity {

    private static final String TAG = "yamba." + PostActivity.class.getSimpleName();
    private EditText messageEditText;
    private TextView charactersRemainingTextView;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        charactersRemainingTextView = (TextView) findViewById(R.id.charactersRemainingTextView);
        postButton = (Button) findViewById(R.id.postButton);
        String status = getIntent().getStringExtra("status");
        if(status != null) messageEditText.setText(status);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = messageEditText.getText().toString();
                // post status to service intent
                Intent postIntent = new Intent(PostActivity.this, YambaPostService.class);
                postIntent.putExtra(YambaPostService.STATUS, status);
                startService(postIntent);
                messageEditText.getText().clear();
                charactersRemainingTextView.setText("Sent " + status);
            }
        });

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s=" + s.toString() + ", start=" + start + " count=" + count + " after=" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: s=" + s.toString() + ", start=" + start + " count=" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: s=" + s.toString());
                int len = s.length();
                charactersRemainingTextView.setText((140 - len) + "");
            }
        });
        if(savedInstanceState != null) {
            String count = savedInstanceState.getString("count");
            if(count != null) charactersRemainingTextView.setText(count);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putString("count", charactersRemainingTextView.getText().toString());
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
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
        if (id == R.id.action_settings) {
            Intent preferencesIntent = new Intent(this, SettingsActivity.class);
            startActivity(preferencesIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
