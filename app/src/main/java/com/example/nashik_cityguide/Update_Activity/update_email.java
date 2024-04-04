package com.example.nashik_cityguide.Update_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.ProgressHandler;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.change_password;
import com.example.nashik_cityguide.main_ui;
import com.example.nashik_cityguide.signIn_signUp_Activity.signin_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class update_email extends AppCompatActivity {

    private EditText textView_current_email, textView_current_pass, textView_new_email;
    private Button auth_btn, update_email_btn;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private TextView authenticate_text;
    private String userOldEmail, userNewEmail, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        textView_current_email = findViewById(R.id.current_email);
        textView_current_pass = findViewById(R.id.password_check);
        textView_new_email = findViewById(R.id.update_email);
        auth_btn = findViewById(R.id.auth_btn);
        update_email_btn = findViewById(R.id.update_btn);
        authenticate_text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progress_bar);

        update_email_btn.setEnabled(false);
        textView_new_email.setEnabled(false);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        userOldEmail = firebaseUser.getEmail();
        textView_current_email.setText(userOldEmail);

        if (firebaseUser.equals("")){
            Toasty.warning(update_email.this, "Something went Wrong! User Details not available", Toast.LENGTH_SHORT).show();
        } else {
            reAuthenticate(firebaseUser);
        }

    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        auth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressHandler progressHandler = new ProgressHandler(update_email.this);

                userPass = textView_current_pass.getText().toString();

                if(TextUtils.isEmpty(userPass)){
                    textView_current_pass.setError("Field cannot be empty");
                    textView_current_pass.requestFocus();
                } else {
                    progressHandler.show();

                    AuthCredential credential = EmailAuthProvider.getCredential(userOldEmail, userPass);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressHandler.dismiss();

                                textView_new_email.setEnabled(true);
                                update_email_btn.setEnabled(true);
                                auth_btn.setEnabled(true);
                                textView_current_pass.setEnabled(true);

                                authenticate_text.setText("You have successfully verified your email! Now you can update your email.");
                                authenticate_text.setTextColor(ContextCompat.getColorStateList(update_email.this,R.color.light_green));

                                update_email_btn.setBackgroundTintList(ContextCompat.getColorStateList(update_email.this,R.color.dark_green));

                                update_email_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userNewEmail = textView_new_email.getText().toString();
                                        if (TextUtils.isEmpty(userNewEmail)){
                                            textView_new_email.setError("Field Cannot be empty");
                                            textView_new_email.requestFocus();
                                        } else if (!Patterns.EMAIL_ADDRESS.matcher(userNewEmail).matches()) {
                                            textView_new_email.setError("Invalid Email");
                                            textView_new_email.requestFocus();
                                        } else if (userOldEmail.matches(userNewEmail)){
                                            textView_new_email.setError("New email cannot be same as the Old");
                                            textView_new_email.requestFocus();
                                        } else {
                                            progressHandler.show();
                                            updateEmail(firebaseUser);
                                        }
                                    }
                                });
                            } else {
                                try {
                                    throw task.getException();
                                } catch (Exception e){
                                    textView_current_pass.setError("Invalid Password");
                                    textView_current_pass.requestFocus();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateEmail(FirebaseUser firebaseUser) {
        firebaseUser.updateEmail(userNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                final ProgressHandler progressHandler = new ProgressHandler(update_email.this);
                if (task.isComplete()){
                    firebaseUser.sendEmailVerification();
                    Toasty.success(update_email.this, "Email has been Updated. Please Verify your new Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(update_email.this, main_ui.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e){
                        Toasty.error(update_email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressHandler.dismiss();
            }
        });
    }
}