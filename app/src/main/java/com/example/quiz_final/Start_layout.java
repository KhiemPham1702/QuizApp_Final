package com.example.quiz_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quiz_final.databinding.ActivityCongratulationsLayoutBinding;
import com.example.quiz_final.databinding.ActivityStartLayoutBinding;

public class Start_layout extends AppCompatActivity {
    TextView stated;
    View question, message, messageTxt, mo;
   public static   MediaPlayer mediaPlayer;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_layout);

        stated = (TextView) findViewById(R.id.get_started);
        question = findViewById(R.id.help);
        message = findViewById(R.id.message);
        messageTxt = findViewById(R.id.textMessage);
        mo = findViewById(R.id.mo_star);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        mediaPlayer.start();
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.put("unvoice","0");
//        editor.commit();
        stated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start_layout.this, login_layout.class);
                startActivity(intent);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getVisibility() == View.VISIBLE) {
                    message.setVisibility(View.INVISIBLE);
                    messageTxt.setVisibility(View.INVISIBLE);
                    mo.setVisibility(View.INVISIBLE);
                } else {
                    message.setVisibility(View.VISIBLE);
                    messageTxt.setVisibility(View.VISIBLE);
                    mo.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}