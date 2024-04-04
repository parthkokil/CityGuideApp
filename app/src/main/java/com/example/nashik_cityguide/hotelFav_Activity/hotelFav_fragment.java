package com.example.nashik_cityguide.hotelFav_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.nashik_cityguide.Fragment_Activity.fav_fragment;
import com.example.nashik_cityguide.Fragment_Activity.home_fragment;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.main_ui;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class hotelFav_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView hotelFavView;
    hotelfav_adapter hotelfav_Adapter;
    private ImageView back_img;
    ShimmerFrameLayout shimmer_hotel;

    public hotelFav_fragment() {
        // Required empty public constructor
    }


    public static hotelFav_fragment newInstance(String param1, String param2) {
        hotelFav_fragment fragment = new hotelFav_fragment();
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
        View view = inflater.inflate(R.layout.fragment_hotel_fav_fragment, container, false);

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseuser.getUid();

        hotelFavView=(RecyclerView)view.findViewById(R.id.hotelFavView);
        hotelFavView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<hoteluserfav> options =
                new FirebaseRecyclerOptions.Builder<hoteluserfav>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Favourite").child("Hotel Favorite").child(userID), hoteluserfav.class)
                        .build();

        hotelfav_Adapter = new hotelfav_adapter(options);
        hotelFavView.setAdapter(hotelfav_Adapter);


        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), main_ui.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        shimmer_hotel = view.findViewById(R.id.shimmer_hotel);
        shimmer_hotel.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            hotelFavView.setVisibility(View.VISIBLE);
            shimmer_hotel.stopShimmer();
            shimmer_hotel.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        hotelfav_Adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        hotelfav_Adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), hotelFav_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotelFav,new hotelFav_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}