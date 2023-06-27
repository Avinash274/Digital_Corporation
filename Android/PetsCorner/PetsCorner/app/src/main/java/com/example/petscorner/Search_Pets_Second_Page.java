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
import android.content.res.Resources;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Search_Pets_Second_Page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    String email_id;

    AutoCompleteTextView petCategory,petGender,petPriceType;

    TextInputLayout tl1,tl2,tl3,tl4;
    TextInputEditText t1,t2,t3,t4;
    Button upload;

    String sPetCategory,sGender,sPriceType,sCity;

    String oPetCategory,oGender,oPriceType,oCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pets_second_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        User user = new User(Search_Pets_Second_Page.this);
        email_id = new User(Search_Pets_Second_Page.this).getEmail();

        getSupportActionBar().setTitle("Find a pet");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#eb4a69\">" + getString(R.string.Find_a_Pet) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent = new Intent(Search_Pets_Second_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search_pets:
                        intent = new Intent(Search_Pets_Second_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.add_pets_requests:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;

                    case R.id.add_events:
                        intent = new Intent(Search_Pets_Second_Page.this,Upload_Event_Details_Form_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile:
                        intent = new Intent(Search_Pets_Second_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.change_password:
                        intent = new Intent(Search_Pets_Second_Page.this,Change_Password_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contact_us:
                        intent = new Intent(Search_Pets_Second_Page.this,Contact_Us_Page.class);
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
                        Intent intent = new Intent(Search_Pets_Second_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.bottom_search_pets:
                        intent = new Intent(Search_Pets_Second_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.bottom_pet_requests:
                        intent = new Intent(Search_Pets_Second_Page.this,View_Pet_Requests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        break;

                    case R.id.bottom_add:
                        showDialog();
                        break;

                    case R.id.bottom_account:
                        intent = new Intent(Search_Pets_Second_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        petCategory = findViewById(R.id.autoCompleteTextView1);
        Resources res = getResources();
        String[] labels =  res.getStringArray( R.array.choose_pet_category ) ;
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.dropdown_item,labels);
        petCategory.setText(arrayAdapter.getItem(0).toString(),false);
        petCategory.setAdapter(arrayAdapter);

        petGender = findViewById(R.id.autoCompleteTextView);
        Resources res1 = getResources();
        String[] labels1 =  res1.getStringArray( R.array.choose_category ) ;
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,R.layout.dropdown_item,labels1);
        petGender.setText(arrayAdapter1.getItem(0).toString(),false);
        petGender.setAdapter(arrayAdapter1);

        petPriceType = findViewById(R.id.autoCompleteTextView3);
        Resources res2 = getResources();
        String[] labels2 =  res2.getStringArray( R.array.price_category ) ;
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,R.layout.dropdown_item,labels2);
        petPriceType.setText(arrayAdapter2.getItem(0).toString(),false);
        petPriceType.setAdapter(arrayAdapter2);

        tl1 = findViewById(R.id.pet_category);
        tl2 = findViewById(R.id.event_time);
        tl3 = findViewById(R.id.priceType);
        tl4 = findViewById(R.id.location);
        t4 = findViewById(R.id.location_edittext);

        upload = findViewById(R.id.signup_next_button);

        petCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Search_Pets_Second_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        petGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl2.setError(null);
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Search_Pets_Second_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        petPriceType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl3.setError(null);
                tl3.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Search_Pets_Second_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl4.setError(null);
                tl4.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Search_Pets_Second_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    checkDetails();
                }
                return false;
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });
    }

    private void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(Search_Pets_Second_Page.this)
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

                        ProgressDialog progressDialog = new ProgressDialog(Search_Pets_Second_Page.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setTitle("Logging out");
                        progressDialog.setProgress(300);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                User user = new User(Search_Pets_Second_Page.this);
                                user.removeUser();
                                Intent intent = new Intent(Search_Pets_Second_Page.this,Login_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }}, 600);
                        progressDialog.show();


                    }
                }).build();
        mDialog.show();
    }

    private void checkDetails()
    {
        sPetCategory = petCategory.getText().toString();
        sGender = petGender.getText().toString();
        sPriceType = petPriceType.getText().toString();
        sCity = t4.getText().toString();
        if(sPetCategory.equals("Pet Category") && sGender.equals("Select Gender") && sPriceType.equals("Price Type") && TextUtils.isEmpty(sCity))
        {
            tl1.setError("Required");
            tl2.setError("Required");
            tl3.setError("Required");
            tl4.setError("Required");
        }
        else if(sPetCategory.equals("Pet Category"))
        {
            tl1.setError("Please select pet category");
        }
        else if(sGender.equals("Select Gender"))
        {
            tl2.setError("Please select pet gender");
        }
        else if(sPriceType.equals("Price Type"))
        {
            tl3.setError("Please select price type");
        }
        else if(sCity.isEmpty())
        {
            tl4.setError("Enter pet location");
            t4.setSelection(sCity.length());
            tl4.requestFocus();
        }
        else
        {
            checkDetails(sPetCategory,sGender,sPriceType,sCity);
        }
    }

    private void checkDetails(String sPetCategory, String sGender, String sPriceType, String sCity)
    {
        ProgressDialog progressDialog = new ProgressDialog(Search_Pets_Second_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Finding your pet...");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/check_user_pets.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject json = new JSONObject(response);
                    JSONArray students = json.getJSONArray("user_books");

                    if (students.length() == 0)
                    {
                        progressDialog.dismiss();
                        MaterialDialog mDialog = new MaterialDialog.Builder(Search_Pets_Second_Page.this)
                                .setTitle("No Records Found")
                                .setMessage("If using a custom view,try adjusting the filters. Otherwise,create some data!").setCancelable(false).setPositiveButton("Okay", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", R.drawable.clip_cross_mark, new AbstractDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                        onBackPressed();
                                    }
                                }).build();
                        mDialog.show();
                    }
                    else
                    {
                        for(int i = 0; i < students.length(); i++)
                        {
                            JSONObject student = students.getJSONObject(i);

                            oPetCategory = student.getString("petCategory");
                            oGender = student.getString("pGender");
                            oPriceType = student.getString("pPriceType");
                            oCity = student.getString("pLocation");

                            Intent intent = new Intent(Search_Pets_Second_Page.this,Search_Page_Three_Result.class);
                            intent.putExtra("oPetCategory",oPetCategory);
                            intent.putExtra("oGender",oGender);
                            intent.putExtra("oPriceType",oPriceType);
                            intent.putExtra("oCity",oCity);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                    }
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
                Toast.makeText(Search_Pets_Second_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("sPetCategory",sPetCategory);
                param.put("sGender",sGender);
                param.put("sPriceType",sPriceType);
                param.put("sCity",sCity);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Search_Pets_Second_Page.this).addToRequestQueue(request);
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
                Intent intent = new Intent(Search_Pets_Second_Page.this,Upload_Pet_Details_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        eventUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_Pets_Second_Page.this,Upload_Event_Details_Form_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        reqUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_Pets_Second_Page.this,Pet_Request_Form.class);
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
            Intent intent = new Intent(Search_Pets_Second_Page.this,View_Events_List.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}