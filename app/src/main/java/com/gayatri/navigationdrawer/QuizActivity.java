package com.gayatri.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    private static final String EXTRA_SCORE ="extrascore" ;
    private static final String KEY_SCORE="keyScore";
    private static final String KEY_QUESTION_COUNT="keyQuestionCount";
    private static final String KEY_ANSWERED="keyAnswered";
    private static final String KEY_QUESTION_LIST="keyQuestionList";

    private TextView textViewquestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private RadioGroup radioGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button btn_submit;

    private ColorStateList textColorDefaultRb;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;
    private ArrayList<Question> questionList;
    public static int QUIZ_CATEGORY=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.ramayana_quiz, contentFrameLayout);*/


        setContentView(R.layout.ramayana_quiz);

        Intent intent=getIntent();
        intent.getIntExtra("QUIZ_CATEGORY",QUIZ_CATEGORY);

        textViewquestion=findViewById(R.id.text_view_question);
        textViewScore=findViewById(R.id.ramayana_text_score);
        textViewQuestionCount=findViewById(R.id.ramayana_qstn_count);
        radioGroup=findViewById(R.id.ramayan_radion_grp);
        rb1=findViewById(R.id.radion_button1);
        rb2=findViewById(R.id.radion_button2);
        rb3=findViewById(R.id.radion_button3);
        rb4=findViewById(R.id.radion_button4);
        btn_submit=findViewById(R.id.btn_submit);

        textColorDefaultRb=rb1.getTextColors();

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            textColorDefaultRb = rb1.getTextColors();
            questionList = dbHelper.getAllQuestions(QUIZ_CATEGORY);
            questionCountTotal = questionList.size();
            //Toast.makeText(QuizActivity.this, "Current situation in if " + questionCountTotal + questionCounter, Toast.LENGTH_SHORT).show();
            Collections.shuffle(questionList);
            showNextQuestion();
        }else {
            questionList=savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);

            if(questionList==null) {
                finish();
            }
            questionCountTotal=questionList.size();
            questionCounter=savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion=questionList.get(questionCounter -1);
            score=savedInstanceState.getInt(KEY_SCORE);
            answered=savedInstanceState.getBoolean(KEY_ANSWERED);
            //Toast.makeText(QuizActivity.this, "Current situation in else " + questionCountTotal + questionCounter , Toast.LENGTH_SHORT).show();
            if(answered){
                showSolution();
            }

        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,"I am in clicklistener" + questionCounter,Toast.LENGTH_SHORT).show();
                if(!answered){
                    //Toast.makeText(QuizActivity.this,"I am in clicklistener if loop" + questionCounter,Toast.LENGTH_SHORT).show();
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this,"Please select an answer",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });

    }

    private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        radioGroup.clearCheck();

        if(questionCounter < questionCountTotal ){
            currentQuestion=questionList.get(questionCounter);
            textViewquestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            questionCounter++;
            textViewQuestionCount.setText("Question:" + questionCounter + "/" + questionCountTotal);
            answered=false;
            btn_submit.setText("Confirm");
        }else {
            finishQuiz();
        }
    }


    private void checkAnswer(){
        answered=true;
        RadioButton rbSelected=findViewById(radioGroup.getCheckedRadioButtonId());
        int answerno= radioGroup.indexOfChild(rbSelected) +1;
        if(answerno==currentQuestion.getAnswerno()){
        score++;
        textViewScore.setText("score:" + score);
        }
        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestion.getAnswerno()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewquestion.setText("Answer " + currentQuestion.getOption1() + "is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewquestion.setText("Answer " + currentQuestion.getOption2() + "is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewquestion.setText("Answer " + currentQuestion.getOption3() + "is correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewquestion.setText("Answer " + currentQuestion.getOption4() + "is correct");
                break;
        }

        //Toast.makeText(QuizActivity.this,"Current situation" + questionCountTotal + questionCounter,Toast.LENGTH_SHORT).show();
        if(questionCounter < questionCountTotal){
        btn_submit.setText("Next");


        } else {
            btn_submit.setText("Finish");
        }
    }

    public void finishQuiz(){
         finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,score);
        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
        outState.putBoolean(KEY_ANSWERED,answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionList);

    }
}
