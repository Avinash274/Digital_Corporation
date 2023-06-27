package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Create_Account_Email_Validation extends AppCompatActivity {

    ImageView backButton;
    TextInputLayout tl1;
    TextInputEditText t1;
    Button verify;
    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_email_validation);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        backButton = findViewById(R.id.createAccountBack);
        tl1 = findViewById(R.id.account_email_creation);
        t1 = findViewById(R.id.getemail_id);
        verify = findViewById(R.id.verify_email_id);

        t1.requestFocus();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Account_Email_Validation.this,Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        t1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    checkDetails();
                }
                return false;
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Email_Validation.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void checkDetails() {
        emailId = t1.getText().toString();
        if(TextUtils.isEmpty(emailId))
        {
            tl1.setError("Enter Email Id");
            t1.setSelection(emailId.length());
            tl1.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
        {
            tl1.setError("Enter Valid Email Id");
            t1.setSelection(emailId.length());
            tl1.requestFocus();
        }
        else
        {
            verifyUserEmailId(emailId);
        }
    }

    private void verifyUserEmailId(String emailId) {
        ProgressDialog progressDialog = new ProgressDialog(Create_Account_Email_Validation.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Verifying EmailId");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/account_creation_email_validation.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("This Email is already registerd, try another email"))
                {
                    progressDialog.dismiss();
                    MaterialDialog mDialog = new MaterialDialog.Builder(Create_Account_Email_Validation.this)
                            .setTitle("Response")
                            .setMessage("This Email is already registered,try another email").setCancelable(false).setPositiveButton("Okay", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                                @Override
                                public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                    t1.setSelection(emailId.length());
                                    tl1.requestFocus();
                                }
                            })
                            .setNegativeButton("Cancel", R.drawable.clip_cancel, new AbstractDialog.OnClickListener() {
                                @Override
                                public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    Intent intent = new Intent(Create_Account_Email_Validation.this,Login_Page.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).build();
                    mDialog.show();
                }
                else if(response.equals("Connection Error"))
                {
                    MaterialDialog mDialog = new MaterialDialog.Builder(Create_Account_Email_Validation.this)
                            .setTitle("Error!")
                            .setMessage("Check your internet connection and try again!").setCancelable(false).setPositiveButton("Okay", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                                @Override
                                public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            }).build();
                    mDialog.show();
                }
                else if(response.equals("Email Verified"))
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Create_Account_Email_Validation.this,Create_Account_Email_Validation_Otp.class);
                    intent.putExtra("email_id",emailId);  //forward email id to next page
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Create_Account_Email_Validation.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",emailId);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Create_Account_Email_Validation.this).addToRequestQueue(request);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Create_Account_Email_Validation.this,Login_Page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}