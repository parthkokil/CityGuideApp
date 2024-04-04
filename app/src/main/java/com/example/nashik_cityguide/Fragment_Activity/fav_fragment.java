package com.example.nashik_cityguide.Fragment_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.hotelFav_Activity.hotelFav_activity;
import com.example.nashik_cityguide.PlaceFav_Activity.placeFav_activity;
import com.example.nashik_cityguide.RestFav_Activity.restFav_activity;


public class fav_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RelativeLayout hotel_card, restaurant_card, place_card;

    public fav_fragment() {
        // Required empty public constructor
    }

    public static fav_fragment newInstance(String param1, String param2) {
        fav_fragment fragment = new fav_fragment();
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
        View view = inflater.inflate(R.layout.fragment_fav_fragment, container, false);

        hotel_card = view.findViewById(R.id.category_hotel_fav);
        restaurant_card = view.findViewById(R.id.category_restaurant_fav);
        place_card = view.findViewById(R.id.category_place_fav);

        hotel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), hotelFav_activity.class);
                startActivity(intent);
            }
        });

        restaurant_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), restFav_activity.class);
                startActivity(intent);
            }
        });

        place_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), placeFav_activity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}