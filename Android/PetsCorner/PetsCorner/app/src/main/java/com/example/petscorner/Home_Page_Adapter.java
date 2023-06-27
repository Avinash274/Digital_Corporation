package com.example.petscorner;

import android.content.Context;
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

public class Home_Page_Adapter extends RecyclerView.Adapter<Home_Page_Adapter.CategoryHolder>
{
    private Context context;
    private List<Home_Page_Modal> home_page_modalList;

    public Home_Page_Adapter(Context context, List<Home_Page_Modal> home_page_modalList) {
        this.context = context;
        this.home_page_modalList = home_page_modalList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_page_category_layout,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Home_Page_Modal home_page_modal = home_page_modalList.get(position);
        holder.text.setText(home_page_modal.getCategoryName());
        Glide.with(context).load(home_page_modal.getCategoryImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return home_page_modalList.size();
    }

    public class CategoryHolder extends  RecyclerView.ViewHolder {

        ImageView image;
        TextView text;
        MaterialCardView card;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageofDog);
            text = itemView.findViewById(R.id.textofCategory);
            card = itemView.findViewById(R.id.requestCard);
        }
    }
}
