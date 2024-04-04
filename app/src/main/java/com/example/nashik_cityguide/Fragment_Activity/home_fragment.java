package com.example.nashik_cityguide.Fragment_Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.nashik_cityguide.All_categories;
import com.example.nashik_cityguide.Hotel_Activity.hotels_category;
import com.example.nashik_cityguide.Place_Activity.touristPlaces_category;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.ReadWriteUserDetails;
import com.example.nashik_cityguide.Restaurant_Activity.restaurant_category;
import com.example.nashik_cityguide.emergency_category;
import com.example.nashik_cityguide.transport_category;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView restaurant_btn, hotels_btn, transport_btn, touristPlaces_btn, emergency_btn;
    private TextView categoryViewAll;

    private TextView temperatureTextView,display_name,greeting_text;
    private FirebaseAuth authprofile;
    private String displayName,greetingUser;
    private ImageSlider imageSlider;

    private static final String API_KEY = "5ac9d5c810e0bfac7f9ecf22a3d89c36";
    private static final String CITY_NAME = "Nashik";

    boolean isInMyFavorite = false;
    private FirebaseAuth firebaseAuth;

    public home_fragment() {
        // Required empty public constructor
    }


    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
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

        // Inflate the layout for this fragment
        View fragment_home = inflater.inflate(R.layout.fragment_home_fragment, container, false);

//        firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null){
//            checkIsFavorite();
//        }

        // View Binding of Categories
        restaurant_btn = fragment_home.findViewById(R.id.restaurant_btn);
        hotels_btn = fragment_home.findViewById(R.id.hotels_btn);
        transport_btn = fragment_home.findViewById(R.id.transport_btn);
        touristPlaces_btn = fragment_home.findViewById(R.id.touristPlaces_btn);
        emergency_btn = fragment_home.findViewById(R.id.emergency_btn);
        categoryViewAll = fragment_home.findViewById(R.id.categories_view_all);
        display_name = fragment_home.findViewById(R.id.display_name);
        greeting_text = fragment_home.findViewById(R.id.greeting_text);
        imageSlider = fragment_home.findViewById(R.id.imageSlider);

        // Code for Image Slider
        ArrayList<SlideModel> imageList_rest = new ArrayList<>(); // Create image list

        imageList_rest.add(new SlideModel(R.drawable.restaurant_banner, ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(R.drawable.hotels_banner,ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(R.drawable.tourist_banner,ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(R.drawable.emergency_banner,ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(R.drawable.feedback_banner,ScaleTypes.CENTER_CROP));

        imageSlider.setSlideAnimation(AnimationTypes. FOREGROUND_TO_BACKGROUND);
        ImageSlider imageSlider_rest = fragment_home.findViewById(R.id.imageSlider);
        imageSlider_rest.setImageList(imageList_rest);


        // Setting On CLick Listener for Categories
        restaurant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), restaurant_category.class);
                startActivity(intent);
            }
        });

        hotels_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), hotels_category.class);
                startActivity(intent);
            }
        });

        transport_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), transport_category.class);
                startActivity(intent);
            }
        });

        touristPlaces_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), touristPlaces_category.class);
                startActivity(intent);
            }
        });

        emergency_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), emergency_category.class);
                startActivity(intent);
            }
        });

        categoryViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), All_categories.class);
                startActivity(intent);
            }
        });


        authprofile = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = authprofile.getCurrentUser();
        displayProfileName(firebaseuser);
        greetingUser();

        return  fragment_home;
    }

    private void greetingUser() {

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            greeting_text.setText("Hey! Good morning");
        } else if (timeOfDay >= 12 && timeOfDay < 18) {
            greeting_text.setText("Hey! Good afternoon");
        } else {
            greeting_text.setText("Hey! Good evening");
        }
    }

    private void displayProfileName(FirebaseUser firebaseuser) {
        String userID = firebaseuser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){
                    displayName = readUserDetails.username;

                    display_name.setText(displayName);

                } else {
                    Toasty.error(getActivity(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(getActivity(), "Can't Fetch Username, Please Update your Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void checkIsFavorite(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered Users");
//        reference.child(firebaseAuth.getUid()).child("Favorites").child(rest_name).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                isInMyFavorite = snapshot.exists();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}