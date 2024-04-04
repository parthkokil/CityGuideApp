package com.example.nashik_cityguide.Flight_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nashik_cityguide.Hotel_Activity.hotel_fragment;
import com.example.nashik_cityguide.R;


public class flight_info_frag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String arr, cname, dept, dest, fno, freq, origin;

    public flight_info_frag() {

    }

    public flight_info_frag(String arr, String cname, String dept, String dest, String fno, String freq, String origin) {
        this.arr = arr;
        this.cname = cname;
        this.dept = dept;
        this.dest = dest;
        this.fno = fno;
        this.freq = freq;
        this.origin = origin;
    }

    public static flight_info_frag newInstance(String param1, String param2) {
        flight_info_frag fragment = new flight_info_frag();
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
        View view = inflater.inflate(R.layout.fragment_flight_info_frag, container, false);

        TextView airline,flight_no,origin_txt,destination,arrival,departure,frequency;
        ImageView back_img;

        airline = view.findViewById(R.id.airline);
        flight_no = view.findViewById(R.id.flight_no);
        origin_txt = view.findViewById(R.id.origin_txt);
        destination = view.findViewById(R.id.destination);
        arrival = view.findViewById(R.id.arrival);
        departure = view.findViewById(R.id.departure);
        frequency = view.findViewById(R.id.frequency);

        airline.setText("Airline : "+cname);
        flight_no.setText("Flight No : "+fno);
        origin_txt.setText("Origin : "+origin);
        destination.setText("Destination : "+dest);
        arrival.setText("Arrival : "+arr);
        departure.setText("Departure : "+dept);
        frequency.setText("Frequency : "+freq);

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), flight_fragment.class);
                AppCompatActivity activity = (AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_flight,new flight_fragment()).addToBackStack(null).commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });

        return view;
    }
    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), flight_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_flight,new flight_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}