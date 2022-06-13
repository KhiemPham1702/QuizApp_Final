package com.example.quiz_final;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.quiz_final.databinding.ActivityResultLayoutBinding;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Result_layout extends AppCompatActivity {
    ActivityResultLayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_layout);
        Intent intent=getIntent();
        Bundle args=intent.getBundleExtra("BUNDLE");
        ArrayList<state_answer> state_answers = (ArrayList<state_answer>) args.getSerializable("state_answers");

        final stateAdapter adapter = new stateAdapter(this, state_answers);
                        adapter.notifyDataSetChanged();
                        binding.a.setLayoutManager(new GridLayoutManager(this, 1));
                        binding.a.setAdapter(adapter);
    }
}