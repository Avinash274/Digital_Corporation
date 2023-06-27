package com.example.petscorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ppdog_adapter extends RecyclerView.Adapter<ppdog_adapter.PetsHolder> {

    private Context context;
    private List<ppdog_modal> ppdog_modalList;

    private final int limit = 5;

    public ppdog_adapter(Context context, List<ppdog_modal> ppdog_modalList) {
        this.context = context;
        this.ppdog_modalList = ppdog_modalList;
    }

    @NonNull
    @Override
    public PetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ppdogs_layout,parent,false);
        return new PetsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsHolder holder, int position) {
        ppdog_modal ppdog_modal = ppdog_modalList.get(position);
        holder.ownerUserName.setText(ppdog_modal.getuName());
        holder.ownerEmailId.setText(ppdog_modal.getuEmailId());
        holder.oPetAmount.setText(ppdog_modal.getpAmount());
        holder.oPetTitle.setText(ppdog_modal.getpTitle());
        holder.oPetBreed.setText(ppdog_modal.getpBreed());
        holder.oPetGender.setText(ppdog_modal.getpGender());
        holder.oPetDescription.setText(ppdog_modal.getpAbout());
        Glide.with(context).load(ppdog_modal.getuImageView()).into(holder.uImage);
        Glide.with(context).load(ppdog_modal.getpImage()).into(holder.oPetImage);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Pet_Detailed_Information.class);
                intent.putExtra("ownerEmailid",ppdog_modal.getuEmailId());
                intent.putExtra("petId",ppdog_modal.getpId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(ppdog_modalList.size() > limit){
            return limit;
        }
        else
        {
            return ppdog_modalList.size();
        }
    }

    public class PetsHolder  extends RecyclerView.ViewHolder {
        CircleImageView uImage;
        ImageView oPetImage;
        TextView ownerUserName,ownerEmailId,oPetAmount,oPetTitle,oPetBreed,oPetGender,oPetDescription;
        MaterialCardView card;
        public PetsHolder(@NonNull View itemView) {
            super(itemView);
            uImage = itemView.findViewById(R.id.image_of_the_person);
            oPetImage = itemView.findViewById(R.id.imagePost);
            ownerUserName = itemView.findViewById(R.id.person_name);
            ownerEmailId = itemView.findViewById(R.id.no_of_posts);
            oPetAmount = itemView.findViewById(R.id.requests);
            oPetTitle = itemView.findViewById(R.id.imageTitle);
            oPetBreed = itemView.findViewById(R.id.dateTextView);
            oPetGender = itemView.findViewById(R.id.request_desc);
            oPetDescription = itemView.findViewById(R.id.book_description);

            card = itemView.findViewById(R.id.petCard);
        }
    }
}
