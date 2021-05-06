package edu.cnm.deepdive.mathquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

  Button button_start, button_answer1, button_answer2, button_answer3, button_answer4;
  TextView textview_timer, textview_score, textview_questions, textview_bottommessage;
  ProgressBar progress_timer;

  Game g = new Game();

  int secondsRemaining = 30;

  CountDownTimer timer = new CountDownTimer(30_000, 1_000) {
    @Override
    public void onTick(long millisUntilFinished) {
      secondsRemaining--;
      textview_timer.setText(Integer.toString(secondsRemaining) + "sec.");
      progress_timer.setProgress(30 - secondsRemaining);

    }

    @Override
    public void onFinish() {
      button_answer1.setEnabled(false);
      button_answer2.setEnabled(false);
      button_answer3.setEnabled(false);
      button_answer4.setEnabled(false);
      textview_bottommessage
          .setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));

      final Handler handler = new Handler();

      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          button_start.setVisibility(View.VISIBLE);
        }

      }, 4_000);


    }
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    button_start = findViewById(R.id.button_start);
    button_answer1 = findViewById(R.id.button_answer1);
    button_answer2 = findViewById(R.id.button_answer2);
    button_answer3 = findViewById(R.id.button_answer3);
    button_answer4 = findViewById(R.id.button_answer4);

    progress_timer = findViewById(R.id.progress_timer);

    textview_timer = findViewById(R.id.textview_timer);
    textview_score = findViewById(R.id.textview_score);
    textview_questions = findViewById(R.id.textiew_questions);
    textview_bottommessage = findViewById(R.id.textview_bottommessage);

    textview_timer.setText("0 seconds");
    textview_score.setText("0 points");
    textview_questions.setText("");
    textview_bottommessage.setText("Press START");

    View.OnClickListener startButtonClickListener = new OnClickListener() {
      @Override
      public void onClick(View v) {
        Button start_button = (Button) v;

        start_button.setVisibility(View.INVISIBLE);
        secondsRemaining = 30;
        g = new Game();
        nextTurn();
        timer.start();

      }
    };

    View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Button buttonClicked = (Button) v;

        int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
        //Toast.makeText(MainActivity.this, "AnswerSelected = " + answerSelected, Toast.LENGTH_SHORT).show();
        //toast to check if the selected button works.

        g.checkAnswer(answerSelected);
        textview_score.setText(Integer.toString(g.getScore()));
        nextTurn();
      }
    };

    button_start.setOnClickListener(startButtonClickListener);

    button_answer1.setOnClickListener(answerButtonClickListener);
    button_answer2.setOnClickListener(answerButtonClickListener);
    button_answer3.setOnClickListener(answerButtonClickListener);
    button_answer4.setOnClickListener(answerButtonClickListener);


  }

  private void nextTurn() {
    g.makeNewQuestion();
    int[] answer = g.getCurrentQuestion().getAnswerArray();

    button_answer1.setText(Integer.toString(answer[0]));
    button_answer2.setText(Integer.toString(answer[1]));
    button_answer3.setText(Integer.toString(answer[2]));
    button_answer4.setText(Integer.toString(answer[3]));

    button_answer1.setEnabled(true);
    button_answer2.setEnabled(true);
    button_answer3.setEnabled(true);
    button_answer4.setEnabled(true);

    textview_questions.setText(g.getCurrentQuestion().getQuestionPhrase());

    textview_bottommessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));

  }
}