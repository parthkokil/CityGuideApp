package com.example.nashik_cityguide.Flight_Activity;

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
import com.example.nashik_cityguide.FireBrigade_Activity.fireBrigade_fragment;
import com.example.nashik_cityguide.Hotel_Activity.hotel;
import com.example.nashik_cityguide.Hotel_Activity.hotel_adapter;
import com.example.nashik_cityguide.Hotel_Activity.hotel_fragment;
import com.example.nashik_cityguide.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class flight_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView flightview;
    flight_adapter flight_Adapter;
    private ImageView back_img;
    SearchView flight_searchBar;

    ShimmerFrameLayout shimmer_flight;

    public flight_fragment() {
        // Required empty public constructor
    }


    public static flight_fragment newInstance(String param1, String param2) {
        flight_fragment fragment = new flight_fragment();
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
        View view = inflater.inflate(R.layout.fragment_flight_fragment, container, false);

        flightview=(RecyclerView)view.findViewById(R.id.flightview);
        flightview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<flight> options =
                new FirebaseRecyclerOptions.Builder<flight>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Flights"), flight.class)
                        .build();

        flight_Adapter = new flight_adapter(options);
        flightview.setAdapter(flight_Adapter);


        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), All_categories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        flight_searchBar = view.findViewById(R.id.flight_searchBar);
        flight_searchBar.clearFocus();
        flight_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_flight = view.findViewById(R.id.shimmer_flight);
        shimmer_flight.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            flightview.setVisibility(View.VISIBLE);
            shimmer_flight.stopShimmer();
            shimmer_flight.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        flight_Adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        flight_Adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), flight_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_flight,new flight_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<flight> options =
                new FirebaseRecyclerOptions.Builder<flight>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Flights").orderByChild("dest").startAt(str).endAt(str+"~"), flight.class)
                        .build();

        flight_Adapter = new flight_adapter(options);
        flight_Adapter.startListening();
        flightview.setAdapter(flight_Adapter);
    }
}