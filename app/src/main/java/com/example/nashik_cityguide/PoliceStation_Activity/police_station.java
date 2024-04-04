package com.example.nashik_cityguide.PoliceStation_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.Ambulance_Activity.ambulance_fragment;

public class police_station extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_station);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_policeStation,new policeStation_fragment()).commit();
    }
}