package com.example.nashik_cityguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nashik_cityguide.signIn_signUp_Activity.signin_signup;
import com.google.firebase.auth.FirebaseAuth;

public class Splash_screen extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 4000;
    private FirebaseAuth authProfile;

    //Animations
    Animation topAnimation, bottomAnimation, midAnimation;
    TextView slogan;
    ImageView logo;
    View first, second, third, fourth, fifth, sixth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        authProfile = FirebaseAuth.getInstance();

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        midAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.fourth_line);
        fifth = findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.sixth_line);

        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        // Setting Animations
        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        logo.setAnimation(midAnimation);
        slogan.setAnimation(bottomAnimation);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (authProfile.getCurrentUser() != null){
                    startActivity(new Intent(Splash_screen.this, main_ui.class));
                    finish();
                } else{
                    Intent intent2 = new Intent(Splash_screen.this, signin_signup.class);
                    startActivity(intent2);
                    finish();
                }
            }
        },SPLASH_TIMEOUT);
    }
}