package com.example.carforum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, scoreText, highScoreText;
    private Button trueButton, falseButton;
    private ImageButton backButton;

    private List<Question> questionList;
    private int questionIndex = 0;
    private int score = 0;
    private int highScore = 0;

    private static final String PREFS_NAME = "QuizPrefs";
    private static final String HIGH_SCORE_KEY = "highScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.question_text);
        scoreText = findViewById(R.id.score_text);
        highScoreText = findViewById(R.id.high_score_text);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        backButton = findViewById(R.id.back_button);

        loadHighScore();
        setupQuestions();
        startQuiz();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("A car\'s alternator charges the battery.", true));
        questionList.add(new Question("You should change your car\'s oil every 3,000 miles.", false)); // It depends on the car, but this is a common misconception.
        questionList.add(new Question("A timing belt and a serpentine belt are the same thing.", false));
        questionList.add(new Question("You should rotate your tires every 5,000 to 7,500 miles.", true));
        questionList.add(new Question("A catalytic converter is part of the car\'s exhaust system.", true));
        questionList.add(new Question("A car\'s engine coolant is also known as antifreeze.", true));
        questionList.add(new Question("You don\'t need to replace your car\'s fuel filter.", false));
        questionList.add(new Question("A manual transmission is also known as a stick shift.", true));
        questionList.add(new Question("The \'E\' in a car\'s fuel gauge stands for \'Enough\'.", false)); // It stands for Empty.
        questionList.add(new Question("A car\'s VIN is a unique 17-digit number.", true));
        questionList.add(new Question("The purpose of a car\'s spoiler is purely for decoration.", false)); // It can improve aerodynamics.
        questionList.add(new Question("Gasoline and diesel engines work in the same way.", false)); // Diesel engines use compression ignition.
        questionList.add(new Question("The recommended tire pressure is listed on the tire itself.", false)); // It's usually on a sticker inside the driver's door jamb.
        questionList.add(new Question("Jump-starting a car with a dead battery can be done in any order.", false)); // There is a specific, safe order to connect the cables.
        questionList.add(new Question("All-wheel drive (AWD) and four-wheel drive (4WD) are the same.", false));
    }

    private void startQuiz() {
        Collections.shuffle(questionList);
        questionIndex = 0;
        score = 0;
        showNextQuestion();
        updateScoreText();
    }

    private void showNextQuestion() {
        if (questionIndex >= questionList.size()) {
            Collections.shuffle(questionList);
            questionIndex = 0;
        }
        questionText.setText(questionList.get(questionIndex).getQuestionText());
    }

    private void checkAnswer(boolean userAnswer) {
        if (questionList.get(questionIndex).isAnswerTrue() == userAnswer) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            score = 0;
            Toast.makeText(this, "Wrong! Score reset.", Toast.LENGTH_SHORT).show();
        }
        questionIndex++;
        updateScoreText();
        showNextQuestion();
    }

    private void updateScoreText() {
        scoreText.setText("Score: " + score);
        if (score > highScore) {
            highScore = score;
            saveHighScore();
            updateHighScoreText();
        }
    }

    private void updateHighScoreText() {
        highScoreText.setText("High Score: " + highScore);
    }

    private void saveHighScore() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HIGH_SCORE_KEY, highScore);
        editor.apply();
    }

    private void loadHighScore() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        highScore = prefs.getInt(HIGH_SCORE_KEY, 0);
        updateHighScoreText();
    }

    private class Question {
        private String questionText;
        private boolean answerTrue;

        public Question(String questionText, boolean answerTrue) {
            this.questionText = questionText;
            this.answerTrue = answerTrue;
        }

        public String getQuestionText() {
            return questionText;
        }

        public boolean isAnswerTrue() {
            return answerTrue;
        }
    }
}
