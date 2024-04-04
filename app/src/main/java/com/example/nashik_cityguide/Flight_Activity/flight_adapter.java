package com.example.nashik_cityguide.Flight_Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nashik_cityguide.Hotel_Activity.hotel;
import com.example.nashik_cityguide.Hotel_Activity.hotel_adapter;
import com.example.nashik_cityguide.Hotel_Activity.hotel_info_frag;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class flight_adapter extends FirebaseRecyclerAdapter<flight, flight_adapter.myviewholder> {

    public flight_adapter(@NonNull FirebaseRecyclerOptions<flight> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull flight_adapter.myviewholder holder, int position, @NonNull flight flight) {
        holder.flight_name.setText(flight.getFno());
        holder.origin.setText("ORIGIN : "+flight.getOrigin());
        holder.destination.setText("DESTINATION : "+flight.getDest());

        holder.flight_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

        holder.flight_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity hotel_activity = (AppCompatActivity)v.getContext();
                hotel_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_flight,new flight_info_frag(flight.getArr(),flight.getCname(),flight.getDept(),flight.getDest(),flight.getFno(),flight.getFreq(),flight.getOrigin())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public flight_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flightinfodesign,parent,false);

        return new flight_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView flight_name, origin, destination;
        CardView flight_cardview;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            flight_name = itemView.findViewById(R.id.flight_name);
            origin = itemView.findViewById(R.id.origin);
            destination = itemView.findViewById(R.id.destination);
            flight_cardview = itemView.findViewById(R.id.flight_cardview);
        }
    }
}
