package com.example.nashik_cityguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nashik_cityguide.Hotel_Activity.hotels_category;
import com.example.nashik_cityguide.Place_Activity.touristPlaces_category;
import com.example.nashik_cityguide.Restaurant_Activity.restaurant_category;


public class All_categories extends AppCompatActivity {

    private RelativeLayout transport, tourist, hotel, restaurant, emergency;
    private TextView expand1, expand2, expand3, expand4, expand5;
    private ImageView back_img;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        transport = findViewById(R.id.category_transport_expand);
        tourist = findViewById(R.id.category_tourist_expand);
        hotel = findViewById(R.id.category_hotel_expand);
        restaurant = findViewById(R.id.category_restaurant_expand);
        emergency = findViewById(R.id.category_emergency_expand);
        expand1 = findViewById(R.id.expand_1);
        expand2 = findViewById(R.id.expand_2);
        expand3 = findViewById(R.id.expand_3);
        expand4 = findViewById(R.id.expand_4);
        expand5 = findViewById(R.id.expand_5);


        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this,transport_category.class);
                startActivity(intent);
            }
        });
        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this,transport_category.class);
                startActivity(intent);
            }
        });

        tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, touristPlaces_category.class);
                startActivity(intent);
            }
        });
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, touristPlaces_category.class);
                startActivity(intent);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, hotels_category.class);
                startActivity(intent);
            }
        });
        expand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, hotels_category.class);
                startActivity(intent);
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, restaurant_category.class);
                startActivity(intent);
            }
        });
        expand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this,restaurant_category.class);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, emergency_category.class);
                startActivity(intent);
            }
        });
        expand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, emergency_category.class);
                startActivity(intent);
            }
        });

        back_img = (ImageView) findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_categories.this, main_ui.class);
                startActivity(intent);
            }
        });
    }
}