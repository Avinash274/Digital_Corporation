package com.example.petscorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment2_Adapter extends RecyclerView.Adapter<fragment2_Adapter.RequestHolder> {

    private Context context;
    private List<fragment2_Modal> fragment2_modalList;

    public fragment2_Adapter(Context context, List<fragment2_Modal> fragment2_modalList) {
        this.context = context;
        this.fragment2_modalList = fragment2_modalList;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_pets_requests_layout,parent,false);
        return new RequestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        fragment2_Modal fragment2_modal = fragment2_modalList.get(position);
        holder.userName.setText(fragment2_modal.getuName());
        holder.userEmailId.setText(fragment2_modal.getUemailId());
        holder.userDescription.setText(fragment2_modal.getuDescription());
        holder.userLocation.setText(fragment2_modal.getuLocation());
        Glide.with(context).load(fragment2_modal.getuImageView()).into(holder.userProfile);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uRl = "http://192.168.43.103/PetsCorner_Android_Php/remove_my_request.php";
                StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("Request Removed Successfully"))
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
                        param.put("emailid",fragment2_modal.getUemailId());
                        param.put("petRequestId",fragment2_modal.getpRequest());
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
        return fragment2_modalList.size();
    }

    public class RequestHolder extends  RecyclerView.ViewHolder  {

        CircleImageView userProfile;
        TextView userName,userEmailId,userDescription,userLocation;
        Button call;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.image_of_the_person);
            userName = itemView.findViewById(R.id.person_name);
            userEmailId = itemView.findViewById(R.id.no_of_posts);
            userDescription = itemView.findViewById(R.id.request_description);
            userLocation = itemView.findViewById(R.id.cropRate);
            call = itemView.findViewById(R.id.call_button);
        }
    }
}
