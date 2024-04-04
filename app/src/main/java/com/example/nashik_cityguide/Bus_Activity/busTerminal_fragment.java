package com.example.nashik_cityguide.Bus_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.TextView;

import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.transport_category;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class busTerminal_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView busTerminalview;
    busTerminal_adapter busTerminal_adapter;
    private ImageView back_img;
    SearchView busTerminal_searchBar;
    TextView search_storage;

    ShimmerFrameLayout shimmer_busTerminal;
    public busTerminal_fragment() {

    }


    public static busTerminal_fragment newInstance(String param1, String param2) {
        busTerminal_fragment fragment = new busTerminal_fragment();
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

        View view = inflater.inflate(R.layout.fragment_bus_terminal_fragment, container, false);

        busTerminalview=(RecyclerView)view.findViewById(R.id.busTerminalview);
        busTerminalview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<busTerminal> options =
                new FirebaseRecyclerOptions.Builder<busTerminal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bus Terminals"), busTerminal.class)
                        .build();

        busTerminal_adapter = new busTerminal_adapter(options);
        busTerminalview.setAdapter(busTerminal_adapter);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), transport_category.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        search_storage = view.findViewById(R.id.search_storage);
        busTerminal_searchBar = view.findViewById(R.id.busTerminal_searchBar);
        busTerminal_searchBar.clearFocus();
        busTerminal_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        shimmer_busTerminal = view.findViewById(R.id.shimmer_busTerminal);
        shimmer_busTerminal.startShimmer();

        Handler handler = new Handler();
        handler.postDelayed(() ->{
            busTerminalview.setVisibility(View.VISIBLE);
            shimmer_busTerminal.stopShimmer();
            shimmer_busTerminal.setVisibility(View.GONE);
        },2000);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        busTerminal_adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        busTerminal_adapter.stopListening();
    }

    private void txtSearch(String str){

        FirebaseRecyclerOptions<busTerminal> options =
                new FirebaseRecyclerOptions.Builder<busTerminal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bus Terminals").orderByChild("name").startAt(str).endAt(str+"~"), busTerminal.class)
                        .build();

        busTerminal_adapter = new busTerminal_adapter(options);
        busTerminal_adapter.startListening();
        busTerminalview.setAdapter(busTerminal_adapter);
    }

}