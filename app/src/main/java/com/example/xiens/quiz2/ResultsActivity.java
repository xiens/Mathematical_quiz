package com.example.xiens.quiz2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    /**
     * Author: Zbigniew Pamula
     * version 1.0
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        final Intent goBack = new Intent(this, MainActivity.class);
        final Button buttonBack = (Button) findViewById(R.id.buttonBack);
        final TextView results = (TextView) findViewById(R.id.textViewResults);

        final String message2;
        DatabaseProvider databaseProvider = new DatabaseProvider(this);
        //Putting the quiz answers into list
        List<QuizAnswers> quizAnswersList2 = databaseProvider.getAllAnswers();
        results.append("\n");
        //Appending the answers to the textView
        for(QuizAnswers quizAnswers: quizAnswersList2){
            results.append("Answer to question "+quizAnswers.getId()+" "+quizAnswers.getCorrect()+"\n"+" radio button selected: "+quizAnswers.getRadioButtonId()+"\n");
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
