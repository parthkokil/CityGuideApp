package com.example.nashik_cityguide.Bus_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nashik_cityguide.R;

public class bus_terminal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_terminal);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_busTerminal,new busTerminal_fragment()).commit();
    }
}