package com.example.nashik_cityguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nashik_cityguide.Ambulance_Activity.ambulance;
import com.example.nashik_cityguide.FireBrigade_Activity.fire_brigade;
import com.example.nashik_cityguide.PoliceStation_Activity.police_station;

public class emergency_category extends AppCompatActivity {

    private ImageView back_img;
    private CardView police_cardView, ambulance_cardView, fire_cardView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_category);

        back_img = (ImageView) findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emergency_category.this, main_ui.class);
                startActivity(intent);
            }
        });

        police_cardView = findViewById(R.id.police_cardView);
        ambulance_cardView = findViewById(R.id.ambulance_cardView);
        fire_cardView = findViewById(R.id.fire_cardView);

        police_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emergency_category.this, police_station.class);
                startActivity(intent);
            }
        });

        ambulance_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emergency_category.this, ambulance.class);
                startActivity(intent);
            }
        });

        fire_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emergency_category.this, fire_brigade.class);
                startActivity(intent);
            }
        });
    }
}