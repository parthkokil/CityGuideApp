package com.example.nashik_cityguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.Fragment_Activity.profile_fragment;
import com.example.nashik_cityguide.Update_Activity.update_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class change_password extends AppCompatActivity {

    private EditText current_pass, new_pass, con_new_pass;
    private FirebaseAuth authProfile;
    private TextView authenticated;
    private Button authenticate_btn, change_pass_btn;
    private ProgressBar progressbar;
    private String user_current_pass;
    private ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        back_img = (ImageView) findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(change_password.this, profile_fragment.class);
                startActivity(intent);
            }
        });

        current_pass = findViewById(R.id.update_password);
        new_pass = findViewById(R.id.new_password);
        con_new_pass = findViewById(R.id.conform_new_password);
        authenticated = findViewById(R.id.text);
        progressbar = findViewById(R.id.progress_bar);
        authenticate_btn = findViewById(R.id.authenticate_btn);
        change_pass_btn = findViewById(R.id.change_pass_btn);

        //Disable edittext for new password and conform password and the button
        new_pass.setEnabled(false);
        con_new_pass.setEnabled(false);
        change_pass_btn.setEnabled(false);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = authProfile.getCurrentUser();

        if (firebaseuser.equals("")){
            Toasty.error(change_password.this, "Something went wrong, User detail's not available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(change_password.this, main_ui.class);
            startActivity(intent);
            finish();
        } else {
            reAuthenticateUser(firebaseuser);
        }
    }

    private void reAuthenticateUser(FirebaseUser firebaseuser) {
        authenticate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressHandler progressHandler = new ProgressHandler(change_password.this);
                user_current_pass = current_pass.getText().toString();

                if (TextUtils.isEmpty(user_current_pass)){
                    current_pass.setError("Please enter the current password to authenticate");
                    current_pass.requestFocus();
                } else {
                    progressHandler.show();

                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseuser.getEmail(),user_current_pass);

                    firebaseuser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressHandler.dismiss();

                                current_pass.setEnabled(false);
                                new_pass.setEnabled(true);
                                con_new_pass.setEnabled(true);

                                authenticate_btn.setEnabled(false);
                                change_pass_btn.setEnabled(true);

                                authenticated.setText("You have successfully authenticated your account, You can change your Password now!");
                                authenticated.setTextColor(ContextCompat.getColorStateList(change_password.this,R.color.dark_green));
                                change_pass_btn.setBackgroundTintList(ContextCompat.getColorStateList(change_password.this,R.color.dark_green));
                                change_pass_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        changePass(firebaseuser);
                                    }
                                });
                            } else {
                                try {
                                    throw task.getException();
                                } catch (Exception e){
                                    current_pass.setError("Invalid Password");
                                    current_pass.requestFocus();
                                    Toasty.warning(change_password.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressHandler.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void changePass(FirebaseUser firebaseuser) {
        String userPassNew = new_pass.getText().toString();
        String userPassConform = con_new_pass.getText().toString();

        final ProgressHandler progressHandler = new ProgressHandler(change_password.this);

        if (TextUtils.isEmpty(userPassNew)){
            new_pass.setError("Please enter the new password");
            new_pass.requestFocus();
        } else if (TextUtils.isEmpty(userPassConform)) {
            con_new_pass.setError("Please enter the same password as entered above");
            con_new_pass.requestFocus();
        } else if (!userPassNew.matches(userPassConform)){
            con_new_pass.setError("Password do not match");
            con_new_pass.requestFocus();
        } else if (user_current_pass.matches(userPassNew)){
            new_pass.setError("New password cannot be same as the Old password");
            new_pass.requestFocus();
        } else {
            progressHandler.show();

            firebaseuser.updatePassword(userPassNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toasty.success(change_password.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(change_password.this,successful_pass_screen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(change_password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}