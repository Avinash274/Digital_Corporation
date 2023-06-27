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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Upload_Event_Details_Form_Page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //EditTextBoxes
    TextInputLayout dateSelector,timeSelector;
    TextInputEditText dateEditor,timeEditor;
    int currentHour,currentMinute;
    String amPm,email_id;
    TimePickerDialog timePickerDialog;

    TextInputLayout tl1,tl2,tl3,tl4,tl5,tl6,tl7,tl8;
    TextInputEditText t1,t2,t3,t4,t5,t6,t7,t8;
    Button upload;
    RelativeLayout imagePicker;
    String eTitle,eDate,eTime,eDesc,eLocation,eState,eContact,eEventLink;

    ImageView eImage;

    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    String encodeImageString;

    private static final Pattern mobile =
            Pattern.compile("[6-9][0-9]{9}");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event_details_form_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        dateSelector = findViewById(R.id.event_date);
        dateEditor = findViewById(R.id.eventDate_editText);

        timeSelector = findViewById(R.id.event_time);
        timeEditor = findViewById(R.id.eventTimeEditText);


        tl1 = findViewById(R.id.signup_fullname);
        t1 = findViewById(R.id.fullname);

        tl2 = findViewById(R.id.event_date);
        t2 = findViewById(R.id.eventDate_editText);

        tl3 = findViewById(R.id.event_time);
        t3 = findViewById(R.id.eventTimeEditText);

        tl4 = findViewById(R.id.signup_email);
        t4 = findViewById(R.id.bookDescription);

        tl5 = findViewById(R.id.location);
        t5 = findViewById(R.id.location_edittext);

        tl6 = findViewById(R.id.state);
        t6 = findViewById(R.id.state_edittext);

        tl7 = findViewById(R.id.contactNumber);
        t7 = findViewById(R.id.contactNumberEditText);

        tl8 = findViewById(R.id.registrationLink);
        t8 = findViewById(R.id.registrationLinkEditText);

        upload = findViewById(R.id.signup_next_button);
        imagePicker = findViewById(R.id.imageofCrop);
        eImage = findViewById(R.id.cropImage);

        t8.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    checkDetails();
                }
                return false;
            }
        });

        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
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
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
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
                tl3.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
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
                tl4.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
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
                tl5.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
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
                tl6.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
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
                tl7.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        t8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl8.setError(null);
                tl8.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Upload_Event_Details_Form_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar() {
                String Format = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                dateEditor.setText(sdf.format(calendar.getTime()));
            }
        };

        dateEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Upload_Event_Details_Form_Page.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Upload_Event_Details_Form_Page.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay >= 12)
                        {
                            amPm = "PM";
                        }else {
                            amPm = "AM";
                        }
                        timeEditor.setText(String.format("%02d:%02d",hourOfDay,minute)+amPm);
                        timeEditor.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);
                timePickerDialog.show();
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#eb4a69\">" + getString(R.string.Add_Event) + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent = new Intent(Upload_Event_Details_Form_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search_pets:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.add_pets_requests:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showDialog();
                        break;

                    case R.id.add_events:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,Upload_Event_Details_Form_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.change_password:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,Change_Password_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contact_us:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,Contact_Us_Page.class);
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
                        Intent intent = new Intent(Upload_Event_Details_Form_Page.this,Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;

                    case R.id.bottom_search_pets:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,Search_Pets_First_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.bottom_pet_requests:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,View_Pet_Requests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);
                        break;

                    case R.id.bottom_add:
                        showDialog();
                        break;

                    case R.id.bottom_account:
                        intent = new Intent(Upload_Event_Details_Form_Page.this,My_Account_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        requestStoragePermission();

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Upload_Event_Details_Form_Page.this)
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

    }

    private void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(Upload_Event_Details_Form_Page.this)
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

                        ProgressDialog progressDialog = new ProgressDialog(Upload_Event_Details_Form_Page.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setTitle("Logging out");
                        progressDialog.setProgress(300);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                User user = new User(Upload_Event_Details_Form_Page.this);
                                user.removeUser();
                                Intent intent = new Intent(Upload_Event_Details_Form_Page.this,Login_Page.class);
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
                eImage.setImageBitmap(bitmap);
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
        eTitle = t1.getText().toString();
        eDate = t2.getText().toString();
        eTime = t3.getText().toString();
        eDesc = t4.getText().toString();
        eLocation = t5.getText().toString();
        eState = t6.getText().toString();
        eContact = t7.getText().toString();
        eEventLink = t8.getText().toString();
        if(TextUtils.isEmpty(eTitle) && TextUtils.isEmpty(eDate) && TextUtils.isEmpty(eTime) && TextUtils.isEmpty(eDesc) && TextUtils.isEmpty(eLocation) && TextUtils.isEmpty(eState) && TextUtils.isEmpty(eContact) && TextUtils.isEmpty(eEventLink))
        {
            tl1.setError("Enter Event Title");
            tl2.setError("Enter Event Data");
            tl3.setError("Enter Event Time");
            tl4.setError("Enter Event Description");
            tl5.setError("Enter  Location");
            tl6.setError("Enter  State");
            tl7.setError("Enter  Contact Number");
            tl8.setError("Enter  Event Link");
        }
        else if(eTitle.isEmpty())
        {
            tl1.setError("Enter Event Title");
            t1.setSelection(eTitle.length());
            tl1.requestFocus();
        }
        else if(eDate.isEmpty())
        {
            tl2.setError("Enter Event Date");
            t2.setSelection(eDate.length());
            tl2.requestFocus();
        }
        else if(eTime.isEmpty())
        {
            tl3.setError("Enter Event Time");
            t3.setSelection(eTime.length());
            tl3.requestFocus();
        }
        else if(eDesc.isEmpty())
        {
            tl4.setError("Enter Event Description");
            t4.setSelection(eDesc.length());
            tl4.requestFocus();
        }
        else if(eLocation.isEmpty())
        {
            tl5.setError("Enter Event Location");
            t5.setSelection(eLocation.length());
            tl5.requestFocus();
        }
        else if(eState.isEmpty())
        {
            tl6.setError("Enter Event State");
            t6.setSelection(eState.length());
            tl6.requestFocus();
        }
        else if(eContact.isEmpty())
        {
            tl7.setError("Enter Event Contact Number");
            t7.setSelection(eContact.length());
            tl7.requestFocus();
        }
        else if(!mobile.matcher(eContact).matches())
        {
            tl7.setError("Please Enter a Valid Contact Number");
            t7.setSelection(eContact.length());
            tl7.requestFocus();
        }
        else if(eEventLink.isEmpty())
        {
            tl8.setError("Enter Event Link");
            t8.setSelection(eEventLink.length());
            tl8.requestFocus();
        }
        else
        {
            uploadData(eTitle,eDate,eTime,eDesc,eLocation,eState,eContact,eEventLink);
        }

    }

    private void uploadData(String eTitle, String eDate, String eTime, String eDesc, String eLocation, String eState, String eContact, String eEventLink) {
        ProgressDialog progressDialog = new ProgressDialog(Upload_Event_Details_Form_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Adding Event......");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/insert_event_details.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success"))
                {
                    progressDialog.dismiss();
                    MaterialDialog mDialog = new MaterialDialog.Builder(Upload_Event_Details_Form_Page.this)
                            .setTitle("Success")
                            .setMessage("Event Created Successfully").setCancelable(false).setPositiveButton("Done", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
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
                Toast.makeText(Upload_Event_Details_Form_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("eTitle",eTitle);
                param.put("eDate",eDate);
                param.put("eTime",eTime);
                param.put("eDesc",eDesc);
                param.put("eLocation",eLocation);
                param.put("eState",eState);
                param.put("eContact",eContact);
                param.put("eEventLink",eEventLink);
                param.put("eImage",encodeImageString);
                param.put("emailid",email_id);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Upload_Event_Details_Form_Page.this).addToRequestQueue(request);
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
                Intent intent = new Intent(Upload_Event_Details_Form_Page.this,Upload_Pet_Details_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        eventUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upload_Event_Details_Form_Page.this,Upload_Event_Details_Form_Page.class);
                intent.putExtra("email_id",email_id);
                startActivity(intent);
                dialog.cancel();
            }
        });

        reqUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upload_Event_Details_Form_Page.this,Pet_Request_Form.class);
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
            Intent intent = new Intent(Upload_Event_Details_Form_Page.this,View_Events_List.class);
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