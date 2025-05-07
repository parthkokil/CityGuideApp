package com.example.nashik_cityguide.signIn_signUp_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.main_ui;
import com.example.nashik_cityguide.recover_pass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class signin_activity extends AppCompatActivity {

    private ImageView back_img;
    private EditText email_login, pass_login;
    private Button sign_in_btn;
    private TextView pass_recover;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "signin_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        back_img = findViewById(R.id.back_arrow);
        email_login = findViewById(R.id.login_email);
        pass_login = findViewById(R.id.login_password);
        sign_in_btn = findViewById(R.id.signin_btn);
        pass_recover = findViewById(R.id.pass_recover);

        // Back button click listener
        back_img.setOnClickListener(v -> {
            finish(); // Simply finish current activity to go back
        });

        // Recover password click listener
        pass_recover.setOnClickListener(v -> {
            startActivity(new Intent(signin_activity.this, recover_pass.class));
        });

        // Sign-In Button click listener
        sign_in_btn.setOnClickListener(v -> {
            String text_email = email_login.getText().toString().trim();
            String text_pass = pass_login.getText().toString().trim();

            if (text_email.isEmpty()) {
                email_login.requestFocus();
                email_login.setError("Field Cannot Be Empty");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()) {
                email_login.requestFocus();
                email_login.setError("Invalid Email");
            } else if (text_pass.isEmpty()) {
                pass_login.requestFocus();
                pass_login.setError("Enter the Password");
            } else {
                loginUser(text_email, text_pass);
            }
        });
    }

    private void loginUser(String text_email, String text_pass) {
        firebaseAuth.signInWithEmailAndPassword(text_email, text_pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                // Email is verified, proceed to main activity
                                Toasty.success(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(signin_activity.this, main_ui.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toasty.warning(this, "Please verify your email first", Toast.LENGTH_LONG).show();
                                firebaseAuth.signOut();
                            }
                        }
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            email_login.setError("User not registered, Please SignUp");
                            email_login.requestFocus();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            email_login.setError("Invalid Credentials. Please check and re-enter");
                            email_login.requestFocus();
                        } catch (Exception e) {
                            Log.e(TAG, "Login error: " + e.getMessage());
                            Toasty.error(this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}