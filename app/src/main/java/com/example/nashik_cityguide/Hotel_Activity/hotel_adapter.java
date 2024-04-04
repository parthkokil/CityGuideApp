package com.example.nashik_cityguide.Hotel_Activity;

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

public class hotel_adapter extends FirebaseRecyclerAdapter<hotel,hotel_adapter.myviewholder> {

    public hotel_adapter(@NonNull FirebaseRecyclerOptions<hotel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull hotel_adapter.myviewholder hotel_holder, int position, @NonNull hotel hotel) {

        hotel_holder.hotel_name.setText(hotel.getName());
        hotel_holder.price_hotel.setText("Price : "+hotel.getPrice());
        hotel_holder.hotel_rating.setText("Rating : "+hotel.getRating());

        hotel_holder.hotel_cardview.startAnimation(AnimationUtils.loadAnimation(hotel_holder.itemView.getContext(),R.anim.recyclerview_anim));


        Glide.with(hotel_holder.img1.getContext()).load(hotel.getImg1()).into(hotel_holder.img1);

        hotel_holder.hotel_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity hotel_activity = (AppCompatActivity)v.getContext();
                hotel_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_hotel,new hotel_info_frag(hotel.getAdd(),hotel.getDesc(),hotel.getGmlink(),hotel.getImg1(),hotel.getImg2(),hotel.getImg3(),hotel.getName(),hotel.getPrice(),hotel.getRating())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public hotel_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotelinfodesign,parent,false);

        return new hotel_adapter.myviewholder(view);
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
