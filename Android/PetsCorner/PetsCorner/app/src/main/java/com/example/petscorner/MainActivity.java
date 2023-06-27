package com.example.petscorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN =800;

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.fade_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.fade_animation);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.mainHeading);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(new User(MainActivity.this).getEmail()!="" || new User(MainActivity.this).getPwd()!="")
                {
                        Intent intent = new Intent(MainActivity.this, Home_Page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, onBoarding_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        },SPLASH_SCREEN);
    }
}