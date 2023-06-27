package com.example.petscorner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment3 extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<fragment3_Modal> fragment3_modalList;
    private fragment3_Adapter adapter;

    String uName,uemailId,uImageView,uDescription,uLocation,uContact,uId,eventId;

    String myEmailId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment3_layout,container,false);

        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        My_Uploads_Posts my_uploads_posts = (My_Uploads_Posts) getActivity();
        myEmailId = my_uploads_posts.getEmail();

        fragment3_modalList = new ArrayList<>();
        fetchEvents(myEmailId);
        return  v;
    }

    private void fetchEvents(String myEmailId)
    {
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/my_profile_list_of_events.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject json = new JSONObject(response);
                    JSONArray students = json.getJSONArray("user_books");

                    for(int i = 0; i < students.length(); i++)
                    {
                        JSONObject student = students.getJSONObject(i);

                        uName = student.getString("firstName");
                        uemailId = student.getString("email_id");
                        uDescription = student.getString("event_desc");
                        uLocation = student.getString("event_date");
                        uContact = student.getString("event_contact");
                        uId = student.getString("event_id");
                        eventId = student.getString("event_id");
                        uImageView = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("profile_pic");

                        fragment3_Modal fragment3_modal = new fragment3_Modal(uName,uemailId,uImageView,uDescription,uLocation,uContact,uId,eventId);
                        fragment3_modalList.add(fragment3_modal);

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                adapter = new fragment3_Adapter(getActivity().getApplicationContext(),fragment3_modalList);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("myEmailId",myEmailId);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }
}
