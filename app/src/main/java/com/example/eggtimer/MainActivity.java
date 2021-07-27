package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int seconds = 720;
    SeekBar eggTimerSeekbar;
    TextView timerTextView;
    CountDownTimer countDownTimer;
    Boolean go = true;
    Button goButton;

    public void resetTimer() {
        timerTextView.setText("00:30");
        eggTimerSeekbar.setProgress(30);
        eggTimerSeekbar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Go!");
        go = true;
    }

    public void clickButton(View view) {
        if(go) {
            eggTimerSeekbar.setEnabled(false);
            goButton.setText("Stop!");

            countDownTimer = new CountDownTimer(eggTimerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();

            go = false;
        } else {
            resetTimer();
        }
    }
    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int startingPosition = 30;

        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.goButton);
        eggTimerSeekbar = findViewById(R.id.eggTimerSeekBar);

        eggTimerSeekbar.setMax(seconds);
        eggTimerSeekbar.setProgress(startingPosition);

        eggTimerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Info", "SeekBar value: " + progress);
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}