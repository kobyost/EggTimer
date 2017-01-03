package com.kobyost.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timer;
     Button button;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (TextView) findViewById(R.id.timer);
         button = (Button) findViewById(R.id.button);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().equals("Stop"))
                    countDown(false);
                else {
                    countDown(true);
                    seekBar.setEnabled(false);
                    button.setText("Stop");
                }
            }
        });

    }

    public void countDown(boolean state) {
        if(!state) {
            countDownTimer.cancel();
            seekBar.setProgress(30);
            timer.setText("0:30");
            seekBar.setEnabled(true);
            button.setText("Start");

        }
        else {
            final int progress = seekBar.getProgress();
            Log.d("progress:", progress + "");
            int millis = progress * 1000 + 100;
            Log.d("millis", millis + "");

            countDownTimer = new CountDownTimer(millis, 1000) {

                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished/1000));
                }

                public void onFinish() {
                    timer.setText("0:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rooster_crow);
                    mediaPlayer.start();
                    seekBar.setEnabled(true);
                    button.setText("Start");
                    Log.d("timer:", "done!");
                }
            }.start();
        }
    }

    public void updateTimer(int progress)
    {
        int minutes = (int) progress / 60;
        int seconds = progress - (minutes * 60);
        String secondsString = String.valueOf(seconds);
        if (seconds <= 9)
            secondsString = "0" + secondsString;
        timer.setText(Integer.toString(minutes) + ":" + secondsString);
    }
}
