package com.example.nashik_cityguide.Place_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class touristPlaces_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_places_category);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new place_fragment()).commit();
    }
}