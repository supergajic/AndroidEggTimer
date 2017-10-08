package com.milangajic.dropmedia.androideggtimer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity
{

    private Button btnController;
    private TextView tvShowTime;
    private SeekBar timerSeekbar;
    private CountDownTimer countDownTimer;
    private boolean isTimerActive = false;


    private void ResetValues()
    {
        isTimerActive = false;
        tvShowTime.setText("0:30");
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);
        timerSeekbar.setEnabled(true);
        btnController.setText("Starta");
    }

    private void UpdateTimer(int secondsLeft)
    {
        int minutes = (int)secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String strSeconds = "";

        if(seconds < 10)
            strSeconds = "0" + String.valueOf(seconds);
        else
            strSeconds = String.valueOf(seconds);

        tvShowTime.setText(Integer.toString(minutes) + ":" + strSeconds);
    }

    public void TimerControl(View view)
    {
        if(!isTimerActive)
        {
            btnController.setText("Stoppa");
            timerSeekbar.setEnabled(false);
            isTimerActive = true;
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long secondsLeft) {
                    UpdateTimer((int) secondsLeft / 1000);
                }

                @Override
                public void onFinish() {

                    tvShowTime.setText("0:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();

                    ResetValues();
                }
            }.start();
        }
        else {
            countDownTimer.cancel();
            ResetValues();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnController = (Button)findViewById(R.id.btnController);
        tvShowTime = (TextView)findViewById(R.id.tvTime);
        timerSeekbar = (SeekBar)findViewById(R.id.timerSeekBar);

        //timerSeekbar.setMax(600);
        //timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                UpdateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ResetValues();
    }
}
