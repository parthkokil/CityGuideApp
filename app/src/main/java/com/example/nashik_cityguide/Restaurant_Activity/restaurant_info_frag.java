package com.example.nashik_cityguide.Restaurant_Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.signIn_signUp_Activity.signup_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;


public class restaurant_info_frag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ImageView back_img;
    String add,cat1,cat2,desc,gmlink,img1,img2,img3,name,rating,timing;
    ImageView fav_restaurant;

    public restaurant_info_frag() {

    }

    public restaurant_info_frag(String add, String cat1, String cat2, String desc, String gmlink, String img1, String img2, String img3, String name, String rating, String timing) {
        this.add = add;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.desc = desc;
        this.gmlink = gmlink;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.name = name;
        this.rating = rating;
        this.timing = timing;
    }

    public static restaurant_info_frag newInstance(String param1, String param2) {
        restaurant_info_frag fragment = new restaurant_info_frag();
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
        View view =  inflater.inflate(R.layout.fragment_restaurant_info_frag, container, false);

        TextView restaurant_name,rest_address,rest_timing,rest_category,rest_rating,rest_info;

        restaurant_name = view.findViewById(R.id.restaurant_name);
        rest_address = view.findViewById(R.id.rest_address);
        rest_timing = view.findViewById(R.id.rest_timing);
        rest_category = view.findViewById(R.id.rest_category);
        rest_rating = view.findViewById(R.id.rest_rating);
        rest_info = view.findViewById(R.id.rest_info);

        ImageView maps, fav;

        restaurant_name.setText(name);
        rest_address.setText("Address : "+add);
        rest_timing.setText("Timing : "+timing);
        rest_category.setText("Category : "+cat1 +"\nFamily / Non-Family : "+cat2);
        rest_rating.setText("Rating : "+rating);
        rest_info.setText("Information : "+desc);

        // Code for Image Slider
        ArrayList<SlideModel> imageList_rest = new ArrayList<>(); // Create image list

        imageList_rest.add(new SlideModel(img1, ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(img2,ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(img3,ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider_rest = view.findViewById(R.id.image_slider_rest);
        imageSlider_rest.setImageList(imageList_rest);

        fav_restaurant = view.findViewById(R.id.fav_restaurant);
        fav_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav_restaurant.setBackgroundResource(R.drawable.nav_fav_selected);
                addToFav(add,cat1,cat2,desc,gmlink,img1,img2,img3,name,rating,timing);
            }
        });

        maps = view.findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(gmlink);
            }
        });

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), restaurant_fragment.class);
                AppCompatActivity activity = (AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_rest,new restaurant_fragment()).addToBackStack(null).commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });

        return view;
    }

    private void addToFav(String add, String cat1, String cat2, String desc, String gmlink, String img1, String img2, String img3, String name, String rating, String timing) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favourite");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        restaurant restFav = new restaurant(add,cat1,cat2,desc,gmlink,img1,img2,img3,name,rating,timing);
        reference.child("Restaurant Favorite").child(firebaseUser.getUid()).child(name).setValue(restFav).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toasty.success(getContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();

                } else {
                    Toasty.error(getContext(), "Failed to add to Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), restaurant_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_rest,new restaurant_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}