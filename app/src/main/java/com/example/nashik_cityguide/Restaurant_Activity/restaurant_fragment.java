package com.example.nashik_cityguide.Restaurant_Activity;

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

import com.example.nashik_cityguide.All_categories;
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation;
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_adapter;
import com.example.nashik_cityguide.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class restaurant_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView restaurantview;
    restaurant_adapter rest_adapter;

    ShimmerFrameLayout shimmerFrameLayout;
    private ImageView back_img;
    SearchView restaurant_searchBar;

    ShimmerFrameLayout shimmer_rest;
    public restaurant_fragment() {
        // Required empty public constructor
    }


    public static restaurant_fragment newInstance(String param1, String param2) {
        restaurant_fragment fragment = new restaurant_fragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurant_fragment, container, false);


        restaurantview=(RecyclerView)view.findViewById(R.id.restaurantview);
        restaurantview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<restaurant> options =
                new FirebaseRecyclerOptions.Builder<restaurant>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Restaurants"), restaurant.class)
                        .build();

        rest_adapter = new restaurant_adapter(options);
        restaurantview.setAdapter(rest_adapter);



        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), All_categories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        restaurant_searchBar = view.findViewById(R.id.restaurant_searchBar);
        restaurant_searchBar.clearFocus();
        restaurant_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                String search = query.toUpperCase();

                txtSearch(search);
                return false;
            }
        });

        shimmer_rest = view.findViewById(R.id.shimmer_rest);
        shimmer_rest.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            restaurantview.setVisibility(View.VISIBLE);
            shimmer_rest.stopShimmer();
            shimmer_rest.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        rest_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        rest_adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), restaurant_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_rest,new restaurant_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<restaurant> options =
                new FirebaseRecyclerOptions.Builder<restaurant>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Restaurants").orderByChild("name").startAt(str).endAt(str+"~"), restaurant.class)
                        .build();

        rest_adapter = new restaurant_adapter(options);
        rest_adapter.startListening();
        restaurantview.setAdapter(rest_adapter);
    }
}