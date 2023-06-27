package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Forgot_Password_Four extends AppCompatActivity {

    ImageView back;
    TextInputLayout tl1,tl2;
    TextInputEditText t1,t2;
    String email_id,password,confirmPassword;
    Button newPassword;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_four);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");

        back = findViewById(R.id.create_Back_Button);
        tl1 = findViewById(R.id.signup_username);
        t1 = findViewById(R.id.newPass);
        tl2 = findViewById(R.id.signup_email);
        t2 = findViewById(R.id.confimPass);
        newPassword = findViewById(R.id.otp_verify);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgot_Password_Four.this,Forgot_Password_One.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Forgot_Password_Four.this,R.color.button_color));
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
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Forgot_Password_Four.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        t2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    checkDetails();
                }
                return false;
            }
        });

        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

    }

    private void checkDetails() {
        password = t1.getText().toString();
        confirmPassword = t2.getText().toString();
        if(TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword))
        {
            tl1.setError("Enter New Password");
            tl2.setError("Enter Confirm Password");
        }
        else if(password.isEmpty())
        {
            tl1.setError("Please Enter New Password");
            t1.requestFocus();
        }
        else  if (!PASSWORD_PATTERN.matcher(password).matches())
        {
            tl1.setError("Password too weak!!");
            t1.requestFocus();
            t1.setSelection(password.length());
        }
        else if(confirmPassword.isEmpty())
        {
            tl2.setError("Please Confirm Password");
            t2.requestFocus();
        }
        else  if (!confirmPassword.equals(password))
        {
            tl2.setError("Password does not match!!");
            t2.requestFocus();
            t2.setSelection(confirmPassword.length());
        }
        else
        {
            updatePassword(email_id,password,confirmPassword);
        }
    }

    private void updatePassword(String email_id, String password, String confirmPassword) {
        ProgressDialog progressDialog = new ProgressDialog(Forgot_Password_Four.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Updating Password");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/forgot_update_password.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Password Updated"))
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Forgot_Password_Four.this,Forgot_Password_Fifth.class);
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
                Toast.makeText(Forgot_Password_Four.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",email_id);
                param.put("newPassword",password);
                param.put("confirmPassword",confirmPassword);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Forgot_Password_Four.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Forgot_Password_Four.this,Forgot_Password_One.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}