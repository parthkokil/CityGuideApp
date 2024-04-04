package com.example.nashik_cityguide.Hotel_Activity;

import android.annotation.SuppressLint;
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
import com.example.nashik_cityguide.Restaurant_Activity.restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class hotel_info_frag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    boolean isFav = false;
    ImageView maps, fav, back_img;
    String add, desc, gmlink, img1, img2, img3, name, price, rating;
    public hotel_info_frag() {

    }

    public hotel_info_frag(String add, String desc, String gmlink, String img1, String img2, String img3, String name, String price, String rating){
        this.add = add;
        this.desc = desc;
        this.gmlink = gmlink;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public static hotel_info_frag newInstance(String param1, String param2) {
        hotel_info_frag fragment = new hotel_info_frag();
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
        View view = inflater.inflate(R.layout.fragment_hotel_info_frag, container, false);

        TextView hotel_info_name,hotel_address,price_hotel,rest_rating,rest_desc;


        hotel_info_name = view.findViewById(R.id.hotel_info_name);
        hotel_address = view.findViewById(R.id.hotel_address);
        price_hotel = view.findViewById(R.id.price_hotel);
        rest_rating = view.findViewById(R.id.rest_rating);
        rest_desc = view.findViewById(R.id.rest_desc);

        hotel_info_name.setText(name);
        hotel_address.setText("Address : "+add);
        price_hotel.setText("Price : "+price);
        rest_rating.setText("Rating : "+rating);
        rest_desc.setText("Details : "+desc);


        // Code for Image Slider
        ArrayList<SlideModel> imageList_rest = new ArrayList<>(); // Create image list

        imageList_rest.add(new SlideModel(img1, ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(img2,ScaleTypes.CENTER_CROP));
        imageList_rest.add(new SlideModel(img3,ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider_rest = view.findViewById(R.id.image_slider_hotel);
        imageSlider_rest.setImageList(imageList_rest);

        fav = view.findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav.setBackgroundResource(R.drawable.nav_fav_selected);
                addToFav_hotel(add, desc, gmlink, img1, img2, img3, name, price, rating);
            }
        });fav.setBackgroundResource(R.drawable.nav_fav_selected);

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
                Intent intent = new Intent(getActivity(), hotel_fragment.class);
                AppCompatActivity activity = (AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotel,new hotel_fragment()).addToBackStack(null).commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });

        checkiIsFav(name);

        return view;
    }

    private void addToFav_hotel(String add, String desc, String gmlink, String img1, String img2, String img3, String name, String price, String rating) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favourite");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        hotel hotelFav = new hotel(add, desc, gmlink, img1, img2, img3, name, price, rating);
        reference.child("Hotel Favorite").child(firebaseUser.getUid()).child(name).setValue(hotelFav).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        Intent intent = new Intent(getActivity(), hotel_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotel,new hotel_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void checkiIsFav(String name) {

        FirebaseUser firebaseUser;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favourite").child("Hotel Favorite");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();


        reference.child(firebaseUser.getUid()).child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@android.support.annotation.NonNull DataSnapshot snapshot) {
                isFav = snapshot.exists();
                if(isFav){
                    fav.setBackgroundResource(R.drawable.nav_fav_selected);
                }else{
                    fav.setBackgroundResource(R.drawable.fav_border_image);
                }
            }

            @Override
            public void onCancelled(@android.support.annotation.NonNull DatabaseError error) {
                Toasty.error(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void removeFromFav() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favourite").child("Hotel Favorite");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        reference.child(firebaseUser.getUid()).child(name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Remove from Your Favourite. Thank You!", Toast.LENGTH_SHORT).show();
                    isFav = false;
                } else {
                    Toast.makeText(getContext(), "Sorry Sir! Something Went Wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}