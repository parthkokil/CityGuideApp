package com.example.nashik_cityguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.signIn_signUp_Activity.signup_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukeshsolanki.OtpView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class enter_otp extends AppCompatActivity {

    String verificationId;
    FirebaseAuth mAuth;
    Button verify_btn;
    TextView mobile_number;
    EditText otp_1_text,otp_2_text,otp_3_text,otp_4_text,otp_5_text,otp_6_text;

    signup_activity signup_activity;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        //editTextInput();

        mAuth = FirebaseAuth.getInstance();
        mobile_number = findViewById(R.id.mobile_number);


        otp_1_text = findViewById(R.id.otp_1_text);
        otp_2_text = findViewById(R.id.otp_2_text);
        otp_3_text = findViewById(R.id.otp_3_text);
        otp_4_text = findViewById(R.id.otp_4_text);
        otp_5_text = findViewById(R.id.otp_5_text);
        otp_6_text = findViewById(R.id.otp_6_text);

        String text_username = getIntent().getStringExtra("username");
        String text_email = getIntent().getStringExtra("email");
        String text_dob = getIntent().getStringExtra("dob");
        String text_gender = getIntent().getStringExtra("gender");
        String text_pass = getIntent().getStringExtra("pass");
        String text_mobile = getIntent().getStringExtra("mobile number");

        mobile_number.setText("+91-"+text_mobile);

        verificationId = getIntent().getStringExtra("verificationId");

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            final ProgressHandler progressHandler = new ProgressHandler(enter_otp.this);

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                progressHandler.dismiss();
                Toasty.success(enter_otp.this, "OTP sent to +91- "+text_mobile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressHandler.dismiss();
                Toasty.error(enter_otp.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + text_mobile,
                60,
                TimeUnit.SECONDS,
                enter_otp.this,
                mCallbacks);

        verify_btn = findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otp_1_text.getText().toString().isEmpty() ||
                        otp_2_text.getText().toString().isEmpty() ||
                        otp_3_text.getText().toString().isEmpty() ||
                        otp_4_text.getText().toString().isEmpty() ||
                        otp_5_text.getText().toString().isEmpty() ||
                        otp_6_text.getText().toString().isEmpty()) {
                    Toasty.error(enter_otp.this, "Please, Enter the OTP", Toast.LENGTH_SHORT).show();
                } else {
                    if (verificationId != null){
                        String code = (otp_1_text.getText().toString()+
                                otp_2_text.getText().toString()+
                                otp_3_text.getText().toString()+
                                otp_4_text.getText().toString()+
                                otp_5_text.getText().toString()+
                                otp_6_text.getText().toString());
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
                        FirebaseAuth.getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    verifyCode(code);
                                    registerUser(text_username, text_email, text_mobile, text_dob, text_gender, text_pass);
                                } else {
                                    Toasty.error(enter_otp.this, "OTP is not valid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

//    private void editTextInput() {
//        otp_1_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (s.toString().trim().isEmpty()){
//
//                    otp_2_text.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//        otp_2_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (s.toString().trim().isEmpty()){
//
//                    otp_3_text.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//        otp_3_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (s.toString().trim().isEmpty()){
//
//                    otp_4_text.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//        otp_4_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (s.toString().trim().isEmpty()){
//
//                    otp_5_text.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//        otp_5_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (s.toString().trim().isEmpty()){
//
//                    otp_6_text.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });
//    }

    private void verifyCode(String code) {
        Intent intent = new Intent(enter_otp.this, main_ui.class);
        Toasty.success(enter_otp.this, "Registration Completed", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void registerUser(String text_username, String text_email, String text_mobile, String text_dob, String text_gender, String text_pass) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(text_email, text_pass).addOnCompleteListener(enter_otp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final ProgressHandler progressHandler = new ProgressHandler(enter_otp.this);
                if (task.isSuccessful()) {
                    progressHandler.show();
                    //progress_bar.setVisibility(View.VISIBLE);
                    FirebaseUser firebaseuser = auth.getCurrentUser();

                    // Enter User Data into the Firebase Realtime Database
                    ReadWriteUserDetails writeuserdetails = new ReadWriteUserDetails(text_username, text_email, text_mobile, text_dob, text_gender, text_pass);

                    //Extracting user reference from database for "Registered Users"
                    DatabaseReference refernceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    refernceProfile.child(firebaseuser.getUid()).setValue(writeuserdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toasty.success(enter_otp.this, "Enter the OTP!", Toast.LENGTH_SHORT).show();

                                //Open Main UI after Successful registration
                                Intent intent = new Intent(enter_otp.this, main_ui.class);

                                // To prevent user from returning back to the Sign up activity if he clicks back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                progressHandler.show();
                               // progress_bar.setVisibility(View.VISIBLE);
                                finish(); //To close the SignUp Activity
                            } else {
                                Toasty.error(enter_otp.this, "Registration Failed, Please try again", Toast.LENGTH_SHORT).show();
                            }

                            // Hide the Progress Bar
                            progressHandler.dismiss();
                            //progress_bar.setVisibility(View.GONE);
                        }
                    });

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        signup_activity.TextView_pass_layout.setError("Password too weak");
                        signup_activity.TextView_pass_layout.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        signup_activity.TextView_email_layout.setError("Invalid Email");
                        signup_activity.TextView_email_layout.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        signup_activity.TextView_email_layout.setError("User Already registered using this email");
                        signup_activity.TextView_email_layout.requestFocus();
                    } catch (Exception e) {
                        Toasty.warning(enter_otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressHandler.dismiss();
                    //progress_bar.setVisibility(View.GONE);
                }
            }
        });
    }
}