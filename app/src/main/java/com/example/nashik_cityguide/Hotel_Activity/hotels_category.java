package com.example.nashik_cityguide.Hotel_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class hotels_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_category);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotel,new hotel_fragment()).commit();
    }
}