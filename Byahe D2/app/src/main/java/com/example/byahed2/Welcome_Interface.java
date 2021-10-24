package com.example.byahed2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Welcome_Interface extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_interface);
        Intent main = new Intent(this,MainActivity.class);
        Intent log = new Intent(this,LogIn_Interface.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser()!= null)
                {
                    startActivity(main);

                }
                else
                {
                    startActivity(log);

                }

                finish();

            }
        },2000);


    }


}