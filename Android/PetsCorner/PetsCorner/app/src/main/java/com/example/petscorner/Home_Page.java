package com.example.petscorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Home_Page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    private RecyclerView recyclerView;
    String categoryName,categoryImage;
    private List<Home_Page_Modal> home_page_modalList;
    private Home_Page_Adapter adapter;

    private RecyclerView recyclerView1;
    String sUserImage,sUserName,sUserEmail,sPetAmount,sPetImage,sPetTitle,sPetBreed,sPetGender,sPetDescription,sPetId,sPetOwnerEmaild;
    private List<ppdog_modal> ppdog_modalList;
    private ppdog_adapter adapter1;

    private RecyclerView recyclerView2;
    String uName,uemailId,uImageView,uDescription,uLocation,uContact,uId,email_id;
    private List<pe_modal> pe_modalList;
    private pe_adapter adapter2;

    EditText searchBar;
    TextView v1,v2,v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        User user = new User(Home_Page.this);
        email_id = new User(Home_Page.this).getEmail();

        actionBarDrawerToggle  = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#eb4a69\">" + getString(R.string.home_page) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        //side navigation view code starts
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent = new Intent(Home_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search_pets:
                        intent = new Intent(Home_Page.this,Search_Pets_First_Page.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.add_pets_requests:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;

                    case R.id.add_events:
                        intent = new Intent(Home_Page.this,Upload_Event_Details_Form_Page.class);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile:
                        intent = new Intent(Home_Page.this,My_Account_Page.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.change_password:
                        intent = new Intent(Home_Page.this,Change_Password_Page.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contact_us:
                        intent = new Intent(Home_Page.this,Contact_Us_Page.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logout:
                        logout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        //bottom navigation view code starts
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.bottom_home:
                        Intent intent = new Intent(Home_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.bottom_search_pets:
                        intent = new Intent(Home_Page.this,Search_Pets_First_Page.class);
                        startActivity(intent);
                        break;

                    case R.id.bottom_pet_requests:
                        intent = new Intent(Home_Page.this,View_Pet_Requests.class);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        break;

                    case R.id.bottom_add:
                       showDialog();
                        break;

                    case R.id.bottom_account:
                        intent = new Intent(Home_Page.this,My_Account_Page.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Home_Page.this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        home_page_modalList = new ArrayList<>();
        fetchCat();

        recyclerView1 = findViewById(R.id.recyclerview1);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Home_Page.this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView1.setLayoutManager(layoutManager1);
        ppdog_modalList = new ArrayList<>();
        fetchPopularDogs();

        recyclerView2 = findViewById(R.id.recyclerview2);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(Home_Page.this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView2.setLayoutManager(layoutManager2);
        pe_modalList = new ArrayList<>();
        fetchEvents();

        searchBar = findViewById(R.id.searchText);
        searchBar.requestFocus();
        searchBar.setInputType(InputType.TYPE_NULL);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setInputType(InputType.TYPE_NULL);
                Intent intent = new Intent(Home_Page.this, Search_Pets_Second_Page.class);
                startActivity(intent);
            }
        });

        v1 = findViewById(R.id.viewAllCategories);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Search_Pets_Second_Page.class);
                startActivity(intent);
            }
        });

        v2 = findViewById(R.id.dogViewall);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, Search_Pets_Second_Page.class);
                startActivity(intent);
            }
        });

        v3 = findViewById(R.id.vEvents);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, View_Events_List.class);
                startActivity(intent);
            }
        });

    }

    private void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(Home_Page.this)
                .setTitle("Log Out?")
                .setMessage("Are you sure you want to log out?").setCancelable(false).setPositiveButton("Cancel", R.drawable.clip_cross_mark, new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Log Out", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        ProgressDialog progressDialog = new ProgressDialog(Home_Page.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setTitle("Logging out");
                        progressDialog.setProgress(300);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                User user = new User(Home_Page.this);
                                user.removeUser();
                                Intent intent = new Intent(Home_Page.this,Login_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }}, 600);
                        progressDialog.show();


                    }
                }).build();
        mDialog.show();
    }

    private void fetchEvents()
    {
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/view_events_list.php";
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
                        uImageView = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("profile_pic");

                        pe_modal pe_modal = new pe_modal(uName,uemailId,uImageView,uDescription,uLocation,uContact,uId);
                        pe_modalList.add(pe_modal);

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                adapter2 = new pe_adapter(Home_Page.this,pe_modalList);
                recyclerView2.setAdapter(adapter2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Home_Page.this).addToRequestQueue(request);

    }

    private void fetchPopularDogs()
    {
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/popular_dog_breed_homepage.php";

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

                        sUserName = student.getString("firstName");
                        sUserEmail = student.getString("email_id");
                        sPetAmount = student.getString("pPrice");
                        sPetTitle = student.getString("petName");
                        sPetBreed = student.getString("pBreed");
                        sPetGender = student.getString("pGender");
                        sPetDescription = student.getString("pABout");
                        sPetId = student.getString("pet_id");
                        sPetOwnerEmaild = student.getString("email_id");
                        sUserImage = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("profile_pic");
                        sPetImage = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("pImage");


                        ppdog_modal ppdog_modal = new ppdog_modal(sUserImage,sUserName,sUserEmail,sPetAmount,sPetImage,sPetTitle,sPetBreed,sPetGender,sPetDescription,sPetId,sPetOwnerEmaild);
                        ppdog_modalList.add(ppdog_modal);

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                adapter1 = new ppdog_adapter(Home_Page.this,ppdog_modalList);
                recyclerView1.setAdapter(adapter1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Home_Page.this).addToRequestQueue(request);
    }

    private void fetchCat()
    {
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/get_Category.php";
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

                        categoryName = student.getString("category_name");
                        categoryImage = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("category_image");

                        Home_Page_Modal home_page_modal = new Home_Page_Modal(categoryName,categoryImage);
                        home_page_modalList.add(home_page_modal);

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                adapter = new Home_Page_Adapter(Home_Page.this,home_page_modalList);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Home_Page.this).addToRequestQueue(request);

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_dialog_layout);

        LinearLayout petUpload = dialog.findViewById(R.id.layoutEdit);
        LinearLayout reqUpload = dialog.findViewById(R.id.layoutEdit1);
        LinearLayout eventUpload = dialog.findViewById(R.id.layoutEdit2);

        petUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Page.this,Upload_Pet_Details_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        eventUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this,Upload_Event_Details_Form_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        reqUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this,Pet_Request_Form.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_icon_menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.top_notification)
        {
            Intent intent = new Intent(Home_Page.this,View_Events_List.class);
            startActivity(intent);
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}