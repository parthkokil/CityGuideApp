package com.example.nashik_cityguide.RestFav_Activity;

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
import com.example.nashik_cityguide.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class restFav_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView restFavView;
    restfav_adapter restfav_Adapter;
    private ImageView back_img;
    ShimmerFrameLayout shimmer_rest;

    public restFav_fragment() {
        // Required empty public constructor
    }


    public static restFav_fragment newInstance(String param1, String param2) {
        restFav_fragment fragment = new restFav_fragment();
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
        View view = inflater.inflate(R.layout.fragment_rest_fav_fragment, container, false);

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseuser.getUid();

        restFavView=(RecyclerView)view.findViewById(R.id.restFavView);
        restFavView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<restuserfav> options =
                new FirebaseRecyclerOptions.Builder<restuserfav>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Favourite").child("Restaurant Favorite").child(userID), restuserfav.class)
                        .build();

        restfav_Adapter = new restfav_adapter(options);
        restFavView.setAdapter(restfav_Adapter);



        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), fav_fragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        shimmer_rest = view.findViewById(R.id.shimmer_rest);
        shimmer_rest.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            restFavView.setVisibility(View.VISIBLE);
            shimmer_rest.stopShimmer();
            shimmer_rest.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        restfav_Adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        restfav_Adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), restfav_adapter.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_restFav,new restFav_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

}