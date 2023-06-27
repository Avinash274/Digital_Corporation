package com.example.petscorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class pe_adapter extends RecyclerView.Adapter<pe_adapter.EventHolder>
{
    private Context context;
    private List<pe_modal> pe_modalList;

    private final int limit = 5;

    public pe_adapter(Context context, List<pe_modal> pe_modalList) {
        this.context = context;
        this.pe_modalList = pe_modalList;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pe_homepage_layout,parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        pe_modal pe_modal = pe_modalList.get(position);
        holder.userName.setText(pe_modal.getuName());
        holder.userEmailId.setText(pe_modal.getUemailId());
        holder.userDescription.setText(pe_modal.getuDescription());
        holder.userLocation.setText(pe_modal.getuLocation());
        Glide.with(context).load(pe_modal.getuImageView()).into(holder.userProfile);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Event_Detailed_Information.class);
                intent.putExtra("emailid",pe_modal.getUemailId());
                intent.putExtra("eventId",pe_modal.getuId());
                context.startActivity(intent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Event_Detailed_Information.class);
                intent.putExtra("emailid",pe_modal.getUemailId());
                intent.putExtra("eventId",pe_modal.getuId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(pe_modalList.size() > limit){
            return limit;
        }
        else
        {
            return pe_modalList.size();
        }
    }


    public class EventHolder extends  RecyclerView.ViewHolder {
        CircleImageView userProfile;
        TextView userName,userEmailId,userDescription,userLocation;
        Button call;
        MaterialCardView cardView;
        public EventHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.image_of_the_person);
            userName = itemView.findViewById(R.id.person_name);
            userEmailId = itemView.findViewById(R.id.no_of_posts);
            userDescription = itemView.findViewById(R.id.request_description);
            userLocation = itemView.findViewById(R.id.cropRate);
            call = itemView.findViewById(R.id.call_button);
            cardView = itemView.findViewById(R.id.requestCard);
        }
    }
}
