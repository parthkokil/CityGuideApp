package com.example.nashik_cityguide.Fragment_Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.nashik_cityguide.ProgressHandler;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.ReadWriteUserDetails;
import com.example.nashik_cityguide.Update_Activity.update_email;
import com.example.nashik_cityguide.Update_Activity.update_profile;
import com.example.nashik_cityguide.change_password;
import com.example.nashik_cityguide.signIn_signUp_Activity.signin_signup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class profile_fragment extends Fragment {

    // UI Components
    private LinearLayout expandableView, expandableView_profile, expandableView_support, expandableView_about, expandableView_Follow;
    private ImageView arrow_btn, arrow_btn2, arrow_btn3, arrow_btn4, arrow_btn5;
    private ImageView update_profile_arrow, change_password_img, update_email_img;
    private ImageView chooseImageViewButton, send_email, send_privacy_policy, send_feedback, follow_Insta, follow_fb;
    private CardView profile_details, edit_profile, support_section, about_section, followus_section;
    private CircleImageView profile_image_profileActivity;
    private Button logout_btn;
    private TextView text_welcome, text_email, text_username, text_phone, text_dob, text_gender;
    private ProgressBar progressbar;

    // Firebase
    private FirebaseAuth authprofile;
    private FirebaseUser firebaseUser;
    private DatabaseReference userDatabaseRef;

    // Image Handling
    private String base64Image;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    // User Data
    private String name, email, mobilenumber, dob, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_profile = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        initializeViews(fragment_profile);
        setupFirebase();
        setupImagePicker();
        setupClickListeners(fragment_profile);
        setupAnimations(fragment_profile);

        if (firebaseUser != null) {
            showUserProfile(firebaseUser);
        } else {
            Toasty.warning(getActivity(), "Please login first", Toast.LENGTH_SHORT).show();
        }

        return fragment_profile;
    }

    private void initializeViews(View view) {
        progressbar = view.findViewById(R.id.progress_bar);
        text_welcome = view.findViewById(R.id.welcome_name);
        text_email = view.findViewById(R.id.email_txt);
        text_username = view.findViewById(R.id.username_txt);
        text_phone = view.findViewById(R.id.phone_txt);
        text_dob = view.findViewById(R.id.dob_txt);
        text_gender = view.findViewById(R.id.gender_txt);
        profile_image_profileActivity = view.findViewById(R.id.profile_image_profileActivity);
        chooseImageViewButton = view.findViewById(R.id.upload_img);
        logout_btn = view.findViewById(R.id.logout_btn);

        // Expandable sections
        expandableView = view.findViewById(R.id.ly1);
        arrow_btn = view.findViewById(R.id.down_arrow);
        profile_details = view.findViewById(R.id.profile_details_cardView);

        edit_profile = view.findViewById(R.id.edit_profile_cardView);
        expandableView_profile = view.findViewById(R.id.ly2);
        arrow_btn2 = view.findViewById(R.id.down_arrow_2);

        support_section = view.findViewById(R.id.support_cardView);
        expandableView_support = view.findViewById(R.id.ly4);
        arrow_btn4 = view.findViewById(R.id.down_arrow_4);

        about_section = view.findViewById(R.id.about_app_cardView);
        expandableView_about = view.findViewById(R.id.ly3);
        arrow_btn3 = view.findViewById(R.id.down_arrow_3);

        followus_section = view.findViewById(R.id.followus_cardView);
        expandableView_Follow = view.findViewById(R.id.ly5);
        arrow_btn5 = view.findViewById(R.id.down_arrow_5);

        // Action buttons
        update_profile_arrow = view.findViewById(R.id.update_profile_arrow);
        change_password_img = view.findViewById(R.id.change_pass_arrow);
        update_email_img = view.findViewById(R.id.update_email_arrow);
        send_email = view.findViewById(R.id.send_email_arrow);
        send_privacy_policy = view.findViewById(R.id.privacy_policy_arrow);
        send_feedback = view.findViewById(R.id.feedback_arrow);
        follow_Insta = view.findViewById(R.id.insta_arrow);
        follow_fb = view.findViewById(R.id.fb_arrow);
    }

    private void setupFirebase() {
        authprofile = FirebaseAuth.getInstance();
        firebaseUser = authprofile.getCurrentUser();

        if (firebaseUser != null) {
            userDatabaseRef = FirebaseDatabase.getInstance().getReference("Registered Users")
                    .child(firebaseUser.getUid());
        }
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        convertAndSetImage(imageUri);
                    }
                }
        );
    }

    private void setupClickListeners(View view) {
        // Back and navigation buttons
        update_profile_arrow.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), update_profile.class)));

        change_password_img.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), change_password.class)));

        update_email_img.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), update_email.class)));

        chooseImageViewButton.setOnClickListener(v -> openFileChooser());
        profile_image_profileActivity.setOnClickListener(v -> uploadImageToRealTimeDatabase());

        // Expandable sections
        arrow_btn.setOnClickListener(v -> toggleExpansion(expandableView, arrow_btn, profile_details));
        arrow_btn2.setOnClickListener(v -> toggleExpansion(expandableView_profile, arrow_btn2, edit_profile));
        arrow_btn3.setOnClickListener(v -> toggleExpansion(expandableView_about, arrow_btn3, about_section));
        arrow_btn4.setOnClickListener(v -> toggleExpansion(expandableView_support, arrow_btn4, support_section));
        arrow_btn5.setOnClickListener(v -> toggleExpansion(expandableView_Follow, arrow_btn5, followus_section));

        // Social media links
        follow_Insta.setOnClickListener(v -> gotoUrl("https://www.instagram.com/cityguide_nashik/"));
        follow_fb.setOnClickListener(v -> gotoUrl("https://www.facebook.com/profile.php?id=100092457782085&sk=about_contact_and_basic_info"));
        send_privacy_policy.setOnClickListener(v -> gotoUrl("https://docs.google.com/document/d/1lnronvwvIYugG6amg00mkhwACnX8mT5i/edit"));
        send_feedback.setOnClickListener(v -> gotoUrl("https://forms.gle/GzzL9o6TMpXLu6BS8"));

        // Email and logout
        send_email.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"cityguide.nashik@gmail.com"});
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email Client"));
        });

        logout_btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toasty.success(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), signin_signup.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void setupAnimations(View view) {
        profile_details.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.profile_cardview_animation2));
        edit_profile.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.profile_cardview_animation2));
        support_section.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.profile_cardview_animation2));
        about_section.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.profile_cardview_animation2));
        followus_section.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.profile_cardview_animation2));
        logout_btn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation));
    }

    private void toggleExpansion(View expandableView, ImageView arrow, CardView cardView) {
        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());

        if (expandableView.getVisibility() == View.GONE) {
            expandableView.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.up_arrow_image);
            arrow.animate().rotation(180f).setDuration(200).start();
        } else {
            expandableView.setVisibility(View.GONE);
            arrow.setImageResource(R.drawable.down_arrow_image);
            arrow.animate().rotation(0f).setDuration(200).start();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(intent);
    }

    private void convertAndSetImage(Uri imageUri) {
        try {
            InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);

            // First decode with inJustDecodeBounds=true to check dimensions
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 800, 800);

            // Decode bitmap with inSampleSize set
            imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();

            // Compress and convert to Base64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            // Set image preview
            profile_image_profileActivity.setImageBitmap(bitmap);

        } catch (Exception e) {
            Toasty.error(getContext(), "Failed to process image", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void uploadImageToRealTimeDatabase() {
        if (base64Image == null || base64Image.isEmpty()) {
            Toasty.warning(getContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading image...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userDatabaseRef.child("profileImage").setValue(base64Image)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toasty.success(getContext(), "Profile image updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(getContext(), "Upload failed: " +
                                        (task.getException() != null ? task.getException().getMessage() : ""),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showUserProfile(FirebaseUser firebaseuser) {
        final ProgressHandler progressHandler = new ProgressHandler(getActivity());
        progressHandler.show();

        userDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    email = firebaseuser.getEmail();
                    name = readUserDetails.username;
                    dob = readUserDetails.dob;
                    gender = readUserDetails.gender;
                    mobilenumber = readUserDetails.mobile;

                    text_welcome.setText("Welcome " + name);
                    text_email.setText(email);
                    text_username.setText(name);
                    text_phone.setText("+91 " + mobilenumber);
                    text_dob.setText(dob);
                    text_gender.setText(gender);

                    loadProfileImage();
                } else {
                    Toasty.error(getActivity(), "User data not found", Toast.LENGTH_SHORT).show();
                }
                progressHandler.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressHandler.dismiss();
                Toasty.error(getActivity(), "Failed to load profile: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProfileImage() {
        userDatabaseRef.child("profileImage").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String imageString = snapshot.getValue(String.class);
                        if (imageString != null && !imageString.isEmpty()) {
                            try {
                                byte[] decodedBytes = Base64.decode(imageString, Base64.DEFAULT);
                                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(
                                        decodedBytes, 0, decodedBytes.length);
                                profile_image_profileActivity.setImageBitmap(decodedBitmap);
                            } catch (IllegalArgumentException e) {
                                Toasty.warning(getContext(), "Invalid image data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toasty.error(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up any resources if needed
    }
}