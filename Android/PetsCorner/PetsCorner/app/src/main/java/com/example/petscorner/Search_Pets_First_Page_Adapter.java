package com.example.petscorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class Search_Pets_First_Page_Adapter extends RecyclerView.Adapter<Search_Pets_First_Page_Adapter.PetsHolder>{

    private Context context;
    private List<Search_Pets_First_Page_Modal> search_pets_first_page_modalList;

    public Search_Pets_First_Page_Adapter(Context context, List<Search_Pets_First_Page_Modal> search_pets_first_page_modalList) {
        this.context = context;
        this.search_pets_first_page_modalList = search_pets_first_page_modalList;
    }

    @NonNull
    @Override
    public PetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item_container,parent,false);
        return new PetsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsHolder holder, int position) {
        Search_Pets_First_Page_Modal search_pets_first_page_modal = search_pets_first_page_modalList.get(position);
        Glide.with(context).load(search_pets_first_page_modal.getuImageView()).into(holder.petImage);

        holder.petImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Pet_Detailed_Information.class);
                intent.putExtra("ownerEmailid",search_pets_first_page_modal.getEmailId());
                intent.putExtra("petId",search_pets_first_page_modal.getPetId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return search_pets_first_page_modalList.size();
    }


    public class PetsHolder extends RecyclerView.ViewHolder {

        ImageView petImage;

        public PetsHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.imagePost);
        }
    }
}
