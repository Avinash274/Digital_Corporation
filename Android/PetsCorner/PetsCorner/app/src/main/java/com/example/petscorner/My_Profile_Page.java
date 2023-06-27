package com.example.petscorner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class My_Profile_Page extends AppCompatActivity {

    ImageView back;
    CircleImageView profile_picture;
    TextView userName,user_surname,userEmailId,myPosts,myRequests;

    String photo,email_id,text_username,text_user_surname,text_email_id,text_Contact_Number,user_entered_firstName,user_entered_lastName,user_entered_ContactNumber,posts,requests;

    TextInputLayout tl1,tl2,tl3;
    TextInputEditText t1,t2,t3;
    Button update;

    MaterialCardView myPostsCard,myRequestCards;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String cameraPermission[];
    String storagePermission[];


    private static final Pattern mobile =
            Pattern.compile("[6-9][0-9]{9}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        User user = new User(My_Profile_Page.this);
        email_id = new User(My_Profile_Page.this).getEmail();


        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        back = findViewById(R.id.gobackto_forgotemailone);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Profile_Page.this,My_Account_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        profile_picture = findViewById(R.id.profile_picture1);
        userName = findViewById(R.id.user_firstName);
        user_surname = findViewById(R.id.user_surname);
        userEmailId = findViewById(R.id.userEmailId);

        myPosts = findViewById(R.id.count_pet);
        myRequests = findViewById(R.id.requests);

        getUserName();
        getMyPosts();

        tl1 = findViewById(R.id.signup_fullname);
        t1 = findViewById(R.id.fullname);

        tl2 = findViewById(R.id.sign_up_last_name);
        t2 = findViewById(R.id.last_name);

        tl3 = findViewById(R.id.signup_username);
        t3 = findViewById(R.id.contact);

        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(My_Profile_Page.this,R.color.button_color));
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
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(My_Profile_Page.this,R.color.button_color));
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
                tl3.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(My_Profile_Page.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    checkDetails();
                }
                return false;
            }
        });

        update = findViewById(R.id.createAccount);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(My_Profile_Page.this);
                builder.setMessage("Upload Profile Picture").setCancelable(false).setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(My_Profile_Page.this,Upload_Profile_Picture.class);
                        intent.putExtra("email_id",email_id);
                        startActivity(intent);

                    }
                }).setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeImage(email_id);
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });

        myPostsCard = findViewById(R.id.myPosts);
        myPostsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Profile_Page.this,My_Uploads_Posts.class);
                intent.putExtra("email",email_id);
                startActivity(intent);
            }
        });

        myRequestCards = findViewById(R.id.myRequestCards);
        myRequestCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Profile_Page.this,My_Uploads_Posts.class);
                intent.putExtra("email",email_id);
                startActivity(intent);
            }
        });
    }

    private void getMyPosts()
    {

        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/get_count_posts.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject json = new JSONObject(response);
                    posts = json.getString("books");
                    requests = json.getString("requests");

                    myPosts.setText(posts);
                    myRequests.setText(requests);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(My_Profile_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("email_id",email_id);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(My_Profile_Page.this.getApplicationContext()).addToRequestQueue(request);
    }

    private void removeImage(String email_id) {
        ProgressDialog progressDialog = new ProgressDialog(My_Profile_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Removing Profile Picture..");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/remove_profile_pic.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Removed Successfully"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(My_Profile_Page.this, response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(My_Profile_Page.this,My_Profile_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                Toast.makeText(My_Profile_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",email_id);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(1000000000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(My_Profile_Page.this).addToRequestQueue(request);
    }

    private void checkDetails() {
        user_entered_firstName = t1.getText().toString();
        user_entered_lastName = t2.getText().toString();
        user_entered_ContactNumber = t3.getText().toString();
        if(user_entered_firstName.isEmpty() && user_entered_lastName.isEmpty()  && user_entered_ContactNumber.isEmpty())
        {
            tl1.setError("Enter First Name");
            tl2.setError("Enter Last Name");
            tl3.setError("Enter Contact Number");
            tl1.requestFocus();
        }
        else if(user_entered_firstName.isEmpty())
        {
            tl1.setError("Enter First Name");
            tl1.requestFocus();
        }
        else if(!user_entered_firstName.matches("[a-zA-Z ]+"))
        {
            tl1.setError("Please Enter a Valid Name");
            t1.setSelection(user_entered_firstName.length());
            tl1.requestFocus();
        }
        else if(user_entered_lastName.isEmpty())
        {
            tl2.setError("Please Enter Your Last Name");
            t2.setSelection(user_entered_lastName.length());
            tl2.requestFocus();
        }
        else if(!user_entered_lastName.matches("[a-zA-Z ]+"))
        {
            tl2.setError("Please Enter a Valid Name");
            t2.setSelection(user_entered_lastName.length());
            tl2.requestFocus();
        }
        else if(user_entered_ContactNumber.isEmpty())
        {
            tl3.setError("Please Enter Contact Number");
            t3.setSelection(user_entered_ContactNumber.length());
            tl3.requestFocus();
        }
        else if(!mobile.matcher(user_entered_ContactNumber).matches())
        {
            tl3.setError("Please Enter a Valid Contact Number");
            t3.setSelection(user_entered_ContactNumber.length());
            tl3.requestFocus();
        }
        else
        {
            uploadData(email_id,user_entered_firstName,user_entered_lastName,user_entered_ContactNumber);

        }

    }

    private void uploadData(String email_id, String user_entered_firstName, String user_entered_lastName, String user_entered_contactNumber) {
        ProgressDialog progressDialog = new ProgressDialog(My_Profile_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Updating Profile");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/update_profile.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Updated"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(My_Profile_Page.this, "Profile Details Updated", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(My_Profile_Page.this,My_Account_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(My_Profile_Page.this, "Error while Updating", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(My_Profile_Page.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",email_id);
                param.put("name",user_entered_firstName);
                param.put("lastName",user_entered_lastName);
                param.put("contact",user_entered_contactNumber);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(My_Profile_Page.this).addToRequestQueue(request);
    }

    private void getUserName() {
        ProgressDialog progressDialog = new ProgressDialog(My_Profile_Page.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Creating Profile");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/getUserName_Count.php";

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
                        text_Contact_Number = student.getString("contactNumber");

                        photo = "http://192.168.43.103/PetsCorner_Android_Php/images/" + student.getString("profile_pic");

                        userName.setText(text_username);
                        user_surname.setText(text_user_surname);
                        userEmailId.setText(text_email_id);
                        Glide.with(getApplicationContext()).load(photo).into(profile_picture);

                        t1.setText(text_username);
                        t2.setText(text_user_surname);
                        t3.setText(text_Contact_Number);
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
                Toast.makeText(My_Profile_Page.this, error.toString(), Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(My_Profile_Page.this).addToRequestQueue(request);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(My_Profile_Page.this,My_Account_Page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}