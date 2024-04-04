package com.example.nashik_cityguide.PlaceFav_Activity;

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

import com.example.nashik_cityguide.All_categories;
import com.example.nashik_cityguide.Fragment_Activity.fav_fragment;
import com.example.nashik_cityguide.Place_Activity.place_adapter;
import com.example.nashik_cityguide.Place_Activity.place_fragment;
import com.example.nashik_cityguide.Place_Activity.tourist;
import com.example.nashik_cityguide.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class place_fav_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView placeFavView;
    placefav_adapter placefav_Adapter;
    private ImageView back_img;

    ShimmerFrameLayout shimmer_place;

    public place_fav_fragment() {
        // Required empty public constructor
    }


    public static place_fav_fragment newInstance(String param1, String param2) {
        place_fav_fragment fragment = new place_fav_fragment();
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
        View view = inflater.inflate(R.layout.fragment_place_fav_fragment, container, false);

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseuser.getUid();

        placeFavView=(RecyclerView)view.findViewById(R.id.placeFavView);
        placeFavView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<placeuserfav> options =
                new FirebaseRecyclerOptions.Builder<placeuserfav>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Favourite").child("Place Favorite").child(userID), placeuserfav.class)
                        .build();

        placefav_Adapter = new placefav_adapter(options);
        placeFavView.setAdapter(placefav_Adapter);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), fav_fragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        shimmer_place = view.findViewById(R.id.shimmer_place);
        shimmer_place.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            placeFavView.setVisibility(View.VISIBLE);
            shimmer_place.stopShimmer();
            shimmer_place.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        placefav_Adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        placefav_Adapter.stopListening();
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), place_fav_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_placeFav,new fav_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}