package com.example.nashik_cityguide.signIn_signUp_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nashik_cityguide.ProgressHandler;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.ReadWriteUserDetails;
import com.example.nashik_cityguide.enter_otp;
import com.example.nashik_cityguide.main_ui;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class signup_activity extends AppCompatActivity {

    //Registration View Declaration
    private EditText TextView_username_layout;
    public EditText TextView_email_layout;
    private EditText TextView_mobile_layout;
    public EditText TextView_pass_layout;
    private EditText TextView_conformpass_layout;
    private ProgressBar progress_bar;
    private ImageView back_img;
    private Button signup_button;
    FirebaseAuth mAuth;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private static final String TAG = "signup_activity";

    String[] items = {"Male", "Female", "Others"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    EditText dp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        final ProgressHandler progressHandler = new ProgressHandler(signup_activity.this);

        // Gender Section
        autoCompleteTxt = findViewById(R.id.gender_selection);
        adapterItems = new ArrayAdapter<String>(this, R.layout.gender_dropdown, items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        // Date Of Birth Section
        dp1 = findViewById(R.id.dob_selection);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);

                updateCalender();
            }

            private void updateCalender() {
                String Format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                dp1.setText(sdf.format(calendar.getTime()));
            }
        };

        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(signup_activity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Back Arrow Image Intent
        back_img = (ImageView) findViewById(R.id.back_arrow);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup_activity.this, signin_signup.class);
                startActivity(intent);
            }
        });


        // Authentication Section
        progress_bar = findViewById(R.id.progress_bar);

        TextView_username_layout = findViewById(R.id.regusername);
        TextView_email_layout = findViewById(R.id.regEmail);
        TextView_mobile_layout = findViewById(R.id.regMobile);
        dp1 = findViewById(R.id.dob_selection);
        autoCompleteTxt = findViewById(R.id.gender_selection);
        TextView_pass_layout = findViewById(R.id.regPassword);
        TextView_conformpass_layout = findViewById(R.id.regConformPassword);

        Button signup_button = findViewById(R.id.signup_btn);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtain Entered Data
                String text_username = TextView_username_layout.getText().toString();
                String text_email = TextView_email_layout.getText().toString();
                String text_mobile = TextView_mobile_layout.getText().toString();
                String text_dob = dp1.getText().toString();
                String text_gender = autoCompleteTxt.getText().toString();
                String text_pass = TextView_pass_layout.getText().toString();
                String text_conform_pass = TextView_conformpass_layout.getText().toString();


                // Validate Mobile Number
                String mobileRegex = "[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(text_mobile);

                // Validation
                if (text_email.isEmpty()) {
                    TextView_email_layout.requestFocus();
                    TextView_email_layout.setError("Field Cannot Be Empty");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()) {
                    TextView_email_layout.requestFocus();
                    TextView_email_layout.setError("Invalid Email");
                } else if (text_mobile.length() != 10) {
                    TextView_mobile_layout.requestFocus();
                    TextView_mobile_layout.setError("Mobile Number Should Be of 10 Digits");
                } else if (text_dob.isEmpty()) {
                    dp1.requestFocus();
                    dp1.setError("Enter Date of Birth");
                } else if (text_gender.isEmpty()) {
                    autoCompleteTxt.requestFocus();
                    autoCompleteTxt.setError("Enter your Gender");
                } else if (text_pass.isEmpty()) {
                    TextView_pass_layout.requestFocus();
                    TextView_pass_layout.setError("Please set a Password");
                } else if (text_conform_pass.isEmpty()) {
                    TextView_conformpass_layout.requestFocus();
                    TextView_conformpass_layout.setError("Enter the same Password as Above");
                } else if (!text_pass.equals(text_conform_pass)) {
                    TextView_conformpass_layout.requestFocus();
                    TextView_conformpass_layout.setError("Password do not match");
                } else if (text_pass.length() < 6) {
                    TextView_pass_layout.requestFocus();
                    TextView_pass_layout.setError("Password Length should be more than 6 Digits");
                } else if (!mobileMatcher.find()) {
                    TextView_mobile_layout.requestFocus();
                    TextView_mobile_layout.setError("Mobile Number is not valid");
                } else if (text_username.isEmpty()) {
                    TextView_username_layout.setError("Enter the Username");
                    TextView_username_layout.requestFocus();
                } else {
//                    Intent intent = new Intent(signup_activity.this, enter_otp.class);
//                    intent.putExtra("mobile number",text_mobile);
//                    intent.putExtra("username",text_username);
//                    intent.putExtra("email",text_email);
//                    intent.putExtra("dob",text_dob);
//                    intent.putExtra("gender",text_gender);
//                    intent.putExtra("pass",text_pass);
//                    startActivity(intent);


                    registerUser(text_username, text_email, text_mobile, text_dob, text_gender, text_pass);
                    //otpSent();


                }
            }
        });
    }







    // Register User Using the Credentials entered
    private void registerUser(String text_name, String text_email, String text_mobile, String text_dob, String text_gender, String text_pass) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(text_email, text_pass).addOnCompleteListener(signup_activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final ProgressHandler progressHandler = new ProgressHandler(signup_activity.this);
                if (task.isSuccessful()) {
                    progressHandler.show();
                    //progress_bar.setVisibility(View.VISIBLE);
                    FirebaseUser firebaseuser = auth.getCurrentUser();

                    // Enter User Data into the Firebase Realtime Database
                    ReadWriteUserDetails writeuserdetails = new ReadWriteUserDetails(text_name, text_email, text_mobile, text_dob, text_gender, text_pass);

                    //Extracting user reference from database for "Registered Users"
                    DatabaseReference refernceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    refernceProfile.child(firebaseuser.getUid()).setValue(writeuserdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toasty.success(signup_activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                //Open Main UI after Successful registration
                                Intent intent = new Intent(signup_activity.this, main_ui.class);
                                intent.putExtra("mobile number",text_mobile);

                                // To prevent user from returning back to the Sign up activity if he clicks back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                progressHandler.show();
                               // progress_bar.setVisibility(View.VISIBLE);
                                finish(); //To close the SignUp Activity
                            } else {
                                Toasty.error(signup_activity.this, "Registration Failed, Please try again", Toast.LENGTH_SHORT).show();
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
                        TextView_pass_layout.setError("Password too weak");
                        TextView_pass_layout.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        TextView_email_layout.setError("Invalid Email");
                        TextView_email_layout.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        TextView_email_layout.setError("User Already registered using this email");
                        TextView_email_layout.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toasty.warning(signup_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressHandler.dismiss();
                    //progress_bar.setVisibility(View.GONE);
                }
            }
        });
    }
}