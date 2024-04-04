package com.example.nashik_cityguide.Railway_Activity;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nashik_cityguide.Bus_Activity.busTerminal_adapter;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import es.dmoral.toasty.Toasty;

public class railwayStation_adapter extends FirebaseRecyclerAdapter<railwayStation, railwayStation_adapter.myviewholder>  {

    public railwayStation_adapter(@NonNull FirebaseRecyclerOptions<railwayStation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull railwayStation railwayStation) {
        holder.railwayStation_name.setText(railwayStation.getName());
        holder.contact_railway.setText(railwayStation.getContact());

        holder.railwayStation_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.railwaystationinfodesign,parent,false);

        return new railwayStation_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        CardView railwayStation_cardview;
        TextView railwayStation_name,contact_railway;
        ImageView copy_railway_no;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            railwayStation_cardview = itemView.findViewById(R.id.railwayStation_cardview);
            railwayStation_name = itemView.findViewById(R.id.railwayStation_name);
            contact_railway = itemView.findViewById(R.id.contact_railway);

        }
    }
}
