package com.example.nashik_cityguide.PlaceFav_Activity;

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
import com.example.nashik_cityguide.hotelFav_Activity.hotelFav_info_frag;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class placefav_adapter extends FirebaseRecyclerAdapter<placeuserfav, placefav_adapter.myviewholder> {

    public placefav_adapter(@NonNull FirebaseRecyclerOptions<placeuserfav> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull placefav_adapter.myviewholder holder, int position, @NonNull placeuserfav placeuserfav) {
        holder.title.setText(placeuserfav.getTime());
        holder.location.setText("Location : "+placeuserfav.getAddress());
        holder.timing.setText("Timing : "+placeuserfav.getTitle());

        holder.places_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

        Glide.with(holder.img1.getContext()).load(placeuserfav.getImg1()).into(holder.img1);

        holder.places_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity rest_activity = (AppCompatActivity)v.getContext();
                rest_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_placeFav,new placefav_info_frag(placeuserfav.getAddress(),placeuserfav.getCategory(),placeuserfav.getDesc(),placeuserfav.getGmlink(),placeuserfav.getImg1(),placeuserfav.getImg2(),placeuserfav.getImg3(),placeuserfav.getTime(),placeuserfav.getTitle())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public placefav_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placefavinfodesign,parent,false);

        return new placefav_adapter.myviewholder(view);
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
