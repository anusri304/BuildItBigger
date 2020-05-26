package com.example.androidjokes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.example.androidjokes.utils.ApplicationConstants;


public class JokeActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Bundle intent = getIntent().getExtras();
        if(intent !=null) {
            String joke = intent.getString(ApplicationConstants.JOKE);
            textView = (TextView) findViewById(R.id.textView);
            textView.setText(joke + "hello");
        }
    }
}
