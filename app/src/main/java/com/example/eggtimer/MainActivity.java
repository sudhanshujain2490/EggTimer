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

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondLeft)
    {
        int minutes = (int) secondLeft / 60 ;
        int seconds = secondLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds <= 9)
        {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) +  ":" + secondString);
    }

    public void controlTimer(View view)
    {
        if (counterIsActive == false)
        {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");

            //Log.i("Button Pressed", "Pressed");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                }

                @Override
                public void onFinish() {

                    resetTimer();
                    timerTextView.setText("0:00");
                    /*Log.i("Finished", "Timer Done!");*/
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        }
        else
        {
            resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                int minutes = (int) i / 60 ;
                int seconds = i - minutes * 60;

                String secondString = Integer.toString(seconds);

                if (secondString.equals("0"))
                {
                    secondString = "00";
                }

                timerTextView.setText(Integer.toString(minutes) +  ":" + secondString);
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