package com.example.nashik_cityguide.Place_Activity;

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

import com.bumptech.glide.Glide;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class place_adapter extends FirebaseRecyclerAdapter<tourist,place_adapter.myviewholder> {


    public place_adapter(@NonNull FirebaseRecyclerOptions<tourist> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull tourist tourist) {
        holder.title.setText(tourist.getTitle());
        holder.location.setText("Location : "+tourist.getAddress());
        holder.timing.setText("Timing : "+tourist.getTime());

        holder.places_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));


        Glide.with(holder.img1.getContext()).load(tourist.getImg1()).into(holder.img1);

        holder.places_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new place_info_frag(tourist.getAddress(),tourist.getCategory(),tourist.getDesc(),tourist.getGmlink(),tourist.getImg1(),tourist.getImg2(),tourist.getImg3(),tourist.getTitle(),tourist.getTime())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.touristinfodesign,parent,false);

        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        ImageView img1;
        TextView title, location, timing;
        CardView places_cardview;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
            timing = itemView.findViewById(R.id.timing);
            places_cardview = itemView.findViewById(R.id.places_cardview);
        }
    }
}
