package com.example.nashik_cityguide.Flight_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.Hotel_Activity.hotel_fragment;
import com.example.nashik_cityguide.R;

public class flight_airport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_airport);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_flight,new flight_fragment()).commit();
    }
}