package com.example.byahed2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount_Interface extends AppCompatActivity {

    Button CreateButton;
    Button CA_BackArrow;
    EditText EmailText;
    EditText PasswordText;
    EditText RepeatPasswordText;

    TextView Overlay;
    ProgressBar PBar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private void ContentDisable()
    {
        CreateButton.setEnabled(false);
        Overlay.setVisibility(View.VISIBLE);
        PBar.setVisibility(View.VISIBLE);
    }

    private void ContentEnable()
    {
        CreateButton.setEnabled(true);
        Overlay.setVisibility(View.GONE);
        PBar.setVisibility(View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_interface);

        CreateButton = findViewById(R.id.CA_CreateButton);
        CA_BackArrow = findViewById(R.id.CA_BackArrow);
        EmailText = findViewById(R.id.CA_EmailText);
        PasswordText = findViewById(R.id.CA_PasswordText);
        RepeatPasswordText = findViewById(R.id.CA_RepeatPasswordText);

        Overlay = findViewById(R.id.CA_ContentOverlay);
        PBar  = findViewById(R.id.CA_ProgressBar);


        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Errors = 0;
                if(EmailText.getText().toString().isEmpty())
                {
                    EmailText.setError("Field Cannot Be Empty");
                    Errors++;
                }

                if(PasswordText.getText().toString().isEmpty())
                {
                    PasswordText.setError("Field Cannot Be Empty");
                    Errors++;
                }
                else
                {
                    if(RepeatPasswordText.getText().toString().isEmpty())
                    {
                        RepeatPasswordText.setError("Field Cannot Be Empty");
                        Errors++;
                    }
                    else
                    {
                        if(!RepeatPasswordText.getText().toString().equals(PasswordText.getText().toString()))
                        {
                            RepeatPasswordText.setError("Password Incorrect");
                            Errors++;
                        }
                    }
                }

                if(Errors == 0)
                {
                    ContentDisable();
                    mAuth.createUserWithEmailAndPassword(EmailText.getText().toString(),PasswordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                ContentEnable();
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Error:"+ e.getMessage(),Toast.LENGTH_LONG).show();
                            ContentEnable();
                        }
                    });
                }
            }
        });

        Intent Log = new Intent(this,LogIn_Interface.class);
        CA_BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Log);
                finish();
            }
        });
    }


}