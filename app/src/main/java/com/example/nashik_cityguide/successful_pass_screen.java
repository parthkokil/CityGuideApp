package com.example.nashik_cityguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class successful_pass_screen extends AppCompatActivity {

    Button dashboard_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_pass_screen);

        dashboard_btn = findViewById(R.id.dashboard_btn);
        dashboard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(successful_pass_screen.this, main_ui.class);
                startActivity(intent);
                finish();
            }
        });
    }
}