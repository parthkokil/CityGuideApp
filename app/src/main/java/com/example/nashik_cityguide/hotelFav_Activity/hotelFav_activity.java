package com.example.nashik_cityguide.hotelFav_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.RestFav_Activity.restFav_fragment;

public class hotelFav_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_fav);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotelFav,new hotelFav_fragment()).commit();
    }
}