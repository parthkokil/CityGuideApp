package com.example.nashik_cityguide.hotelFav_Activity;

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
import com.example.nashik_cityguide.Hotel_Activity.hotel_info_frag;
import com.example.nashik_cityguide.R;
import com.example.nashik_cityguide.Restaurant_Activity.restaurant_info_frag;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class hotelfav_adapter extends FirebaseRecyclerAdapter<hoteluserfav, hotelfav_adapter.myviewholder> {

    public hotelfav_adapter(@NonNull FirebaseRecyclerOptions<hoteluserfav> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull hotelfav_adapter.myviewholder holder, int position, @NonNull hoteluserfav hoteluserfav) {
        holder.hotel_name.setText(hoteluserfav.getName());
        holder.price_hotel.setText("Price : "+hoteluserfav.getPrice());
        holder.hotel_rating.setText("Rating : "+hoteluserfav.getRating());

        holder.hotel_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

        Glide.with(holder.img1.getContext()).load(hoteluserfav.getImg1()).into(holder.img1);

        holder.hotel_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity rest_activity = (AppCompatActivity)v.getContext();
                rest_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotelFav,new hotelFav_info_frag(hoteluserfav.getAdd(),hoteluserfav.getDesc(),hoteluserfav.getGmlink(),hoteluserfav.getImg1(),hoteluserfav.getImg2(),hoteluserfav.getImg3(),hoteluserfav.getName(),hoteluserfav.getPrice(),hoteluserfav.getRating())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public hotelfav_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotelfavinfodesign,parent,false);

        return new hotelfav_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        ImageView img1;
        TextView hotel_name, price_hotel, hotel_rating;
        CardView hotel_cardview;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);
            hotel_name = itemView.findViewById(R.id.hotel_name);
            price_hotel = itemView.findViewById(R.id.price_hotel);
            hotel_rating = itemView.findViewById(R.id.hotel_rating);
            hotel_cardview = itemView.findViewById(R.id.hotel_cardview);
        }
    }
}
