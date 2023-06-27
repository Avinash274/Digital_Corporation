package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.regex.Pattern;

public class Create_Account_Three extends AppCompatActivity {

    ImageView back;
    TextView login;
    String email_id;
    TextInputLayout tl1,tl2,tl3,tl4,tl5,tl6;
    TextInputEditText t1,t2,t3,t4,t5;
    AutoCompleteTextView autoCompleteTextView;
    Button sigin;
    String firstName,lastName,gender,contactNumber,password,confirmPassword;

    private static final Pattern mobile =
            Pattern.compile("[6-9][0-9]{9}");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_three);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent in = getIntent();
        email_id= in.getStringExtra("email_id");

        back = findViewById(R.id.signup_back_button);
        login = findViewById(R.id.try_another_email);
        tl1 = findViewById(R.id.signup_fullname);
        t1 = findViewById(R.id.fullname);
        tl2 = findViewById(R.id.sign_up_last_name);
        t2 = findViewById(R.id.last_name);
        tl3 = findViewById(R.id.signup_username);
        t3 = findViewById(R.id.contact);
        tl4 = findViewById(R.id.signup_email);
        t4 = findViewById(R.id.newPassword);
        tl5 = findViewById(R.id.signup_password);
        t5 = findViewById(R.id.confirmPassword);
        sigin = findViewById(R.id.createAccount);
        tl6 = findViewById(R.id.dropdown_for_category);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Account_Three.this,Create_Account_Email_Validation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Account_Three.this,Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        Resources res = getResources();
        String[] labels =  res.getStringArray( R.array.choose_category ) ;
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.dropdown_item,labels);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);

        autoCompleteTextView.setAdapter(arrayAdapter);

        t5.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                tl1.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Three.this,R.color.button_color));
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
                tl2.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Three.this,R.color.button_color));
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
                tl3.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Three.this,R.color.button_color));
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
                tl4.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Three.this,R.color.button_color));
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
                tl5.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Three.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tl6.setError(null);
                tl6.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(Create_Account_Three.this,R.color.button_color));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });

    }

    private void checkDetails() {
        firstName = t1.getText().toString();
        lastName = t2.getText().toString();
        gender = autoCompleteTextView.getText().toString();
        contactNumber = t3.getText().toString();
        password = t4.getText().toString();
        confirmPassword = t5.getText().toString();

        if(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(contactNumber) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword))
        {
            tl1.setError("Enter First Name");
            tl2.setError("Enter Last Name");
            tl3.setError("Enter Contact Number");
            tl4.setError("Enter Password");
            tl5.setError("Enter Confirm Password");
        }
        else if(firstName.isEmpty())
        {
            tl1.setError("Please Enter Your First Name");
            t1.setSelection(firstName.length());
            tl1.requestFocus();
        }
        else if(!firstName.matches("[a-zA-Z ]+"))
        {
            tl1.setError("Please Enter a Valid Name");
            t1.setSelection(firstName.length());
            tl1.requestFocus();
        }
        else if(lastName.isEmpty())
        {
            tl2.setError("Please Enter Your Last Name");
            t2.setSelection(lastName.length());
            tl2.requestFocus();
        }
        else if(!lastName.matches("[a-zA-Z ]+"))
        {
            tl2.setError("Please Enter a Valid Name");
            t2.setSelection(lastName.length());
            tl2.requestFocus();
        }
        else if(gender.equals("Select Gender"))
        {
            tl6.setError("Please Select Gender");
        }
        else if(contactNumber.isEmpty())
        {
            tl3.setError("Please Enter Contact Number");
            t3.setSelection(contactNumber.length());
            tl3.requestFocus();
        }
        else if(!mobile.matcher(contactNumber).matches())
        {
            tl3.setError("Please Enter a Valid Contact Number");
            t3.setSelection(contactNumber.length());
            tl3.requestFocus();
        }
        else if(password.isEmpty())
        {
            tl4.setError("Please Enter Your Password");
            t4.setSelection(password.length());
            tl4.requestFocus();
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches())
        {
            tl4.setError("Password Too Weak");
            t4.setSelection(password.length());
            tl4.requestFocus();
        }
        else if(confirmPassword.isEmpty())
        {
            tl5.setError("Please Confirm Your Password");
            t5.setSelection(confirmPassword.length());
            tl5.requestFocus();
        }
        else if(!password.equals(confirmPassword))
        {
            tl5.setError("Password Does Not Match");
            t5.setSelection(confirmPassword.length());
            tl5.requestFocus();
        }
        else
        {
            uploadData(email_id,firstName,lastName,gender,contactNumber,password);

        }
    }

    private void uploadData(String email_id, String firstName, String lastName, String gender, String contactNumber, String password) {
        ProgressDialog progressDialog = new ProgressDialog(Create_Account_Three.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Creating Your Account");
        progressDialog.show();
        String uRl = "http://192.168.43.103/PetsCorner_Android_Php/user_account_creation.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Account Created Successfully"))
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Create_Account_Three.this,Create_Account_Four.class);
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
                Toast.makeText(Create_Account_Three.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("email_id",email_id);
                param.put("firstName",firstName);
                param.put("lastName",lastName);
                param.put("gender",gender);
                param.put("contactNumber",contactNumber);
                param.put("password",password);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Create_Account_Three.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Create_Account_Three.this,Create_Account_Email_Validation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}