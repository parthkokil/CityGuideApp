package com.example.nashik_cityguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nashik_cityguide.Bus_Activity.bus_terminal;
import com.example.nashik_cityguide.Flight_Activity.flight_airport;
import com.example.nashik_cityguide.Railway_Activity.railway_station;

public class transport_category extends AppCompatActivity {

    private ImageView back_img;
    private CardView buses_cardView, railway_cardView, flight_cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_category);

        buses_cardView = findViewById(R.id.buses_cardView);
        railway_cardView = findViewById(R.id.railway_cardView);
        flight_cardView = findViewById(R.id.flight_cardView);


        buses_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(transport_category.this, bus_terminal.class);
                startActivity(intent);
            }
        });

        railway_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(transport_category.this, railway_station.class);
                startActivity(intent);
            }
        });

        flight_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(transport_category.this, flight_airport.class);
                startActivity(intent);
            }
        });


        back_img = (ImageView) findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(transport_category.this, All_categories.class);
                startActivity(intent);
            }
        });
    }
}