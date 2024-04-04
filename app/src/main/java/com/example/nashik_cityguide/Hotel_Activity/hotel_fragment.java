package com.example.nashik_cityguide.Hotel_Activity;

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
import com.example.nashik_cityguide.Bus_Activity.busTerminal;
import com.example.nashik_cityguide.Bus_Activity.busTerminal_adapter;
import com.example.nashik_cityguide.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class hotel_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView hotelview;
    hotel_adapter hotel_adapter;
    private ImageView back_img;
    SearchView hotels_searchBar;

    ShimmerFrameLayout shimmer_hotel;
    public hotel_fragment() {
        // Required empty public constructor
    }


    public static hotel_fragment newInstance(String param1, String param2) {
        hotel_fragment fragment = new hotel_fragment();
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
        View view = inflater.inflate(R.layout.fragment_hotel_fragment, container, false);

        hotelview=(RecyclerView)view.findViewById(R.id.hotelview);
        hotelview.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<hotel> options =
                new FirebaseRecyclerOptions.Builder<hotel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotels"), hotel.class)
                        .build();

        hotel_adapter = new hotel_adapter(options);
        hotelview.setAdapter(hotel_adapter);


        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), All_categories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        hotels_searchBar = view.findViewById(R.id.hotels_searchBar);
        hotels_searchBar.clearFocus();
        hotels_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_hotel = view.findViewById(R.id.shimmer_hotel);
        shimmer_hotel.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            hotelview.setVisibility(View.VISIBLE);
            shimmer_hotel.stopShimmer();
            shimmer_hotel.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        hotel_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        hotel_adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), hotel_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotel,new hotel_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<hotel> options =
                new FirebaseRecyclerOptions.Builder<hotel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotels").orderByChild("name").startAt(str).endAt(str+"~"), hotel.class)
                        .build();

        hotel_adapter = new hotel_adapter(options);
        hotel_adapter.startListening();
        hotelview.setAdapter(hotel_adapter);
    }
}