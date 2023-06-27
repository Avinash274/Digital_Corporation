package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Login_Page extends AppCompatActivity {

    TextInputLayout tl1,tl2;
    TextInputEditText t1,t2;
    MaterialButton login;
    String emailId,password;
    TextView signUp,forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tl1 = findViewById(R.id.emailId);
        tl2 = findViewById(R.id.password);
        t1 = findViewById(R.id.email_id);
        t2 = findViewById(R.id.email_password);
        login = findViewById(R.id.signInButton);
        signUp = findViewById(R.id.sign_up);
        forgotPassword = findViewById(R.id.forgot_pass);

        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl1.setError(null);
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Login_Page.this,R.color.button_color));
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
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Login_Page.this,R.color.button_color));
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Page.this,Create_Account_Email_Validation.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Page.this,Forgot_Password_One.class);
                startActivity(intent);
            }
        });
    }

    private void checkDetails() {
        emailId = t1.getText().toString();
        password = t2.getText().toString();
        if(emailId.isEmpty() && password.isEmpty())
        {
            tl1.setError("Enter Email Id");
            tl2.setError("Enter Password");
            tl1.requestFocus();
        }
        else if(emailId.isEmpty())
        {
            tl1.setError("Enter Email Id");
            tl1.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
        {
            tl1.setError("Enter proper emailId");
            tl1.requestFocus();
            t1.setSelection(emailId.length());
        }
        else if(password.isEmpty())
        {
            tl2.setError("Enter Password");
            tl2.requestFocus();
        }
        else
        {
           userLogin(emailId,password);
        }
    }

    private void userLogin(String emailId, String password) {
        ProgressDialog progressDialog = new ProgressDialog(Login_Page.this);
        progressDialog.setMessage("Verifying Credentials");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Login Failed"))
                {
                    progressDialog.dismiss();
                    MaterialDialog mDialog = new MaterialDialog.Builder(Login_Page.this)
                    .setTitle("Authentication Failed")
                    .setMessage("Oops! Looks like you have entered the wrong username or password. Please check your login details and try again. ").setCancelable(false).setPositiveButton("Okay", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                                @Override
                                public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel", R.drawable.clip_cancel, new AbstractDialog.OnClickListener() {
                                @Override
                                public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            }).build();
                    mDialog.show();
                }
                else if(response.equals("Connection Error"))
                {
                    progressDialog.dismiss();
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Login_Page.this);
                    builder.setMessage("Check your Internet Connection and try again..").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
                }
                else if(response.equals("Success"))
                {
                    progressDialog.dismiss();
                    User user = new User(Login_Page.this);
                    user.setEmail(emailId);
                    user.setPwd(password);

                    Intent intent = new Intent(Login_Page.this,Home_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Login_Page.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emailid",emailId);
                param.put("password",password);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Login_Page.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        MaterialDialog mDialog = new MaterialDialog.Builder(Login_Page.this)
                .setTitle("Confirm?")
                .setAnimation(R.raw.quit_two)
                .setMessage("Do you really want to quit?").setCancelable(false).setPositiveButton("Yes", R.drawable.clip_check, new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        Login_Page.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.clip_cancel, new AbstractDialog.OnClickListener() {
                    @Override
                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                }).build();
        mDialog.show();
    }
}