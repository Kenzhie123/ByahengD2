package com.example.byahed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Interface extends AppCompatActivity {

    EditText LI_EmailText;
    EditText LI_PasswordText;
    Button LI_LogInButton;
    Button LI_CreateAccountButton;
    Button LI_GuestButton;

    TextView Overlay;
    ProgressBar Pbar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    void Enable()
    {
        Overlay.setVisibility(View.GONE);
        Pbar.setVisibility(View.GONE);
        LI_LogInButton.setEnabled(true);
        LI_CreateAccountButton.setEnabled(true);
        LI_GuestButton.setEnabled(true);
    }
    void Disable()
    {
        Overlay.setVisibility(View.VISIBLE);
        Pbar.setVisibility(View.VISIBLE);
        LI_LogInButton.setEnabled(false);
        LI_CreateAccountButton.setEnabled(false);
        LI_GuestButton.setEnabled(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_interface);

        LI_EmailText = findViewById(R.id.LI_EmailText);
        LI_PasswordText = findViewById(R.id.LI_PasswordText);
        LI_LogInButton = findViewById(R.id.LI_LogInButton);
        LI_CreateAccountButton = findViewById(R.id.LI_CreateAccountButton);
        LI_GuestButton = findViewById(R.id.LI_GuestButton);

        Overlay = findViewById(R.id.LI_Overlay);
        Pbar = findViewById(R.id.LI_ProgressBar);

        Intent CreateAccount = new Intent(this,CreateAccount_Interface.class);
        Intent Main = new Intent(this,MainActivity.class);

        LI_LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disable();
                int Error = 0;
                if(LI_EmailText.getText().toString().isEmpty())
                {
                    LI_EmailText.setError("Field Cannot Be Empty");
                    Error++;
                }
                if(LI_PasswordText.getText().toString().isEmpty())
                {
                    LI_PasswordText.setError("Field Cannot Be Empty");
                    Error++;
                }

                if(Error == 0)
                {
                    mAuth.signInWithEmailAndPassword(LI_EmailText.getText().toString(),LI_PasswordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                startActivity(Main);
                                Enable();
                                finish();

                            }
                            else
                            {

                                if(task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                                {
                                    Toast.makeText(getApplicationContext(),"User does not exist",Toast.LENGTH_LONG).show();

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                }
                                Enable();
                            }
                        }
                    });
                }
                else
                {
                    Enable();
                }
            }
        });

        LI_CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CreateAccount);
                finish();
            }
        });

        LI_GuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}