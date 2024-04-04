package com.example.nashik_cityguide.FireBrigade_Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nashik_cityguide.PoliceStation_Activity.policeStation;
import com.example.nashik_cityguide.PoliceStation_Activity.policeStation_adapter;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class fireBrigade_adapter extends FirebaseRecyclerAdapter<firebrigade, fireBrigade_adapter.myviewholder> {

    public fireBrigade_adapter(@NonNull FirebaseRecyclerOptions<firebrigade> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull firebrigade firebrigade) {
        holder.fireBrigade_name.setText(firebrigade.getName());
        holder.contact_fireBrigade.setText("0253-"+firebrigade.getContact());

        holder.fireBrigade_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.firebrigadeinfodesign,parent,false);

        return new fireBrigade_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        CardView fireBrigade_cardview;
        TextView fireBrigade_name,contact_fireBrigade;
        ImageView call_image, maps_image;
        String link;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            fireBrigade_cardview = itemView.findViewById(R.id.fireBrigade_cardview);
            fireBrigade_name = itemView.findViewById(R.id.fireBrigade_name);
            contact_fireBrigade = itemView.findViewById(R.id.contact_fireBrigade);

        }
    }
}
