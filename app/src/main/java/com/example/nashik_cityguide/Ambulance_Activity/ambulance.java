package com.example.nashik_cityguide.Ambulance_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_fragment;
import com.example.nashik_cityguide.R;

public class ambulance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_ambulance,new ambulance_fragment()).commit();
    }
}