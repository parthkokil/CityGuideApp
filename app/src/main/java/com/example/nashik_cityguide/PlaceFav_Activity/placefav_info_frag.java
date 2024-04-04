package com.example.nashik_cityguide.PlaceFav_Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.nashik_cityguide.Place_Activity.place_fragment;
import com.example.nashik_cityguide.R;

import java.util.ArrayList;


public class placefav_info_frag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private ImageView back_img;

    String address, category, desc, gmlink, img1, img2, img3, time, title;

    public placefav_info_frag() {
        // Required empty public constructor
    }

    public placefav_info_frag(String address, String category, String desc, String gmlink, String img1, String img2, String img3, String time, String title) {
        this.address = address;
        this.category = category;
        this.desc = desc;
        this.gmlink = gmlink;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.time = time;
        this.title = title;
    }


    public static placefav_info_frag newInstance(String param1, String param2) {
        placefav_info_frag fragment = new placefav_info_frag();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placefav_info_frag, container, false);

        TextView info_title, info_timing, info_desc, info_address, info_category;
        ImageView maps, fav;

        info_title = view.findViewById(R.id.info_title);
        info_timing = view.findViewById(R.id.place_timing);
        info_desc = view.findViewById(R.id.place_info);
        info_address = view.findViewById(R.id.place_address);
        info_category = view.findViewById(R.id.place_category);
        maps = view.findViewById(R.id.maps);
        fav = view.findViewById(R.id.fav);


        info_desc.setText("Information : "+desc);
        info_timing.setText("Timing : "+title);
        info_title.setText(time);
        info_address.setText("Address : "+address);
        info_category.setText("Category : "+category);


        // Code for Image Slider
        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        imageList.add(new SlideModel(img1, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img2,ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img3,ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

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
                Intent intent = new Intent(getActivity(), place_fav_fragment.class);
                AppCompatActivity activity = (AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_placeFav,new place_fav_fragment()).addToBackStack(null).commit();
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

        Intent intent = new Intent(getActivity(), place_fav_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_placeFav,new place_fav_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}