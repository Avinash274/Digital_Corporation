package com.example.petscorner;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    SharedPreferences sharedPreferences;
    private String email,id,pwd;

    public User(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("login_details",context.MODE_PRIVATE);

    }

    public String getEmail() {
        email = sharedPreferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).commit();
    }

    public String getId() {
        id = sharedPreferences.getString("id","");
        return id;
    }

    public void setId(String id) {
        this.id = id;
        sharedPreferences.edit().putString("id",id).commit();
    }

    public String getPwd() {
        pwd = sharedPreferences.getString("pwd","");
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        sharedPreferences.edit().putString("pwd",pwd).commit();
    }

    public void removeUser()
    {
        sharedPreferences.edit().clear().commit();
    }

}
