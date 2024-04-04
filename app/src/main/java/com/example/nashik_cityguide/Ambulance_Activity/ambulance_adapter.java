package com.example.nashik_cityguide.Ambulance_Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nashik_cityguide.Bus_Activity.busTerminal;
import com.example.nashik_cityguide.Bus_Activity.busTerminal_adapter;
import com.example.nashik_cityguide.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ambulance_adapter extends FirebaseRecyclerAdapter<ambulance_, ambulance_adapter.myviewholder> {

    public ambulance_adapter(@NonNull FirebaseRecyclerOptions<ambulance_> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ambulance_adapter.myviewholder holder, int position, @NonNull ambulance_ ambulance_) {
        holder.ambulance_name.setText(ambulance_.getName());
        holder.contact_ambulance.setText("Contact No : "+ambulance_.getContact());
    }

    @NonNull
    @Override
    public ambulance_adapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulanceinfodesign,parent,false);

        return new ambulance_adapter.myviewholder(view);
    }
    public class myviewholder extends RecyclerView.ViewHolder{

        CardView ambulance_cardview;
        TextView ambulance_name,contact_ambulance;
        SearchView ambulance_searchBar;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            ambulance_cardview = itemView.findViewById(R.id.ambulance_cardview);
            ambulance_name = itemView.findViewById(R.id.ambulance_name);
            contact_ambulance = itemView.findViewById(R.id.contact_ambulance);
            ambulance_searchBar = itemView.findViewById(R.id.ambulance_searchBar);
        }
    }
}
