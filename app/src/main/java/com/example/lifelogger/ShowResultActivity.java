package com.example.lifelogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ShowResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
    }


    public void goMaps (View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
