package com.example.petscorner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Upload_Pet_Details_Page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigationView;

    String email_id;

    AutoCompleteTextView petCategory,gender,priceType;

    TextInputLayout tl1,tl2,tl3,tl4,tl5,tl6,tl7,tl8,tl9;
    TextInputEditText t1,t2,t3,t4,t5,t6,t7,t8,t9;

    Button upload;

    String pCategory,pName,pAge,pGender,pBreed,pABout,pPrice,pPriceType,pLocation;

    RelativeLayout imagePicker;

    private static final Pattern mobile =
            Pattern.compile("[0-9]{2}");

    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    String encodeImageString;

    ImageView petImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pet_details_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#eb4a69\">" + getString(R.string.Upload_Pet_Details) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent = new Intent(Upload_Pet_Details_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search_pets:
                        intent = new Intent(Upload_Pet_Details_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.add_pets_requests:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;

                    case R.id.add_events:
                        intent = new Intent(Upload_Pet_Details_Page.this,Upload_Event_Details_Form_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile:
                        intent = new Intent(Upload_Pet_Details_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.change_password:
                        intent = new Intent(Upload_Pet_Details_Page.this,Change_Password_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contact_us:
                        intent = new Intent(Upload_Pet_Details_Page.this,Contact_Us_Page.class);
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
                        Intent intent = new Intent(Upload_Pet_Details_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.bottom_search_pets:
                        intent = new Intent(Upload_Pet_Details_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.bottom_pet_requests:
                        intent = new Intent(Upload_Pet_Details_Page.this,View_Pet_Requests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        break;

                    case R.id.bottom_add:
                        showDialog();
                        break;

                    case R.id.bottom_account:
                        intent = new Intent(Upload_Pet_Details_Page.this,My_Account_Page.class);
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


        gender = findViewById(R.id.autoCompleteTextView);
        Resources res1 = getResources();
        String[] labels1 =  res1.getStringArray( R.array.choose_category ) ;
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,R.layout.dropdown_item,labels1);
        gender.setText(arrayAdapter1.getItem(0).toString(),false);
        gender.setAdapter(arrayAdapter1);

        priceType = findViewById(R.id.autoCompleteTextView3);
        Resources res2 = getResources();
        String[] labels2 =  res2.getStringArray( R.array.price_category ) ;
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,R.layout.dropdown_item,labels2);
        priceType.setText(arrayAdapter2.getItem(0).toString(),false);
        priceType.setAdapter(arrayAdapter2);

        tl1 = findViewById(R.id.pet_category);

        tl2 = findViewById(R.id.signup_fullname);
        t2 = findViewById(R.id.fullname);

        tl3 = findViewById(R.id.event_date);
        t3 = findViewById(R.id.eventDate_editText);

        tl4 = findViewById(R.id.event_time);

        tl5 = findViewById(R.id.pet_breed);
        t5 = findViewById(R.id.petBreedEdittext);

        tl6 = findViewById(R.id.signup_email);
        t6 = findViewById(R.id.bookDescription);

        tl7 = findViewById(R.id.petPrice);
        t7 = findViewById(R.id.petPriceEditText);

        tl8 = findViewById(R.id.priceType);

        tl9 = findViewById(R.id.location);
        t9 = findViewById(R.id.location_edittext);

        petCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
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
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl3.setError(null);
                tl3.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        gender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl4.setError(null);
                tl4.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl5.setError(null);
                tl5.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl6.setError(null);
                tl6.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl7.setError(null);
                tl7.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        priceType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl8.setError(null);
                tl8.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl9.setError(null);
                tl9.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Pet_Details_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t9.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    checkDetails();
                }
                return false;
            }
        });

        upload = findViewById(R.id.signup_next_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

        requestStoragePermission();

        imagePicker = findViewById(R.id.imageofCrop);
        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Upload_Pet_Details_Page.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        petImage = findViewById(R.id.cropImage);
    }

    private void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(Upload_Pet_Details_Page.this)
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

                        ProgressDialog progressDialog = new ProgressDialog(Upload_Pet_Details_Page.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setTitle("Logging out");
                        progressDialog.setProgress(300);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                User user = new User(Upload_Pet_Details_Page.this);
                                user.removeUser();
                                Intent intent = new Intent(Upload_Pet_Details_Page.this,Login_Page.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }}, 600);
                        progressDialog.show();


                    }
                }).build();
        mDialog.show();
    }


    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                petImage.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {
                Toast.makeText(this, "Please insert event image", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void checkDetails()
    {
        pCategory = petCategory.getText().toString();
        pName = t2.getText().toString();
        pAge = t3.getText().toString();
        pGender = gender.getText().toString();
        pBreed = t5.getText().toString();
        pABout = t6.getText().toString();
        pPrice = t7.getText().toString();
        pPriceType = priceType.getText().toString();
        pLocation = t9.getText().toString();
        if(TextUtils.isEmpty(pName) && TextUtils.isEmpty(pAge) && TextUtils.isEmpty(pBreed) && TextUtils.isEmpty(pABout) && TextUtils.isEmpty(pPrice) && TextUtils.isEmpty(pLocation) && pCategory.equals("Pet Category") && pGender.equals("Select Gender") && pPriceType.equals("Price Type"))
        {
            tl1.setError("Required");
            tl2.setError("Required");
            tl3.setError("Required");
            tl4.setError("Required");
            tl5.setError("Required");
            tl6.setError("Required");
            tl7.setError("Required");
            tl8.setError("Required");
            tl9.setError("Required");

        }
        else if(pCategory.equals("Pet Category"))
        {
            tl1.setError("Please select pet category");
        }
        else if(pName.isEmpty())
        {
            tl2.setError("Enter Pet Name");
            t2.setSelection(pName.length());
            tl2.requestFocus();
        }
        else if(pAge.isEmpty())
        {
            tl3.setError("Enter Pet Age");
            t3.setSelection(pAge.length());
            tl3.requestFocus();
        }
        else if(!mobile.matcher(pAge).matches())
        {
            tl3.setError("Enter Pet Age");
            t3.setSelection(pAge.length());
            tl3.requestFocus();
        }
        else if(gender.equals("Select Gender"))
        {
            tl4.setError("Please select pet gender");
        }
        else if(pBreed.isEmpty())
        {
            tl5.setError("Enter pet breed");
            t5.setSelection(pBreed.length());
            tl5.requestFocus();
        }
        else if(pABout.isEmpty())
        {
            tl6.setError("Enter pet description");
            t6.setSelection(pABout.length());
            tl6.requestFocus();
        }
        else if(pPrice.isEmpty())
        {
            tl7.setError("Enter pet price");
            t7.setSelection(pPrice.length());
            tl7.requestFocus();
        }
        else if(pPriceType.equals("Price Type"))
        {
            tl8.setError("Required");
        }
        else if(pLocation.isEmpty())
        {
            tl9.setError("Enter pet location");
            t9.setSelection(pLocation.length());
            tl9.requestFocus();
        }
        else
        {
            uploadData(pCategory,pName,pAge,pGender,pBreed,pABout,pPrice,pPriceType,pLocation);
        }
    }

    private void uploadData(String pCategory, String pName, String pAge, String pGender, String pBreed, String pABout, String pPrice, String pPriceType, String pLocation)
    {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Pet_Details_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Adding pet details......");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/insert_pet_details.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success"))
                {
                    progressDialog.dismiss();
                    MaterialDialog mDialog = new MaterialDialog.Builder(Upload_Pet_Details_Page.this)
                            .setTitle("Success")
                            .setMessage("Pet Details Uploaded Successfully").setCancelable(false).setPositiveButton("Done", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                                @Override
                                public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                    onBackPressed();
                                }
                            })
                            .setNegativeButton("Okay", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
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
                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Upload_Pet_Details_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("pCategory",pCategory);
                param.put("pName",pName);
                param.put("pAge",pAge);
                param.put("pGender",pGender);
                param.put("pBreed",pBreed);
                param.put("pABout",pABout);
                param.put("pPrice",pPrice);
                param.put("pPriceType",pPriceType);
                param.put("pLocation",pLocation);
                param.put("eImage",encodeImageString);
                param.put("emailid",email_id);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Upload_Pet_Details_Page.this).addToRequestQueue(request);
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
                Intent intent = new Intent(Upload_Pet_Details_Page.this,Upload_Pet_Details_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        eventUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upload_Pet_Details_Page.this,Upload_Event_Details_Form_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        reqUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upload_Pet_Details_Page.this,Pet_Request_Form.class);
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
            Intent intent = new Intent(Upload_Pet_Details_Page.this,View_Events_List.class);
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