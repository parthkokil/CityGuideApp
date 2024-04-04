package com.example.nashik_cityguide.Bus_Activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_fragment;
import com.example.nashik_cityguide.R;

import es.dmoral.toasty.Toasty;


public class bus_info_frag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public bus_info_frag() {
        // Required empty public constructor
    }

    String contact,gmlink,name;

    public bus_info_frag(String contact, String gmlink, String name) {
        this.contact = contact;
        this.gmlink = gmlink;
        this.name = name;
    }

    public static bus_info_frag newInstance(String param1, String param2) {
        bus_info_frag fragment = new bus_info_frag();
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
        View view = inflater.inflate(R.layout.fragment_bus_info_frag, container, false);

        TextView busTermial_title,contact_info_bus;
        ImageView getDirection_bus, back_img, copyContact_bus;

        busTermial_title = view.findViewById(R.id.busTermial_title);
        contact_info_bus = view.findViewById(R.id.contact_info_bus);
        copyContact_bus = view.findViewById(R.id.copyContact_bus);
        String mobile = contact;

        busTermial_title.setText(name);
        contact_info_bus.setText(contact);

        getDirection_bus = view.findViewById(R.id.getDirection_bus);
        getDirection_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(gmlink);
            }
        });

        copyContact_bus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("number",contact);
                clipboardManager.setPrimaryClip(data);
                Toasty.success(getActivity(), "Contact No. Copied", Toast.LENGTH_SHORT).show();
            }
        });

        back_img = (ImageView)view.findViewById(R.id.back_arrow);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), busTerminal_fragment.class);
                AppCompatActivity activity = (AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_busTerminal,new busTerminal_fragment()).addToBackStack(null).commit();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });

        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void onBackPressed(){

        Intent intent = new Intent(getActivity(), busTerminal_fragment.class);
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_busTerminal,new busTerminal_fragment()).addToBackStack(null).commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}