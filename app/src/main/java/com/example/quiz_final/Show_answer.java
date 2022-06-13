package com.example.quiz_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quiz_final.databinding.ActivityQuizLayoutBinding;
import com.example.quiz_final.databinding.ActivityShowAnswerBinding;

import java.util.ArrayList;

public class Show_answer extends AppCompatActivity {
    ActivityShowAnswerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAnswerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final ArrayList<state_answer> state_answers = new ArrayList<>();
        final stateAdapter adapter = new stateAdapter(this, state_answers);

    }
}