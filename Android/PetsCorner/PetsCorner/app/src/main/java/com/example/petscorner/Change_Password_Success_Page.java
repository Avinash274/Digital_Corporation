package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Change_Password_Success_Page extends AppCompatActivity {

    private static int SPLASH_SCREEN =5000;
    ImageView back;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_success_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        back = findViewById(R.id.gobackto_forgotemailone);
        login = findViewById(R.id.next_otp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(Change_Password_Success_Page.this);
                user.removeUser();
                Intent intent = new Intent(Change_Password_Success_Page.this,Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(Change_Password_Success_Page.this);
                user.removeUser();
                Intent intent = new Intent(Change_Password_Success_Page.this,Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = new User(Change_Password_Success_Page.this);
                user.removeUser();
                Intent intent = new Intent(Change_Password_Success_Page.this,Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        },SPLASH_SCREEN);
    }

    @Override
    public void onBackPressed() {
        User user = new User(Change_Password_Success_Page.this);
        user.removeUser();
        Intent intent = new Intent(Change_Password_Success_Page.this,Login_Page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}