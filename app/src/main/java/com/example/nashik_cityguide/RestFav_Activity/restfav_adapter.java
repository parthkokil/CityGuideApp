package com.example.nashik_cityguide.RestFav_Activity;

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
import com.example.nashik_cityguide.Restaurant_Activity.restaurant_info_frag;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class restfav_adapter extends FirebaseRecyclerAdapter<restuserfav, restfav_adapter.myviewholder> {

    public restfav_adapter(@NonNull FirebaseRecyclerOptions<restuserfav> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull restfav_adapter.myviewholder holder, int position, @NonNull restuserfav restuserfav) {
        holder.rest_name.setText(restuserfav.getName());
        holder.cat1.setText("Veg/Non-Veg - "+restuserfav.getCat1());
        holder.cat2.setText("Family/Non-Family : "+restuserfav.getCat2());
        holder.rest_timing.setText("Timing : "+restuserfav.getTiming());

        holder.restaurant_cardview.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recyclerview_anim));

        Glide.with(holder.img1.getContext()).load(restuserfav.getImg1()).into(holder.img1);

        holder.restaurant_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity rest_activity = (AppCompatActivity)v.getContext();
                rest_activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper_restFav,new restFav_info_frag(restuserfav.getAdd(),restuserfav.getCat1(),restuserfav.getCat2(),restuserfav.getDesc(),restuserfav.getGmlink(),restuserfav.getImg1(),restuserfav.getImg2(),restuserfav.getImg3(),restuserfav.getName(),restuserfav.getRating(),restuserfav.getTiming())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public restfav_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restfavinfodesign,parent,false);

        return new restfav_adapter.myviewholder(view);
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
