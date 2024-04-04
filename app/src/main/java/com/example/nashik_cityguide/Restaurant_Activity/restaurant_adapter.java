package com.example.nashik_cityguide.Restaurant_Activity;

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

public class restaurant_adapter extends FirebaseRecyclerAdapter<restaurant,restaurant_adapter.myviewholder> {
    public restaurant_adapter(@NonNull FirebaseRecyclerOptions<restaurant> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder rest_holder, int position, @NonNull restaurant restaurant) {
        rest_holder.rest_name.setText(restaurant.getName());
        rest_holder.cat1.setText("Veg/Non-Veg - "+restaurant.getCat1());
        rest_holder.cat2.setText("Family/Non-Family : "+restaurant.getCat2());
        rest_holder.rest_timing.setText("Timing : "+restaurant.getTiming());

        rest_holder.restaurant_cardview.startAnimation(AnimationUtils.loadAnimation(rest_holder.itemView.getContext(),R.anim.recyclerview_anim));

        Glide.with(rest_holder.img1.getContext()).load(restaurant.getImg1()).into(rest_holder.img1);

        rest_holder.restaurant_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity rest_activity = (AppCompatActivity)v.getContext();
                rest_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_rest,new restaurant_info_frag(restaurant.getAdd(),restaurant.getCat1(),restaurant.getCat2(),restaurant.getDesc(),restaurant.getGmlink(),restaurant.getImg1(),restaurant.getImg2(),restaurant.getImg3(),restaurant.getName(),restaurant.getRating(),restaurant.getTiming())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantinfodesign,parent,false);

        return new restaurant_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        ImageView img1;
        TextView rest_name, cat1,cat2, rest_timing;
        CardView restaurant_cardview;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            rest_name = itemView.findViewById(R.id.rest_name);
            cat1 = itemView.findViewById(R.id.cat1);
            cat2 = itemView.findViewById(R.id.cat2);
            rest_timing = itemView.findViewById(R.id.rest_timing);
            restaurant_cardview = itemView.findViewById(R.id.restaurant_cardview);
        }
    }
}
