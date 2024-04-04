package com.example.nashik_cityguide.PoliceStation_Activity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nashik_cityguide.Hotel_Activity.hotel_info_frag;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class policeStation_adapter extends FirebaseRecyclerAdapter<policeStation, policeStation_adapter.myviewholder> {

    public policeStation_adapter(@NonNull FirebaseRecyclerOptions<policeStation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder_ps, int position, @NonNull policeStation policeStation) {
        holder_ps.policeStation_name.setText(policeStation.getName());
        holder_ps.contact_policeStation.setText(policeStation.getContact());

        holder_ps.policeStation_cardview.startAnimation(AnimationUtils.loadAnimation(holder_ps.itemView.getContext(),R.anim.recyclerview_anim));

        holder_ps.policeStation_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity police_activity = (AppCompatActivity)v.getContext();
                police_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_policeStation,new police_info_frag(policeStation.getContact(),policeStation.getGmlink(),policeStation.getName())).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.policestationinfodesign,parent,false);

        return new policeStation_adapter.myviewholder(view);
    }
    public class myviewholder extends RecyclerView.ViewHolder{

        CardView policeStation_cardview;
        TextView policeStation_name,contact_policeStation;
        ImageView call_image, direction_arrow;
        Button expand_btn_police;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            policeStation_cardview = itemView.findViewById(R.id.policeStation_cardview);
            policeStation_name = itemView.findViewById(R.id.policeStation_name);
            contact_policeStation = itemView.findViewById(R.id.contact_policeStation);
            //direction_arrow = itemView.findViewById(R.id.direction_arrow);
            //expand_btn_police = itemView.findViewById(R.id.expand_btn_police);
        }
    }

}
