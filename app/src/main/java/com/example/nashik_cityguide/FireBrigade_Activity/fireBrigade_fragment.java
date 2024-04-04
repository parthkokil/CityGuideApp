package com.example.nashik_cityguide.FireBrigade_Activity;

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
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation;
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_adapter;
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_fragment;
import com.example.nashik_cityguide.PoliceStation_Activity.police_station;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.emergency_category;
import com.example.nashik_cityguide.transport_category;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class fireBrigade_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView fireBrigadeview;
    fireBrigade_adapter fireBrigade_Adapter;
    private ImageView back_img;
    SearchView fireBrigade_searchBar;
    ShimmerFrameLayout shimmer_fireBrigade;

    public fireBrigade_fragment() {
        // Required empty public constructor
    }


    public static fireBrigade_fragment newInstance(String param1, String param2) {
        fireBrigade_fragment fragment = new fireBrigade_fragment();
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
        View view =  inflater.inflate(R.layout.fragment_fire_brigade_fragment, container, false);

        fireBrigadeview=(RecyclerView)view.findViewById(R.id.fireBrigadeview);
        fireBrigadeview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<firebrigade> options =
                new FirebaseRecyclerOptions.Builder<firebrigade>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Fire Department"), firebrigade.class)
                        .build();

        fireBrigade_Adapter = new fireBrigade_adapter(options);
        fireBrigadeview.setAdapter(fireBrigade_Adapter);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), emergency_category.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        fireBrigade_searchBar = view.findViewById(R.id.fireBrigade_searchBar);
        fireBrigade_searchBar.clearFocus();
        fireBrigade_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_fireBrigade = view.findViewById(R.id.shimmer_fireBrigade);
        shimmer_fireBrigade.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            fireBrigadeview.setVisibility(View.VISIBLE);
            shimmer_fireBrigade.stopShimmer();
            shimmer_fireBrigade.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fireBrigade_Adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fireBrigade_Adapter.stopListening();
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<firebrigade> options =
                new FirebaseRecyclerOptions.Builder<firebrigade>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Fire Department").orderByChild("name").startAt(str).endAt(str+"~"), firebrigade.class)
                        .build();

        fireBrigade_Adapter = new fireBrigade_adapter(options);
        fireBrigade_Adapter.startListening();
        fireBrigadeview.setAdapter(fireBrigade_Adapter);
    }
}