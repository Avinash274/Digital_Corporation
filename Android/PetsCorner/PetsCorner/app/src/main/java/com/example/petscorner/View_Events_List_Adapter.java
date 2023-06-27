package com.example.petscorner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class View_Events_List_Adapter extends RecyclerView.Adapter<View_Events_List_Adapter.EventHolder> {

    private Context context;
    private List<View_Events_List_Modal> view_events_list_modalList;

    public View_Events_List_Adapter(Context context, List<View_Events_List_Modal> view_events_list_modalList) {
        this.context = context;
        this.view_events_list_modalList = view_events_list_modalList;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_event_details_recycler_layout_file,parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        View_Events_List_Modal view_events_list_modal = view_events_list_modalList.get(position);
        holder.userName.setText(view_events_list_modal.getuName());
        holder.userEmailId.setText(view_events_list_modal.getUemailId());
        holder.userDescription.setText(view_events_list_modal.getuDescription());
        holder.userLocation.setText(view_events_list_modal.getuLocation());
        Glide.with(context).load(view_events_list_modal.getuImageView()).into(holder.userProfile);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Event_Detailed_Information.class);
                intent.putExtra("emailid",view_events_list_modal.getUemailId());
                intent.putExtra("eventId",view_events_list_modal.getuId());
                context.startActivity(intent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(context,Event_Detailed_Information.class);
                intent.putExtra("emailid",view_events_list_modal.getUemailId());
                intent.putExtra("eventId",view_events_list_modal.getuId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return view_events_list_modalList.size();
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
