package com.example.nashik_cityguide.PoliceStation_Activity;

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

import com.example.nashik_cityguide.Bus_Activity.busTerminal;
import com.example.nashik_cityguide.Bus_Activity.busTerminal_adapter;
import com.example.nashik_cityguide.Bus_Activity.busTerminal_fragment;
import com.example.nashik_cityguide.Hotel_Activity.hotel;
import com.example.nashik_cityguide.Hotel_Activity.hotel_adapter;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.emergency_category;
import com.example.nashik_cityguide.transport_category;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class policeStation_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView policeStationview;
    policeStation_adapter policeStation_adapter;
    private ImageView back_img;
    SearchView policeStation_searchBar;

    ShimmerFrameLayout shimmer_policeStation;

    public policeStation_fragment() {
        // Required empty public constructor
    }


    public static policeStation_fragment newInstance(String param1, String param2) {
        policeStation_fragment fragment = new policeStation_fragment();
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
        View view = inflater.inflate(R.layout.fragment_police_station_fragment, container, false);

        policeStationview=(RecyclerView)view.findViewById(R.id.policeStationview);
        policeStationview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<policeStation> options =
                new FirebaseRecyclerOptions.Builder<policeStation>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Police Station"), policeStation.class)
                        .build();

        policeStation_adapter = new policeStation_adapter(options);
        policeStationview.setAdapter(policeStation_adapter);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), emergency_category.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        policeStation_searchBar = view.findViewById(R.id.policeStation_searchBar);
        policeStation_searchBar.clearFocus();
        policeStation_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_policeStation = view.findViewById(R.id.shimmer_policeStation);
        shimmer_policeStation.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            policeStationview.setVisibility(View.VISIBLE);
            shimmer_policeStation.stopShimmer();
            shimmer_policeStation.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        policeStation_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        policeStation_adapter.stopListening();
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<policeStation> options =
                new FirebaseRecyclerOptions.Builder<policeStation>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Police Station").orderByChild("name").startAt(str).endAt(str+"~"), policeStation.class)
                        .build();

        policeStation_adapter = new policeStation_adapter(options);
        policeStation_adapter.startListening();
        policeStationview.setAdapter(policeStation_adapter);
    }
}