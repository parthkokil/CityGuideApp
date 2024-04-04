package com.example.nashik_cityguide.Railway_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class railway_station extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_station);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_railway,new railwayStation_fragment()).commit();
    }
}