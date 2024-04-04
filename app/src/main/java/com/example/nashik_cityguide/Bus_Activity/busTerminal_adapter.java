package com.example.nashik_cityguide.Bus_Activity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nashik_cityguide.PoliceStation_Activity.police_info_frag;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class busTerminal_adapter extends FirebaseRecyclerAdapter<busTerminal,busTerminal_adapter.myviewholder> {


    public busTerminal_adapter(@NonNull FirebaseRecyclerOptions<busTerminal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull busTerminal busTerminal) {
        holder.busTerminal_name.setText(busTerminal.getName());
        holder.contact_buses.setText(busTerminal.getContact());

        holder.busTerminal_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

        holder.busTerminal_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity police_activity = (AppCompatActivity)v.getContext();
                police_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_busTerminal,new bus_info_frag(busTerminal.getContact(),busTerminal.getGmlink(),busTerminal.getName())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.busterminalinfodesign,parent,false);

        return new busTerminal_adapter.myviewholder(view);
    }


    public class myviewholder extends RecyclerView.ViewHolder{

        CardView busTerminal_cardview;
        TextView busTerminal_name,contact_buses,search_storage;
        ImageView call_image, maps_image;
        SearchView busTerminal_searchBar;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            busTerminal_cardview = itemView.findViewById(R.id.busTerminal_cardview);
            busTerminal_name = itemView.findViewById(R.id.busTerminal_name);
            contact_buses = itemView.findViewById(R.id.contact_buses);
            busTerminal_searchBar = itemView.findViewById(R.id.busTerminal_searchBar);
            search_storage = itemView.findViewById(R.id.search_storage);
        }

        public void startActivity(Intent intent) {
            startActivity(intent);
        }
    }
}
