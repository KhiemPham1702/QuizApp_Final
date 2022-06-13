package com.example.quiz_final;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quiz_final.databinding.ActivityQuizLayoutBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Quiz_layout extends AppCompatActivity {

    ActivityQuizLayoutBinding binding;

    ArrayList<Question> questions;
    ArrayList<String>iD=new ArrayList<>();
    int index = 0;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    Question question;
    CountDownTimer timer;
    int correctAnswers = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questions = new ArrayList<>();
        Intent intent=getIntent();
        final String cat = intent.getStringExtra("cat");
        final String testId = getIntent().getStringExtra("testId");
        final String course = getIntent().getStringExtra("course");
        Start_layout.mediaPlayer.pause();
        if(course.equals("English"))
        {
            binding.quizApp.setBackgroundResource(R.drawable.english);
        }
        else
        if(course.equals("Math"))
        {
            binding.quizApp.setBackgroundResource(R.drawable.math);
        }
        else
        if(course.equals("Physics"))
        {
            binding.quizApp.setBackgroundResource(R.drawable.physics);
        } else
        if(course.equals("Chemistry"))
        {
            binding.quizApp.setBackgroundResource(R.drawable.chemistry);
        } else
        if(course.equals("Exam"))
        {
            binding.quizApp.setBackgroundResource(R.drawable.exam);
        } else
        if(course.equals("Biology"))
        {
            binding.quizApp.setBackgroundResource(R.drawable.biology);
        }
        database = FirebaseFirestore.getInstance();
        binding.course.setText(course);
        Random random = new Random();
        final int rand = random.nextInt(12);
        database.collection("Courses")
                .document(cat)
                .collection("Test")
                .document(testId)
                .collection("questions")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() < 999) {
                    database.collection("Courses")
                            .document(cat)
                            .collection("Test")
                            .document(testId)
                            .collection("questions")
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            questions.clear();
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
//                                iD.add(snapshot.getId());
                            }
                            setNextQuestion();
                        }
                    });
                } else {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();

                }
            }
        });

        resetTimer();
        timer.start();

    }

    void resetTimer() {
        Intent intent=getIntent();
        final String time = intent.getStringExtra("time");
        long duration=TimeUnit.MINUTES.toMillis(Long.parseLong(time));
        timer = new CountDownTimer(duration,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration=String.format(Locale.ENGLISH,"%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        ,TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                binding.timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                Intent intent3 = new Intent(Quiz_layout.this, timeout_layout.class);
                intent3.putExtra("correct", correctAnswers);
                intent3.putExtra("total", questions.size());
                intent3.putExtra("nameCourse",binding.course.getText());
//                intent2.putExtra("state_answers",state_answers);
                startActivity(intent3);
//              Toast toast= Toast.makeText(Quiz_layout.this,"endddd",Toast.LENGTH_LONG);
//              toast.show();

            }
        };
    }

    void showAnswer() {
        if(question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
    }

    void setNextQuestion() {
        if(timer != null)
            timer.cancel();
        timer.start();
        if (index < questions.size()) {
            binding.next.setVisibility(View.VISIBLE);
            binding.count.setText(String.format("%d/%d", (index + 1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
            if(index!=0){
                playAnima(binding.question,0,0);
                playAnima(binding.option1,0,1);
                playAnima(binding.option2,0,2);
                playAnima(binding.option3,0,3);
                playAnima(binding.option4,0,4);
            }
        }
        else{

            binding.next.setVisibility(View.INVISIBLE);
            binding.option1.setVisibility(View.INVISIBLE);
            binding.option2.setVisibility(View.INVISIBLE);
            binding.option3.setVisibility(View.INVISIBLE);
            binding.option4.setVisibility(View.INVISIBLE);
        }
    }
    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));

        }
        else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }
    void reset() {
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                TextView selected = (TextView) view;
                checkAnswer(selected);
                break;
            case R.id.undo:
                Intent intent = new Intent(Quiz_layout.this, MainActivity.class);
                startActivity(intent);
            case R.id.next:
                reset();
                if (index <= questions.size()) {
                    index++;
                    setNextQuestion();
                }
                break;
//            case R.id.course:
//                Intent intent3 = new Intent(Quiz_layout.this, Result_layout.class);
//                Bundle args = new Bundle();
//                args.putSerializable("state_answers",(Serializable)state_answers);
//                intent3.putExtra("BUNDLE",args);
//                startActivity(intent3);
//                break;
            case R.id.submit:
                Intent intent2 = new Intent(Quiz_layout.this, Congratulations_layout.class);
                intent2.putExtra("correct", correctAnswers);
                intent2.putExtra("total", questions.size());
                intent2.putExtra("nameCourse",binding.course.getText());
//                intent2.putExtra("state_answers",state_answers);
                startActivity(intent2);
                }

    }
   private void playAnima(View view,final int value,int viewNum){
    view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if(value==0){
                            switch(viewNum){
                                case 0:
                                    ((TextView)view).setText(questions.get(index).getQuestion());
                                    break;
                                case 1:
                                    ((TextView)view).setText(questions.get(index).getOption1());
                                    break;
                                case 2:
                                    ((TextView)view).setText(questions.get(index).getOption2());
                                    break;
                                case 3:
                                    ((TextView)view).setText(questions.get(index).getOption3());
                                    break;
                                case 4:
                                    ((TextView)view).setText(questions.get(index).getOption4());
                                    break;

                            }
                            playAnima(view,1,viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
   }
}