package com.example.nashik_cityguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nashik_cityguide.signIn_signUp_Activity.signin_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import es.dmoral.toasty.Toasty;

public class recover_pass extends AppCompatActivity {

    private ImageView back_img;
    private EditText email_recover;
    private Button recover_btn;
    private FirebaseAuth authProfile;
    private ProgressDialog progressDialog;
    private static final String TAG = "recover_pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);

        authProfile = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);

        back_img = findViewById(R.id.back_arrow);
        email_recover = findViewById(R.id.login_email);
        recover_btn = findViewById(R.id.recover_btn);

        back_img.setOnClickListener(v -> finish());

        recover_btn.setOnClickListener(v -> {
            String text_email = email_recover.getText().toString().trim();

            if (text_email.isEmpty()) {
                email_recover.requestFocus();
                email_recover.setError("Field Cannot Be Empty");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()) {
                email_recover.requestFocus();
                email_recover.setError("Invalid Email");
            } else {
                resetPassword(text_email);
            }
        });
    }

    private void resetPassword(String text_email) {
        progressDialog.show();
        authProfile.sendPasswordResetEmail(text_email)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toasty.success(this, "Password reset link sent to your email", Toast.LENGTH_LONG).show();
                        // Return to login screen after short delay
                        new android.os.Handler().postDelayed(
                                () -> finish(),
                                2000
                        );
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            email_recover.setError("Email does not exist or is not valid");
                            email_recover.requestFocus();
                        } catch (Exception e) {
                            Log.e(TAG, "Password reset error: " + e.getMessage());
                            Toasty.error(this, "Failed to send reset email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}