package com.example.nashik_cityguide.PlaceFav_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.hotelFav_Activity.hotelFav_fragment;

public class placeFav_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_fav);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_placeFav,new place_fav_fragment()).commit();
    }
}