package com.example.nashik_cityguide;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.Fragment_Activity.fav_fragment;
import com.example.nashik_cityguide.Fragment_Activity.home_fragment;
import com.example.nashik_cityguide.Fragment_Activity.profile_fragment;

import es.dmoral.toasty.Toasty;

public class main_ui extends AppCompatActivity {

    private int selectedTab = 1;
    private DoubleBackPressExitHandler doubleBackPressExitHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);



        doubleBackPressExitHandler = new DoubleBackPressExitHandler(this);

        final LinearLayout homelayout = findViewById(R.id.home_layout);
        final LinearLayout favlayout = findViewById(R.id.fav_layout);
        final LinearLayout profilelayout = findViewById(R.id.profile_layout);

        final ImageView homeimage = findViewById(R.id.home_image);
        final ImageView favimage = findViewById(R.id.fav_image);
        final ImageView profileimage = findViewById(R.id.profile_image);

        final TextView hometext = findViewById(R.id.home_text);
        final TextView favtext = findViewById(R.id.fav_text);
        final TextView profiletext = findViewById(R.id.profile_text);

        //set home fragment as default
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, home_fragment.class, null).commit();


        homelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 1){
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, home_fragment.class, null).commit();


                    favtext.setVisibility(View.GONE);
                    profiletext.setVisibility(View.GONE);

                    favimage.setImageResource(R.drawable.nav_fav);
                    profileimage.setImageResource(R.drawable.nav_person);

                    favlayout.setBackgroundColor(ContextCompat.getColor(main_ui.this, android.R.color.transparent));
                    profilelayout.setBackgroundColor(ContextCompat.getColor(main_ui.this, android.R.color.transparent));

                    // Select Home Tab
                    hometext.setVisibility(View.VISIBLE);
                    homeimage.setImageResource(R.drawable.nav_home_selected);
                    homelayout.setBackgroundResource(R.drawable.round_back_home);

                    // Set Animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homelayout.startAnimation(scaleAnimation);

                    // Set first tab as selected
                    selectedTab = 1;
                }
            }
        });

        favlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 2){
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, fav_fragment.class, null).commit();


                    hometext.setVisibility(View.GONE);
                    profiletext.setVisibility(View.GONE);

                    homeimage.setImageResource(R.drawable.nav_home);
                    profileimage.setImageResource(R.drawable.nav_person);

                    homelayout.setBackgroundColor(ContextCompat.getColor(main_ui.this, android.R.color.transparent));
                    profilelayout.setBackgroundColor(ContextCompat.getColor(main_ui.this, android.R.color.transparent));

                    // Select Fav Tab
                    favtext.setVisibility(View.VISIBLE);
                    favimage.setImageResource(R.drawable.nav_fav_selected);
                    favlayout.setBackgroundResource(R.drawable.round_back_fav);

                    // Set Animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    favlayout.startAnimation(scaleAnimation);

                    // Set first tab as selected
                    selectedTab = 2;
                }
            }
        });

        profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3){
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer, profile_fragment.class, null).commit();


                    hometext.setVisibility(View.GONE);
                    favtext.setVisibility(View.GONE);

                    homeimage.setImageResource(R.drawable.nav_home);
                    favimage.setImageResource(R.drawable.nav_fav);

                    homelayout.setBackgroundColor(ContextCompat.getColor(main_ui.this, android.R.color.transparent));
                    favlayout.setBackgroundColor(ContextCompat.getColor(main_ui.this, android.R.color.transparent));

                    // Select Profile Tab
                    profiletext.setVisibility(View.VISIBLE);
                    profileimage.setImageResource(R.drawable.nav_person_selected);
                    profilelayout.setBackgroundResource(R.drawable.round_back_profile);

                    // Set Animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profilelayout.startAnimation(scaleAnimation);

                    // Set first tab as selected
                    selectedTab = 3;
                }
            }
        });

        if (!isConnected()){
            showInternetDialog();
        }

        // Modern back press handling (replaces deprecated onBackPressed())
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                doubleBackPressExitHandler.onBackPressed();
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(main_ui.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog,findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected()){
                    showInternetDialog();
                } else {
                    Toasty.success(main_ui.this, "Reconnected Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),main_ui.class));
                }
            }
        });
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) return false;

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (capabilities == null) return false;

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
    }
}