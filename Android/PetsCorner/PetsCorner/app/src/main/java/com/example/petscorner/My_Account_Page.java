package com.example.petscorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class My_Account_Page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    LinearLayout logout,contactUs,changePassword,profileUpdate,myPosts;

    TextView userName,userSurname,userEmailId;

    String email_id,text_username, text_user_surname, text_email_id,photo;

    Button editProfile;

    CircleImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        User user = new User(My_Account_Page.this);
        email_id = new User(My_Account_Page.this).getEmail();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#eb4a69\">" + getString(R.string.my_profile) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        //side navigation view code starts
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent = new Intent(My_Account_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search_pets:
                        intent = new Intent(My_Account_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.add_pets_requests:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;

                    case R.id.add_events:
                        intent = new Intent(My_Account_Page.this,Upload_Event_Details_Form_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile:
                        intent = new Intent(My_Account_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.change_password:
                        intent = new Intent(My_Account_Page.this,Change_Password_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contact_us:
                        intent = new Intent(My_Account_Page.this,Contact_Us_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                        Intent intent = new Intent(My_Account_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.bottom_search_pets:
                        intent = new Intent(My_Account_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.bottom_pet_requests:
                        intent = new Intent(My_Account_Page.this,View_Pet_Requests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        break;

                    case R.id.bottom_add:
                        showDialog();
                        break;

                    case R.id.bottom_account:
                        intent = new Intent(My_Account_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        logout = findViewById(R.id.logOutProfile);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog mDialog = new MaterialDialog.Builder(My_Account_Page.this)
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

                                ProgressDialog progressDialog = new ProgressDialog(My_Account_Page.this);
                                progressDialog.setCancelable(false);
                                progressDialog.setIndeterminate(false);
                                progressDialog.setTitle("Logging out");
                                progressDialog.setProgress(300);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        User user = new User(My_Account_Page.this);
                                        user.removeUser();
                                        Intent intent = new Intent(My_Account_Page.this,Login_Page.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }}, 600);
                                progressDialog.show();


                            }
                        }).build();
                mDialog.show();
            }
        });

        contactUs = findViewById(R.id.contactUsProfile);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,Contact_Us_Page.class);
                startActivity(intent);
            }
        });

        changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,Change_Password_Page.class);
                startActivity(intent);
            }
        });

        userName = findViewById(R.id.farmerName);
        userSurname = findViewById(R.id.farmerSurname);
        userEmailId = findViewById(R.id.farmerEmailId);

        myPosts = findViewById(R.id.myPosts);
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,My_Uploads_Posts.class);
                intent.putExtra("email",email_id);
                startActivity(intent);
            }
        });

        getUserName();

        editProfile = findViewById(R.id.signInButton);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,My_Profile_Page.class);
                startActivity(intent);
            }
        });

        profilePicture = findViewById(R.id.profile_picture);

        profileUpdate = findViewById(R.id.profileSetting);
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,My_Profile_Page.class);
                startActivity(intent);
            }
        });
    }

    private void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(My_Account_Page.this)
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

                        ProgressDialog progressDialog = new ProgressDialog(My_Account_Page.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setTitle("Logging out");
                        progressDialog.setProgress(300);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                User user = new User(My_Account_Page.this);
                                user.removeUser();
                                Intent intent = new Intent(My_Account_Page.this,Login_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }}, 600);
                        progressDialog.show();


                    }
                }).build();
        mDialog.show();
    }

    private void getUserName() {
        ProgressDialog progressDialog = new ProgressDialog(My_Account_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Creating Profile");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/getUserName_And_Photo.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject json = new JSONObject(response);
                    JSONArray students = json.getJSONArray("book_request");

                    progressDialog.dismiss();
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject student = students.getJSONObject(i);

                        text_username = student.getString("firstName");
                        text_user_surname = student.getString("lastName");
                        text_email_id = student.getString("email_id");
                        photo = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("profile_pic");

                        userName.setText(text_username);
                        userSurname.setText(text_user_surname);
                        userEmailId.setText(text_email_id);
                        Glide.with(getApplicationContext()).load(photo).into(profilePicture);
                    }
                    progressDialog.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(My_Account_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",email_id);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(My_Account_Page.this).addToRequestQueue(request);

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
                Intent intent = new Intent(My_Account_Page.this,Upload_Pet_Details_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        eventUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,Upload_Event_Details_Form_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        reqUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Account_Page.this,Pet_Request_Form.class);
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
           Intent intent = new Intent(My_Account_Page.this,View_Events_List.class);
           startActivity(intent);
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}