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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment3_Adapter extends RecyclerView.Adapter<fragment3_Adapter.EventHolder>
{
    private Context context;
    private List<fragment3_Modal> fragment3_modalList;

    public fragment3_Adapter(Context context, List<fragment3_Modal> fragment3_modalList) {
        this.context = context;
        this.fragment3_modalList = fragment3_modalList;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_profile_event_list_layout,parent,false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        fragment3_Modal fragment3_modal = fragment3_modalList.get(position);
        holder.userName.setText(fragment3_modal.getuName());
        holder.userEmailId.setText(fragment3_modal.getUemailId());
        holder.userDescription.setText(fragment3_modal.getuDescription());
        holder.userLocation.setText(fragment3_modal.getuLocation());
        Glide.with(context).load(fragment3_modal.getuImageView()).into(holder.userProfile);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uRl = "http://192.168.43.103/PetsCorner_Android_Php/remove_my_event.php";
                StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("Event Removed Successfully"))
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
                        param.put("emailid",fragment3_modal.getUemailId());
                        param.put("eventId",fragment3_modal.getEventId());
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
        return fragment3_modalList.size();
    }

    public class EventHolder extends  RecyclerView.ViewHolder   {
        CircleImageView userProfile;
        TextView userName,userEmailId,userDescription,userLocation;
        Button call;
        public EventHolder(@NonNull View itemView) {
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
