package com.example.quiz_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_layout extends AppCompatActivity {
    TextView signUp, login, forgot;
    EditText email, pass;
    FirebaseAuth mAuth;
    boolean isNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        signUp = (TextView) findViewById(R.id.sign_up);
        login = (TextView) findViewById(R.id.login1);
        forgot = (TextView) findViewById(R.id.forgot_pass);
        email = (EditText) findViewById(R.id.txtEmail);
        pass = (EditText) findViewById(R.id.txtPass);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_layout.this, Sign_up_layout.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString().trim();
                String text_pass = pass.getText().toString().trim();

                if(text_email.isEmpty() || text_pass.isEmpty()) {
                    Toast.makeText(login_layout.this, "Please fill in all the information!!!",Toast.LENGTH_SHORT).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()) {
                    Toast.makeText(login_layout.this, "Please provide valid email!!!",Toast.LENGTH_SHORT).show();
                    email.setText("");
                } else {
                    loginUser();
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_layout.this, Forgot_pass_layout.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(){
        String usr_email = email.getText().toString();
        String usr_password = pass.getText().toString();

        mAuth.signInWithEmailAndPassword(usr_email,usr_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(login_layout.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent a = getIntent();
                    isNew = a.getBooleanExtra("is new",false);
                    if(isNew) {
                        Intent intent = new Intent(login_layout.this, Nickname_layout.class);
                        intent.putExtra("email",usr_email);
                        startActivity(intent);
                        Profile myFragment=new Profile();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                                .beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("email",usr_email);
                        myFragment.setArguments(bundle);

                    } else {
                        Intent intent = new Intent(login_layout.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(login_layout.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}