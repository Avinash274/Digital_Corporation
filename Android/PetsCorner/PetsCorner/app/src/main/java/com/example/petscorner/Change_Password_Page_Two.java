package com.example.petscorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Change_Password_Page_Two extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    String email_id,user_entered_new_password,user_entered_confirm_password;

    TextView cancel;
    Button next;

    TextInputLayout tl1,tl2;
    TextInputEditText t1,t2;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_page_two);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#eb4a69\">" + getString(R.string.createAccount1) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent = new Intent(Change_Password_Page_Two.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search_pets:
                        intent = new Intent(Change_Password_Page_Two.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.add_pets_requests:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;

                    case R.id.add_events:
                        intent = new Intent(Change_Password_Page_Two.this,Upload_Event_Details_Form_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile:
                        intent = new Intent(Change_Password_Page_Two.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.change_password:
                        intent = new Intent(Change_Password_Page_Two.this,Change_Password_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contact_us:
                        intent = new Intent(Change_Password_Page_Two.this,Contact_Us_Page.class);
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
                        Intent intent = new Intent(Change_Password_Page_Two.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.bottom_search_pets:
                        intent = new Intent(Change_Password_Page_Two.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.bottom_pet_requests:
                        intent = new Intent(Change_Password_Page_Two.this,View_Pet_Requests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        break;

                    case R.id.bottom_add:
                        showDialog();
                        break;

                    case R.id.bottom_account:
                        intent = new Intent(Change_Password_Page_Two.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        cancel = findViewById(R.id.forgot_password_change);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Password_Page_Two.this,My_Account_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        next = findViewById(R.id.signup_next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

        tl1 = findViewById(R.id.signup_fullname);
        t1 = findViewById(R.id.currentPassword);
        tl2 = findViewById(R.id.confirm_pasword);
        t2 = findViewById(R.id.confirm_password_edittext);

        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Change_Password_Page_Two.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl2.setError(null);
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Change_Password_Page_Two.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    checkDetails();
                }
                return false;
            }
        });
    }

    private void checkDetails() {
        user_entered_new_password = t1.getText().toString();
        user_entered_confirm_password = t2.getText().toString();
        if(TextUtils.isEmpty(user_entered_new_password) && TextUtils.isEmpty(user_entered_confirm_password))
        {
            tl1.setError("Enter New Password");
            tl2.setError("Enter Confirm Password");
        }
        else  if (!PASSWORD_PATTERN.matcher(user_entered_new_password).matches())
        {
            tl1.setError("Password too weak!!");
            t1.requestFocus();
            t1.setSelection(user_entered_new_password.length());
        }
        else if(user_entered_confirm_password.isEmpty())
        {
            tl2.setError("Please Confirm Password");
            t2.requestFocus();
        }
        else  if (!user_entered_confirm_password.equals(user_entered_new_password))
        {
            tl2.setError("Password does not match!!");
            t2.requestFocus();
            t2.setSelection(user_entered_confirm_password.length());
        }
        else
        {
            updatePassword(email_id,user_entered_new_password,user_entered_confirm_password);
        }
    }

    private void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(Change_Password_Page_Two.this)
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

                        ProgressDialog progressDialog = new ProgressDialog(Change_Password_Page_Two.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setTitle("Logging out");
                        progressDialog.setProgress(300);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                User user = new User(Change_Password_Page_Two.this);
                                user.removeUser();
                                Intent intent = new Intent(Change_Password_Page_Two.this,Login_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }}, 600);
                        progressDialog.show();


                    }
                }).build();
        mDialog.show();
    }

    private void updatePassword(String email_id, String user_entered_new_password, String user_entered_confirm_password) {
        ProgressDialog progressDialog = new ProgressDialog(Change_Password_Page_Two.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Updating Password");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/change_Password_Update.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Password Updated"))
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Change_Password_Page_Two.this,Change_Password_Success_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Change_Password_Page_Two.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",email_id);
                param.put("newPassword",user_entered_new_password);
                param.put("confirmPassword",user_entered_confirm_password);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Change_Password_Page_Two.this).addToRequestQueue(request);
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
                Intent intent = new Intent(Change_Password_Page_Two.this,Upload_Pet_Details_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        eventUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Password_Page_Two.this,Upload_Event_Details_Form_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        reqUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Password_Page_Two.this,Pet_Request_Form.class);
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
        inflater.inflate(R.menu.notification_icon_menu, menu);
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
            Intent intent = new Intent(Change_Password_Page_Two.this,View_Events_List.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}