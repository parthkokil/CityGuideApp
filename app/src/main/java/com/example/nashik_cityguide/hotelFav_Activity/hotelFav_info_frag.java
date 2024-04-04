package com.example.nashik_cityguide.hotelFav_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.nashik_cityguide.Hotel_Activity.hotel_fragment;
import com.example.nashik_cityguide.R;
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


public class hotelFav_info_frag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    boolean isFav = false;

    String add, desc, gmlink, img1, img2, img3, name, price, rating;

    public hotelFav_info_frag() {
        // Required empty public constructor
    }

    public hotelFav_info_frag(String add, String desc, String gmlink, String img1, String img2, String img3, String name, String price, String rating){
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

    public static hotelFav_info_frag newInstance(String param1, String param2) {
        hotelFav_info_frag fragment = new hotelFav_info_frag();
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
        View view =  inflater.inflate(R.layout.fragment_hotel_fav_info_frag, container, false);

        TextView hotel_info_name,hotel_address,price_hotel,rest_rating,rest_desc;
        ImageView maps, back_img;

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
                Intent intent = new Intent(getActivity(), hotelFav_fragment.class);
                AppCompatActivity activity = (AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotelFav,new hotelFav_fragment()).addToBackStack(null).commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });

        return view;
    }
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), hotelFav_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotelFav,new hotelFav_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}