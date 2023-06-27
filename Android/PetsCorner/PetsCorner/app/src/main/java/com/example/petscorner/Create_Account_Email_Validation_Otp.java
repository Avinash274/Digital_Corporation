package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.chaos.view.PinView;

import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Create_Account_Email_Validation_Otp extends AppCompatActivity {

    String email_id,otp;
    ImageView back;
    TextView skip,another_email;
    PinView pinView;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_email_validation_otp);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");

        back = findViewById(R.id.backtocreateAccount);
        skip = findViewById(R.id.skipConfirm);
        another_email = findViewById(R.id.try_another_email);
        pinView = findViewById(R.id.pin_view);
        verify = findViewById(R.id.verifyotp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Account_Email_Validation_Otp.this,Create_Account_Email_Validation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Account_Email_Validation_Otp.this,Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        another_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Account_Email_Validation_Otp.this,Create_Account_Email_Validation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        pinView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
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
    }

    private void checkDetails() {
        otp = pinView.getText().toString();
        if(otp.isEmpty())
        {
            Toast.makeText(Create_Account_Email_Validation_Otp.this, "Please Enter OTP", Toast.LENGTH_LONG).show();
        }
        else
        {
            verifyotp(email_id,otp);
        }
    }

    private void verifyotp(String email_id, String otp) {
        ProgressDialog progressDialog = new ProgressDialog(Create_Account_Email_Validation_Otp.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Validating Pin");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/account_creation_otp_validation.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("OTP Verified"))
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Create_Account_Email_Validation_Otp.this,Create_Account_Three.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("email_id",email_id);  //forward email id to next page
                    startActivity(intent);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Create_Account_Email_Validation_Otp.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Create_Account_Email_Validation_Otp.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",email_id);
                param.put("otp",otp);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Create_Account_Email_Validation_Otp.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Create_Account_Email_Validation_Otp.this,Create_Account_Email_Validation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}