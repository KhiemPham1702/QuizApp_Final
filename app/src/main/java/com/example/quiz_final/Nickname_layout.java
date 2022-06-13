package com.example.quiz_final;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Nickname_layout extends AppCompatActivity {
    EditText nickname;
    TextView submit;
    String email;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_layout);

        fStore = FirebaseFirestore.getInstance();

        nickname = (EditText) findViewById(R.id.txtnickname);
        submit = (TextView) findViewById(R.id.submit);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNickName = "@" + nickname.getText().toString().trim();
                UpdateNickname(email,newNickName);
            }
        });
    }

    private void UpdateNickname(String email, String newNickName) {
        Map<String,Object> userUpdate = new HashMap<>();
        userUpdate.put("nickname",newNickName);

        fStore.collection("users")
                .whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    fStore.collection("users")
                            .document(documentID)
                            .update(userUpdate)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Nickname_layout.this,"Successfully create nickname",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Nickname_layout.this,"Create nickname failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(Nickname_layout.this, MainActivity.class));
                } else {
                    Toast.makeText(Nickname_layout.this, "Error" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}