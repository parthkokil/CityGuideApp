package com.example.nashik_cityguide.Fragment_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
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

import com.bumptech.glide.Glide;
import com.example.nashik_cityguide.ProgressHandler;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.ReadWriteUserDetails;
import com.example.nashik_cityguide.Update_Activity.update_email;
import com.example.nashik_cityguide.Update_Activity.update_profile;
import com.example.nashik_cityguide.Update_Activity.update_profile_photo;
import com.example.nashik_cityguide.change_password;
import com.example.nashik_cityguide.signIn_signUp_Activity.signin_signup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class profile_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    LinearLayout expandableView, expandableView_profile, expandableView_support, expandableView_about, expandableView_Follow;
    ImageView arrow_btn, arrow_btn2, arrow_btn3, arrow_btn4, arrow_btn5;
    ImageView update_profile_arrow, change_password_img, update_profile_img, update_email_img;
    ImageView upload_img;
    ImageView send_email, send_privacy_policy, send_feedback, follow_Insta, follow_fb;
    CardView profile_details, edit_profile, support_section, about_section, followus_section;
    CircleImageView profile_image_profileActivity;

    Button logout_btn;

    private String name,email,mobilenumber,dob,gender;
    private FirebaseAuth authprofile;
    private TextView text_welcome, text_email, text_username, text_phone, text_dob, text_gender;
    private ProgressBar progressbar;

    StorageReference storageReference;

    public profile_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static profile_fragment newInstance(String param1, String param2) {
        profile_fragment fragment = new profile_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment_profile = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        final ProgressHandler progressHandler = new ProgressHandler(getActivity());

        progressbar = fragment_profile.findViewById(R.id.progress_bar);
        text_welcome = fragment_profile.findViewById(R.id.welcome_name);
        text_email = fragment_profile.findViewById(R.id.email_txt);
        text_username = fragment_profile.findViewById(R.id.username_txt);
        text_phone = fragment_profile.findViewById(R.id.phone_txt);
        text_dob = fragment_profile.findViewById(R.id.dob_txt);
        text_gender = fragment_profile.findViewById(R.id.gender_txt);
        profile_image_profileActivity = fragment_profile.findViewById(R.id.profile_image_profileActivity);

        authprofile = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = authprofile.getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();

        FirebaseDatabase md = FirebaseDatabase.getInstance();
        DatabaseReference mr = md.getReference("ImageUrl");

        // Fetching the Data from Firebase
        if (firebaseuser == null){
            Toasty.warning(getActivity(), "Please, Update your Profile", Toast.LENGTH_SHORT).show();
        } else {progressHandler.show();
            showUserProfile(firebaseuser);
        }
        progressHandler.dismiss();
        // END Here


        // EDIT PROFILE SECTION
        update_profile_arrow = fragment_profile.findViewById(R.id.update_profile_arrow);
        update_profile_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_profile.class);
                startActivity(intent);
            }
        });
        change_password_img = fragment_profile.findViewById(R.id.change_pass_arrow);
        change_password_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), change_password.class);
                startActivity(intent);
            }
        });
        update_profile_img = fragment_profile.findViewById(R.id.profile_img_arrow);
        update_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_profile_photo.class);
                startActivity(intent);
            }
        });
        update_email_img = fragment_profile.findViewById(R.id.update_email_arrow);
        update_email_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_email.class);
                startActivity(intent);
            }
        });
        upload_img = fragment_profile.findViewById(R.id.upload_img);
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_profile_photo.class);
                startActivity(intent);
            }
        });


        expandableView = fragment_profile.findViewById(R.id.ly1);
        arrow_btn = fragment_profile.findViewById(R.id.down_arrow);
        profile_details = fragment_profile.findViewById(R.id.profile_details_cardView);

        arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(profile_details, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrow_btn.setBackgroundResource(R.drawable.up_arrow_image);
                } else {
                    expandableView.setVisibility(View.GONE);
                    arrow_btn.setBackgroundResource(R.drawable.down_arrow_image);
                }
            }
        });
        // EDIT PROFILE SECTION ENDS HERE


        // PROFILE DETAILS SECTION STARTS HERE
        edit_profile = fragment_profile.findViewById(R.id.edit_profile_cardView);
        expandableView_profile = fragment_profile.findViewById(R.id.ly2);
        arrow_btn2 = fragment_profile.findViewById(R.id.down_arrow_2);
        arrow_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView_profile.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(edit_profile, new AutoTransition());
                    expandableView_profile.setVisibility(View.VISIBLE);
                    arrow_btn2.setBackgroundResource(R.drawable.up_arrow_image);
                } else {
                    expandableView_profile.setVisibility(View.GONE);
                    arrow_btn2.setBackgroundResource(R.drawable.down_arrow_image);
                }
            }
        });
        // PROFILE DETAILS END HERE


        // SUPPORT SECTION STARTS HERE
        support_section = fragment_profile.findViewById(R.id.support_cardView);
        expandableView_support = fragment_profile.findViewById(R.id.ly4);
        arrow_btn4 = fragment_profile.findViewById(R.id.down_arrow_4);
        arrow_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView_support.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(support_section, new AutoTransition());
                    expandableView_support.setVisibility(View.VISIBLE);
                    arrow_btn4.setBackgroundResource(R.drawable.up_arrow_image);
                } else {
                    expandableView_support.setVisibility(View.GONE);
                    arrow_btn4.setBackgroundResource(R.drawable.down_arrow_image);
                }
            }
        });
        // SUPPORT SECTION END HERE


        // ABOUT SECTION STARTS HERE
        about_section = fragment_profile.findViewById(R.id.about_app_cardView);
        expandableView_about = fragment_profile.findViewById(R.id.ly3);
        arrow_btn3 = fragment_profile.findViewById(R.id.down_arrow_3);
        arrow_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView_about.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(about_section, new AutoTransition());
                    expandableView_about.setVisibility(View.VISIBLE);
                    arrow_btn3.setBackgroundResource(R.drawable.up_arrow_image);
                } else {
                    expandableView_about.setVisibility(View.GONE);
                    arrow_btn3.setBackgroundResource(R.drawable.down_arrow_image);
                }
            }
        });

        // FOLLOW US SECTION STARTS HERE
        followus_section = fragment_profile.findViewById(R.id.followus_cardView);
        expandableView_Follow = fragment_profile.findViewById(R.id.ly5);
        arrow_btn5 = fragment_profile.findViewById(R.id.down_arrow_5);
        arrow_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView_Follow.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(followus_section, new AutoTransition());
                    expandableView_Follow.setVisibility(View.VISIBLE);
                    arrow_btn5.setBackgroundResource(R.drawable.up_arrow_image);
                } else {
                    expandableView_Follow.setVisibility(View.GONE);
                    arrow_btn5.setBackgroundResource(R.drawable.down_arrow_image);
                }
            }
        });

        follow_Insta = fragment_profile.findViewById(R.id.insta_arrow);
        follow_Insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.instagram.com/cityguide_nashik/");
            }
        });

        follow_fb = fragment_profile.findViewById(R.id.fb_arrow);
        follow_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.facebook.com/profile.php?id=100092457782085&sk=about_contact_and_basic_info");
            }
        });

        send_privacy_policy = fragment_profile.findViewById(R.id.privacy_policy_arrow);
        send_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://docs.google.com/document/d/1lnronvwvIYugG6amg00mkhwACnX8mT5i/edit?usp=share_link&ouid=115231662005818950041&rtpof=true&sd=true");
            }
        });

        send_feedback = fragment_profile.findViewById(R.id.feedback_arrow);
        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://forms.gle/GzzL9o6TMpXLu6BS8");
            }
        });




        // ABOUT SECTION END HERE

        send_email = fragment_profile.findViewById(R.id.send_email_arrow);
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "cityguide.nashik@gmail.com";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{ to});

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email Client"));
            }
        });

        logout_btn = fragment_profile.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toasty.success(getActivity(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), signin_signup.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // ANIMATION PART
        profile_details.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.profile_cardview_animation2));
        edit_profile.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.profile_cardview_animation2));
        support_section.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.profile_cardview_animation2));
        about_section.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.profile_cardview_animation2));
        followus_section.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.profile_cardview_animation2));
        logout_btn.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation));

        return fragment_profile;
    }

    private void gotoUrl(String s) {
        Uri  uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    // Setting Data text to the Fields
    private void showUserProfile(FirebaseUser firebaseuser) {
        String userID = firebaseuser.getUid();
        final ProgressHandler progressHandler = new ProgressHandler(getActivity());
        progressHandler.dismiss();
        // Extracting Data from Database
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Images");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){
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

                    showProfilePhoto();


                } else {
                    Toasty.error(getActivity(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
                progressHandler.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressHandler.dismiss();
                Toasty.warning(getActivity(), "Profile Details not filled, Please Update your Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProfilePhoto() {


        // Get a reference to the Firebase Realtime Database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference imageRef = database.getReference("Images/userID/"+"imageURL"+".jpg");
//
//        // Attach a ValueEventListener to retrieve the image URL
//        imageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Retrieve the image URL from the dataSnapshot
//                String imageUrl = dataSnapshot.getValue(String.class);
//                // Use the image URL to display the image
//                Glide.with(getActivity()).load(imageUrl).into(profile_image_profileActivity);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle any errors that may occur while retrieving the image URL
//            }
//        });


//        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("imageURL");
//        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                String image = uri.toString();
//                Glide.with(getActivity()).load(image).into(profile_image_profileActivity);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}