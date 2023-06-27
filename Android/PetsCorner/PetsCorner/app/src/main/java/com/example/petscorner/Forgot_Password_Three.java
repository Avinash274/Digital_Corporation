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

public class Forgot_Password_Three extends AppCompatActivity {

    ImageView back;
    PinView pinView;
    Button verifyOtp;
    String otp,email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_three);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");

        back = findViewById(R.id.otpVerifyBack);
        pinView = findViewById(R.id.pin_view);
        verifyOtp = findViewById(R.id.otp_verify);

        pinView.requestFocus();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgot_Password_Three.this,Forgot_Password_One.class);
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

        verifyOtp.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(Forgot_Password_Three.this, "Please Enter OTP", Toast.LENGTH_LONG).show();
        }
        else
        {
            verifyotp(email_id,otp);
        }
    }

    private void verifyotp(String email_id, String otp) {
        ProgressDialog progressDialog = new ProgressDialog(Forgot_Password_Three.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Verifying OTP");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/forgot_verify_otp.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("OTP Verified"))
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Forgot_Password_Three.this,Forgot_Password_Four.class);
                    intent.putExtra("email_id",email_id);  //forward email id to next page
                    startActivity(intent);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Forgot_Password_Three.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Forgot_Password_Three.this, error.toString(), Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(Forgot_Password_Three.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Forgot_Password_Three.this,Forgot_Password_One.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}