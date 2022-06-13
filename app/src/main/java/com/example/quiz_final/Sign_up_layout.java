package com.example.quiz_final;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_up_layout extends AppCompatActivity {
    public static final String TAG = "TAG";

    TextView login, signUp;
    EditText email, pass, confirpass;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    User userSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_layout);

        signUp = (TextView) findViewById(R.id.sign_up_1);
        login = (TextView) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.txtEmail);
        pass = (EditText) findViewById(R.id.txtPass);
        confirpass = (EditText) findViewById(R.id.txtConfirPass);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up_layout.this, login_layout.class);
                intent.putExtra("is new", false);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email.getText().toString().trim();
                String text_pass = pass.getText().toString().trim();
                String text_confirpass = confirpass.getText().toString().trim();

                userSignUp = new User(text_email, text_pass, "name", "Empty", "Empty");

                if(text_email.isEmpty() || text_pass.isEmpty() || text_confirpass.isEmpty()) {
                    Toast.makeText(Sign_up_layout.this, "Please fill in all the information!!!",Toast.LENGTH_SHORT).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()) {
                    Toast.makeText(Sign_up_layout.this, "Please provide valid email!!!",Toast.LENGTH_SHORT).show();
                    email.setText("");
                }
                else if (text_pass.compareTo(text_confirpass) != 0) {
                    Toast.makeText(Sign_up_layout.this, "Password does not match, please re-enter!!!",Toast.LENGTH_SHORT).show();
                    pass.setText("");
                    confirpass.setText("");
                } else {
                    createUser();
                }

            }
        });
    }

    private void createUser(){
        String usr_email = email.getText().toString();
        String usr_password = confirpass.getText().toString();

        mAuth.createUserWithEmailAndPassword(usr_email,usr_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Sign_up_layout.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("email",userSignUp.email);
                    user.put("password",userSignUp.pass);
                    user.put("nickname",userSignUp.nickname);
                    user.put("school",userSignUp.school);
                    user.put("phone",userSignUp.phone);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"onFailure: " + e.toString());
                        }
                    });
                    Intent intent = new Intent(Sign_up_layout.this, login_layout.class);
                    intent.putExtra("is new", true);
                    startActivity(intent);
                }else{
                    Toast.makeText(Sign_up_layout.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}