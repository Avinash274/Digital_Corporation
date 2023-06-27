package com.example.petscorner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Fragment1_Adapter extends RecyclerView.Adapter<Fragment1_Adapter.PetsHolder> {

    private Context context;
    private List<Fragment1_Modal> fragment1_modalList;

    public Fragment1_Adapter(Context context, List<Fragment1_Modal> fragment1_modalList) {
        this.context = context;
        this.fragment1_modalList = fragment1_modalList;
    }

    @NonNull
    @Override
    public PetsHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_pets_uploaded_layout,parent,false);
        return new PetsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsHolder holder, int position) {
        Fragment1_Modal fragment1_modal = fragment1_modalList.get(position);
        holder.ownerUserName.setText(fragment1_modal.getuName());
        holder.ownerEmailId.setText(fragment1_modal.getuEmailId());
        holder.oPetAmount.setText(fragment1_modal.getpAmount());
        holder.oPetTitle.setText(fragment1_modal.getpTitle());
        holder.oPetBreed.setText(fragment1_modal.getpBreed());
        holder.oPetGender.setText(fragment1_modal.getpGender());
        holder.oPetDescription.setText(fragment1_modal.getpAbout());
        Glide.with(context).load(fragment1_modal.getuImageView()).into(holder.uImage);
        Glide.with(context).load(fragment1_modal.getpImage()).into(holder.oPetImage);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uRl = "http://192.168.43.103/PetsCorner_Android_Php/remove_my_pet.php";
                StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("Pet Removed Successfully"))
                        {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context.getApplicationContext(),My_Account_Page.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if(response.equals("Error while Removing"))
                        {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> param = new HashMap<>();
                        param.put("emailid",fragment1_modal.getuEmailId());
                        param.put("petId",fragment1_modal.getpId());
                        return param;
                    }
                };

                request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragment1_modalList.size();
    }

    public class PetsHolder extends  RecyclerView.ViewHolder  {
        CircleImageView uImage;
        ImageView oPetImage;
        TextView ownerUserName,ownerEmailId,oPetAmount,oPetTitle,oPetBreed,oPetGender,oPetDescription;
        Button remove;

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
            remove = itemView.findViewById(R.id.signup_next_button);

        }
    }
}
