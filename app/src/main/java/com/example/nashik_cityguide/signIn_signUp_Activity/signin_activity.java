package com.example.nashik_cityguide.signIn_signUp_Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.ProgressHandler;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.main_ui;
import com.example.nashik_cityguide.recover_pass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

import es.dmoral.toasty.Toasty;

public class signin_activity extends AppCompatActivity {

    private ImageView back_img;
    private EditText email_login, pass_login;
    private Button sign_in_btn;
    private TextView pass_recover;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;
    private ProgressDialog pd;

    FirebaseAuth firebaseAuth;

    private static final String TAG = "signin_activity";


    // For Using Google Image Button
    GoogleSignInOptions gso;
    private ImageView google_signIn, twitter_signIn;
    private FirebaseAuth mAuth;
    private GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final ProgressHandler progressHandler = new ProgressHandler(signin_activity.this);

        back_img = (ImageView) findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin_activity.this, signin_signup.class);
                startActivity(intent);
            }
        });

        authProfile = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        // Recover Password Section
        pass_recover = (TextView) findViewById(R.id.pass_recover);
        pass_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin_activity.this, recover_pass.class);
                startActivity(intent);
            }
        });
        // Recover pass ENDS here



        // Authentication Section
        progressBar = findViewById(R.id.progress_bar);
        email_login = findViewById(R.id.login_email);
        pass_login = findViewById(R.id.login_password);
        sign_in_btn = findViewById(R.id.signin_btn);
        google_signIn = findViewById(R.id.google_btn);
        twitter_signIn = findViewById(R.id.twitter_btn);

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtain Entered Data
                String text_email = email_login.getText().toString();
                String text_pass = pass_login.getText().toString();

                // Validation
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
                    loginUser(text_email,text_pass);
                }
            }
        });progressHandler.dismiss();


        // Method of Google Sign In
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        client = GoogleSignIn.getClient(this,options);
        google_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });
        // Ends Here


        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
        // Localize to French.
        provider.addCustomParameter("lang", "en");
        twitter_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Twitter Login Starts Here
                Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(signin_activity.this, main_ui.class) );
                                            Toasty.success(signin_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toasty.error(signin_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                } else {
                    // There's no pending result so you need to start the sign-in flow.
                    // See below.
                }

                firebaseAuth
                        .startActivityForSignInWithProvider(/* activity= */ signin_activity.this, provider.build())
                        .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        startActivity(new Intent(signin_activity.this,main_ui.class) );
                                        Toasty.success(signin_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toasty.error(signin_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                twitter_signIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        // Twitter Method ENDS Here

    }


    // Method for Google signIn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),main_ui.class);
                            startActivity(intent);
                        } else {
                            Toasty.error(signin_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e){
                e.printStackTrace();
            }
        }
    }
    // Ends Here



    // Method for Checking User email and password from Database
    private void loginUser(String text_email, String text_pass) {
        final ProgressHandler progressHandler = new ProgressHandler(signin_activity.this);
        authProfile.signInWithEmailAndPassword(text_email, text_pass).addOnCompleteListener(signin_activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toasty.success(signin_activity.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signin_activity.this, main_ui.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        email_login.setError("User not registered, Please SignUp");
                        email_login.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        email_login.setError("Invalid Credentials. Kindle check and re-enter");
                        email_login.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toasty.warning(signin_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }progressHandler.dismiss();

                    Toasty.error(signin_activity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                progressHandler.dismiss();
            }
        });
    }
    // Login User Method Ended here
}