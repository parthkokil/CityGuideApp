package com.example.nashik_cityguide.Railway_Activity;

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

import com.example.nashik_cityguide.PoliceStation_Activity.policeStation;
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_adapter;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.transport_category;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class railwayStation_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView railwayStationview;
    railwayStation_adapter railwayStation_Adapter;
    private ImageView back_img;
    SearchView railwayStation_searchBar;

    ShimmerFrameLayout shimmer_railway;

    public railwayStation_fragment() {

    }

    public static railwayStation_fragment newInstance(String param1, String param2) {
        railwayStation_fragment fragment = new railwayStation_fragment();
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
        View view = inflater.inflate(R.layout.fragment_railway_station_fragment, container, false);

        railwayStationview=(RecyclerView)view.findViewById(R.id.railwayStationview);
        railwayStationview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<railwayStation> options_railway =
                new FirebaseRecyclerOptions.Builder<railwayStation>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Railway Station"), railwayStation.class)
                        .build();

        railwayStation_Adapter = new railwayStation_adapter(options_railway);
        railwayStationview.setAdapter(railwayStation_Adapter);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), transport_category.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        railwayStation_searchBar = view.findViewById(R.id.railwayStation_searchBar);
        railwayStation_searchBar.clearFocus();
        railwayStation_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_railway = view.findViewById(R.id.shimmer_railway);
        shimmer_railway.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            railwayStationview.setVisibility(View.VISIBLE);
            shimmer_railway.stopShimmer();
            shimmer_railway.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        railwayStation_Adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        railwayStation_Adapter.stopListening();
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<railwayStation> options =
                new FirebaseRecyclerOptions.Builder<railwayStation>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Railway Station").orderByChild("name").startAt(str).endAt(str+"~"), railwayStation.class)
                        .build();

        railwayStation_Adapter = new railwayStation_adapter(options);
        railwayStation_Adapter.startListening();
        railwayStationview.setAdapter(railwayStation_Adapter);
    }
}