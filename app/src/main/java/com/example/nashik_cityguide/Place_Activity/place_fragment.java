package com.example.nashik_cityguide.Place_Activity;

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
import com.example.nashik_cityguide.Hotel_Activity.hotel;
import com.example.nashik_cityguide.Hotel_Activity.hotel_adapter;
import com.example.nashik_cityguide.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class place_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView placeview;
    place_adapter adapter;
    private ImageView back_img;
    SearchView places_searchBar;

    ShimmerFrameLayout shimmer_place;

    public place_fragment() {

    }

    public static place_fragment newInstance(String param1, String param2) {
        place_fragment fragment = new place_fragment();
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
        View view = inflater.inflate(R.layout.fragment_place_fragment, container, false);

        placeview=(RecyclerView)view.findViewById(R.id.placeview);
        placeview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<tourist> options =
                new FirebaseRecyclerOptions.Builder<tourist>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Places"), tourist.class)
                        .build();

        adapter = new place_adapter(options);
        placeview.setAdapter(adapter);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), All_categories.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        places_searchBar = view.findViewById(R.id.places_searchBar);
        places_searchBar.clearFocus();
        places_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_place = view.findViewById(R.id.shimmer_place);
        shimmer_place.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            placeview.setVisibility(View.VISIBLE);
            shimmer_place.stopShimmer();
            shimmer_place.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), place_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new place_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<tourist> options =
                new FirebaseRecyclerOptions.Builder<tourist>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Places").orderByChild("title").startAt(str).endAt(str+"~"), tourist.class)
                        .build();

        adapter = new place_adapter(options);
        adapter.startListening();
        placeview.setAdapter(adapter);
    }
}