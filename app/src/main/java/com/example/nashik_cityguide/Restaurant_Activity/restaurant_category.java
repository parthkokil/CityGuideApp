package com.example.nashik_cityguide.Restaurant_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class restaurant_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_category);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_rest,new restaurant_fragment()).commit();
    }
}