package com.example.nashik_cityguide.Update_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.nashik_cityguide.main_ui;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class update_profile extends AppCompatActivity {

    private EditText update_name, dp1, update_phone, update_Email, update_Password;
    private Button update_profile_btn;
    private String textName, textDob, textGender, textMobile, textEmail, textPass;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    private ImageView back_img;

    String[] items = {"Male", "Female", "Others"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        progressBar = findViewById(R.id.progress_bar);

        update_name = findViewById(R.id.update_username);
        update_phone = findViewById(R.id.update_Mobile);
        update_Email = findViewById(R.id.update_Email);
        update_Password = findViewById(R.id.update_Password);
        update_profile_btn = findViewById(R.id.update_profile_btn);
        back_img = findViewById(R.id.back_arrow);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        // Gender Section
        autoCompleteTxt = findViewById(R.id.update_gender_selection);
        adapterItems = new ArrayAdapter<String>(this, R.layout.gender_dropdown, items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        // Date Of Birth Section
        dp1 = findViewById(R.id.update_dob_selection);
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
                new DatePickerDialog(update_profile.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Back Arrow Image Intent
        back_img = (ImageView) findViewById(R.id.back_arrow);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_profile.this, main_ui.class);
                startActivity(intent);
                finish();
            }
        });

        //Update Button Method
        update_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });

        // Update Method
        showProfile(firebaseUser);

    }

    private void updateProfile(FirebaseUser firebaseUser) {

        final ProgressHandler progressHandler = new ProgressHandler(update_profile.this);

        // Validate Mobile Number
        String mobileRegex = "[6-9][0-9]{9}";
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textMobile);

        // Validation
        if (textGender.isEmpty()){
            autoCompleteTxt.requestFocus();
            autoCompleteTxt.setError("Enter your Gender");
        } else if (textMobile.length() != 10) {
            update_phone.requestFocus();
            update_phone.setError("Mobile Number Should Be of 10 Digits");
        } else if (textDob.isEmpty()) {
            dp1.requestFocus();
            dp1.setError("Enter Date of Birth");
        } else if (!mobileMatcher.find()) {
            update_phone.requestFocus();
            update_phone.setError("Mobile Number is not valid");
        } else if (textName.isEmpty()) {
            update_name.setError("Enter the Username");
            update_name.requestFocus();
        } else {

            // Obtain Entered Data
            textName = update_name.getText().toString();
            textMobile = update_phone.getText().toString();
            textDob = dp1.getText().toString();
            textGender = autoCompleteTxt.getText().toString();

            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textName,textEmail, textMobile,textDob, textGender,textPass);

            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

            String userID = firebaseUser.getUid();

            progressHandler.show();

            referenceProfile.child(userID).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(textName).build();
                        firebaseUser.updateProfile(profileUpdate);

                        Toasty.success(update_profile.this, "Update Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(update_profile.this, main_ui.class);
                        // To prevent user from returning back to the Sign up activity if he clicks back button
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toasty.warning(update_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressHandler.dismiss();
                }
            });
        }
    }

    private void showProfile(FirebaseUser firebaseUser) {
        final ProgressHandler progressHandler = new ProgressHandler(update_profile.this);
        String userIDofRegistered = firebaseUser.getUid();

        // Extracting User Details from Database
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

        progressHandler.show();

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails != null){
                    textName = readWriteUserDetails.username;
                    textDob = readWriteUserDetails.dob;
                    textGender = readWriteUserDetails.gender;
                    textMobile = readWriteUserDetails.mobile;
                    textEmail = firebaseUser.getEmail();
                    textPass = readWriteUserDetails.pass;

                    update_Password.setEnabled(false);
                    update_Email.setEnabled(false);

                    update_Password.setText(textPass);
                    update_Email.setText(textEmail);
                    update_name.setText(textName);
                    update_phone.setText(textMobile);
                    dp1.setText(textDob);
                    autoCompleteTxt.setText(textGender);
                } else {
                    Toasty.error(update_profile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                progressHandler.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(update_profile.this, "Update Profile Cancelled", Toast.LENGTH_SHORT).show();
                progressHandler.dismiss();
            }
        });
    }
}