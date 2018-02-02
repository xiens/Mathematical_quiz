package com.example.xiens.quiz2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Author: Zbigniew Pamula
 * version 1.0
 */

public class MainActivity extends AppCompatActivity {
    
    //checked radio button id
    //for questions 1, 2, 3, 4 it is a number from 0-3
    //for questions 5 and 6, 0 - false 1 - true    
    final Integer[] radioButtonIds = new Integer[6]; 

    Boolean[] answers = new Boolean[6]; //answers for questions 0-5
    final DatabaseProvider databaseProvider = new DatabaseProvider(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase(databaseProvider.getDatabaseName());

        final RadioGroup question1 = (RadioGroup) findViewById(R.id.radioGroup1);
        final RadioGroup question2 = (RadioGroup) findViewById(R.id.radioGroup2);
        final RadioGroup question3 = (RadioGroup) findViewById(R.id.radioGroup3);
        final RadioGroup question4 = (RadioGroup) findViewById(R.id.radioGroup4);
        final Button buttonClear = (Button) findViewById(R.id.buttonClear);
        final Switch question5 = (Switch) findViewById(R.id.switch2);
        final ToggleButton question6 = (ToggleButton) findViewById(R.id.toggleButton10);
        final TextView switchText = (TextView) findViewById(R.id.switchText);
        final Button buttonNextPage = (Button) findViewById(R.id.buttonNextPage);

        //Loading initial data from database if database is empty
        if(databaseProvider.isMasterEmpty()==true){
            databaseProvider.addAnswer(new QuizAnswers("not answered",-1));
            databaseProvider.addAnswer(new QuizAnswers("not answered",-1));
            databaseProvider.addAnswer(new QuizAnswers("not answered",-1));
            databaseProvider.addAnswer(new QuizAnswers("not answered",-1));
            databaseProvider.addAnswer(new QuizAnswers("not answered",-1));
            databaseProvider.addAnswer(new QuizAnswers("not answered",-1));
        }

        //go to next page
        buttonNextPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Updating quiz answers in the database
                for(int i = 0; i< answers.length; i++) {

                    QuizAnswers quizAnswers = databaseProvider.getAnswer(i+1);
                    if(answers[i]!=null) {
                        quizAnswers.setCorrect(answers[i] + ""); //set answer correctness
                        quizAnswers.setRadioButtonId(radioButtonIds[i]); //set ids of selected radiobuttons
                    }
                    else{
                        quizAnswers.setCorrect("No answer");
                    }
                    databaseProvider.updateAnswers(quizAnswers);
                }

                //start results page
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));
            }
        });
        //Clear answers
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                    question1.clearCheck();
                    question2.clearCheck();
                    question3.clearCheck();
                    question4.clearCheck();
                    question5.setChecked(false);
                    question6.setChecked(false);
                    //Updating the answers table
                for(int i = 0; i< answers.length; i++) {
                    answers[i]=null;
                }
            }
        });

        //set elements of the boolean table answers to true or false depending on the clicked option
        question1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId != -1) {
                    RadioButton chosenNumber = (RadioButton) findViewById(checkedId);
                    radioButtonIds[0] = question1.indexOfChild(findViewById(question1.getCheckedRadioButtonId()));

                    if(checkedId == R.id.radioButton1) {
                        answers[0] = true;
                    }
                    else {
                        answers[0] = false;
                    }
                }
            }
        });

        question2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId != -1) {
                    radioButtonIds[1] = question2.indexOfChild(findViewById(question2.getCheckedRadioButtonId()));

                    if(checkedId == R.id.radioButton6)
                        answers[1] = true;
                    else
                        answers[1] = false;
                }
            }
        });

        question3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId != -1) {
                    radioButtonIds[2] = question3.indexOfChild(findViewById(question3.getCheckedRadioButtonId()));
                    if(checkedId == R.id.radioButton10) {
                        answers[2] = true;
                    }
                    else {
                        answers[2] = false;
                    }
                }
            }
        });

        question4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId != -1) {
                    radioButtonIds[3] = question4.indexOfChild(findViewById(question4.getCheckedRadioButtonId()));
                    if(checkedId == R.id.radioButton11)
                        answers[3] = true;
                    else
                        answers[3] = false;
                }
            }
        });


        question5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){
                    answers[4]=true;
                    switchText.setText("yes");
                    radioButtonIds[4]=1;
                }
                else{
                    answers[4]=false;
                    switchText.setText("no");
                    radioButtonIds[4]=0;
                }
            }
        });


        question6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                answers[5] = true;
                for (int i = 0; i < answers.length - 1; i++) {
                    Log.d("toggle","ss");
                    if (answers[i] == false) {
                        answers[5] = false; //if any of questions is not answered correctly, the answer is false
                    }
                }

                if(checked && answers[5]==false) {
                    answers[5]=false;
                }
                else if(checked && answers[5]==true){
                    answers[5]=true;
                }
                else if(checked ==false && answers[5]==true){
                    answers[5]=false;
                }
                else if(checked == false && answers[5]==false){
                    answers[5]=true;
                }

                if(answers[5]==true){
                    radioButtonIds[5]=1;
                }
                else{
                    radioButtonIds[5]=0;
                }

            }
        });

    }


}

