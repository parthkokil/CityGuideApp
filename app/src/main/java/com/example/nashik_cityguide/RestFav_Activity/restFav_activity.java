package com.example.nashik_cityguide.RestFav_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class restFav_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_fav);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_restFav,new restFav_fragment()).commit();
    }
}

