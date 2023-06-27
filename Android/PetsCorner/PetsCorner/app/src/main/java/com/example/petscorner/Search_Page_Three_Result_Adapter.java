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

public class Search_Page_Three_Result_Adapter extends RecyclerView.Adapter<Search_Page_Three_Result_Adapter.PetsHolder> {

    private Context context;
    private List<Search_Page_Three_Result_Modal> search_page_three_result_modalList;

    public Search_Page_Three_Result_Adapter(Context context, List<Search_Page_Three_Result_Modal> search_page_three_result_modalList) {
        this.context = context;
        this.search_page_three_result_modalList = search_page_three_result_modalList;
    }

    @NonNull
    @Override
    public PetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_pets_recyclerview_layout_file,parent,false);
        return new PetsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsHolder holder, int position) {
        Search_Page_Three_Result_Modal search_page_three_result_modal = search_page_three_result_modalList.get(position);
        holder.ownerUserName.setText(search_page_three_result_modal.getuName());
        holder.ownerEmailId.setText(search_page_three_result_modal.getuEmailId());
        holder.oPetAmount.setText(search_page_three_result_modal.getpAmount());
        holder.oPetTitle.setText(search_page_three_result_modal.getpTitle());
        holder.oPetBreed.setText(search_page_three_result_modal.getpBreed());
        holder.oPetGender.setText(search_page_three_result_modal.getpGender());
        holder.oPetDescription.setText(search_page_three_result_modal.getpAbout());
        Glide.with(context).load(search_page_three_result_modal.getuImageView()).into(holder.uImage);
        Glide.with(context).load(search_page_three_result_modal.getpImage()).into(holder.oPetImage);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Pet_Detailed_Information.class);
                intent.putExtra("ownerEmailid",search_page_three_result_modal.getuEmailId());
                intent.putExtra("petId",search_page_three_result_modal.getpId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return search_page_three_result_modalList.size();
    }

    public class PetsHolder extends RecyclerView.ViewHolder {

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
