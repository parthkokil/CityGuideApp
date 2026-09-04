package com.example.nashik_cityguide.signIn_signUp_Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.main_ui;

import es.dmoral.toasty.Toasty;

public class signin_signup extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);

        Button signin_button = findViewById(R.id.signin_btn);
        Button signup_button = findViewById(R.id.signup_btn);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (signin_signup.this,signin_activity.class);
                startActivity(intent);
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (signin_signup.this,signup_activity.class);
                startActivity(intent);
            }
        });

        if (!isConnected(this)){
            showInternetDialog();
        }
    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(signin_signup.this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog,findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(signin_signup.this)){
                    showInternetDialog();
                } else {
                    Toasty.success(signin_signup.this, "Reconnected Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),main_ui.class));
                }
            }
        });
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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