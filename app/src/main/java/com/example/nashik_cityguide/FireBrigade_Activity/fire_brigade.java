package com.example.nashik_cityguide.FireBrigade_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class fire_brigade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_brigade);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_fireBrigade,new fireBrigade_fragment()).commit();
    }
}