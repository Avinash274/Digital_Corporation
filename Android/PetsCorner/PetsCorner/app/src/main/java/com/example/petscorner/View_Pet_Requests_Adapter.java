package com.example.petscorner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class View_Pet_Requests_Adapter extends RecyclerView.Adapter<View_Pet_Requests_Adapter.PetHolder> {

    private Context context;
    private List<View_Pet_Requests_Modal> view_pet_requests_modalList;

    public View_Pet_Requests_Adapter(Context context, List<View_Pet_Requests_Modal> view_pet_requests_modalList) {
        this.context = context;
        this.view_pet_requests_modalList = view_pet_requests_modalList;
    }

    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pet_request_layout_file,parent,false);
        return new PetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHolder holder, int position) {
        View_Pet_Requests_Modal view_pet_requests_modal = view_pet_requests_modalList.get(position);
        holder.userName.setText(view_pet_requests_modal.getuName());
        holder.userEmailId.setText(view_pet_requests_modal.getUemailId());
        holder.userDescription.setText(view_pet_requests_modal.getuDescription());
        holder.userLocation.setText(view_pet_requests_modal.getuLocation());
        Glide.with(context).load(view_pet_requests_modal.getuImageView()).into(holder.userProfile);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", view_pet_requests_modal.getuContact(), null));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return view_pet_requests_modalList.size();
    }

    public class PetHolder extends  RecyclerView.ViewHolder
    {
        CircleImageView userProfile;
        TextView userName,userEmailId,userDescription,userLocation;
        Button call;
        MaterialCardView cardView;

        public PetHolder(@NonNull View itemView) {
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
