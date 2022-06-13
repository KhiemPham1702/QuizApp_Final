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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_pass_layout extends AppCompatActivity {
    View back;
    EditText email;
    TextView submit;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_layout);

        back = (View) findViewById(R.id.undo);
        email = (EditText) findViewById(R.id.forgot_email);
        submit = (TextView) findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgot_pass_layout.this, login_layout.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString().trim();
                if(text_email.isEmpty()) {
                    Toast.makeText(Forgot_pass_layout.this, "Please fill in all the information!!!",Toast.LENGTH_SHORT).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()) {
                    Toast.makeText(Forgot_pass_layout.this, "Please provide valid email!!!",Toast.LENGTH_SHORT).show();
                    email.setText("");
                } else {
                    resetPassword();
                    Intent intent = new Intent(Forgot_pass_layout.this, login_layout.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void resetPassword() {
        String forgot_email = email.getText().toString().trim();
        mAuth.sendPasswordResetEmail(forgot_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Forgot_pass_layout.this,"Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Forgot_pass_layout.this,"Your email was wrong! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}