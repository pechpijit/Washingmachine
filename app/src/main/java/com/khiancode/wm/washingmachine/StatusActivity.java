package com.khiancode.wm.washingmachine;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.khiancode.wm.washingmachine.helper.BaseActivity;
import com.khiancode.wm.washingmachine.helper.PrefUtils;
import com.khiancode.wm.washingmachine.helper.TimeReceiver;

import java.util.Arrays;

public class StatusActivity extends BaseActivity {

    TextView statusMode, statusFunction, statusTime,txtStatus;
    Button btnStaPa,btnStop;
    private PrefUtils prefUtils;
    TextView txtTimeMin,txtTimeSec;
    private CountDownTimer countDownTimer;
    private int timeToStart;
    private TimerState timerState;
    private static int MAX_TIME = 0 ;

    private enum TimerState {
        STOPPED,
        RUNNING,
        PAUSE
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, null);
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        statusMode = findViewById(R.id.status_mode);
        statusFunction = findViewById(R.id.status_function);
        statusTime = findViewById(R.id.status_time);
        btnStaPa = findViewById(R.id.btn_start_pause);
        btnStop = findViewById(R.id.btn_stop);
        txtTimeMin = findViewById(R.id.txtTimeMin);
        txtTimeSec = findViewById(R.id.txtTimeSec);
        txtStatus = findViewById(R.id.txtStatus);

        final Bundle extras = getIntent().getExtras();
        String[] value = extras.getStringArray("value");

        prefUtils = new PrefUtils(getApplicationContext());

        assert value != null;

        if (value[0].substring(0, 4).equals("Auto")) {
            prefUtils.setTime("60");
        } else {
            String[] time = value[2].split(" ");
            prefUtils.setTime(time[1]);
        }

        MAX_TIME = Integer.parseInt(prefUtils.getTime()) * 60;


        prefUtils.setMode(value[0]);
        prefUtils.setFunction(value[1]);
        prefUtils.setMaxTime(Integer.parseInt(prefUtils.getTime()) * 60);

        statusMode.setText(prefUtils.getMode());
        statusFunction.setText(prefUtils.getFunction());
        statusTime.setText(prefUtils.getTime());

        showProgressDialog(VERIFY);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (timerState == TimerState.STOPPED) {
                            prefUtils.setStartedTime((int) getNow());
                            startTimer();
                            timerState = TimerState.RUNNING;
                            prefUtils.setStatus(true);
                            btnStaPa.setText(getString(R.string.button_pause));
                            btnStaPa.setBackgroundColor(getResources().getColor(R.color.orange));
                        }
                        hideProgressDialog();
                    }
                }, 3000);

        btnStaPa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerState == TimerState.RUNNING) {
                    timerState = TimerState.PAUSE;
                    btnStaPa.setText("run");
                    btnStaPa.setBackgroundColor(getResources().getColor(R.color.green));
                    prefUtils.setStartedTime(0);
                    prefUtils.setMaxTime(timeToStart);
                    MAX_TIME = timeToStart;
                    countDownTimer.cancel();
                    removeAlarmManager();
                    updatingUI();
                } else if (timerState == TimerState.PAUSE){
                    prefUtils.setStartedTime((int) getNow());
                    startTimer();
                    timerState = TimerState.RUNNING;
                    prefUtils.setStatus(true);
                    btnStaPa.setText(getString(R.string.button_pause));
                    btnStaPa.setBackgroundColor(getResources().getColor(R.color.orange));
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                timerState = TimerState.STOPPED;
                prefUtils.setStatus(false);
                prefUtils.setStartedTime(0);
                timeToStart = 0;
                onTimerFinish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            setResult(RESULT_OK, null);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public  void onResume() {
        super.onResume();
        //initializing a countdown timer
        if (timerState != TimerState.PAUSE) {
            initTimer();
            updatingUI();
            removeAlarmManager();
        }
    }

    @Override
    public  void onPause() {
        super.onPause();
        if (timerState == TimerState.RUNNING) {
            countDownTimer.cancel();
            setAlarmManager();
        }
    }

    private long getNow() {
        Calendar rightNow = Calendar.getInstance();
        return rightNow.getTimeInMillis() / 1000;
    }

    private void initTimer() {
        long startTime = prefUtils.getStartedTime();
        if (startTime > 0) {
            timeToStart = (int) (MAX_TIME - (getNow() - startTime));
            if (timeToStart <= 0) {
                // TIMER EXPIRED
                timeToStart = MAX_TIME;
                timerState = TimerState.STOPPED;
                prefUtils.setStatus(false);
                onTimerFinish();
            } else {
                startTimer();
                timerState = TimerState.RUNNING;
                prefUtils.setStatus(true);
            }
        } else {
            timeToStart = MAX_TIME;
            timerState = TimerState.STOPPED;
            prefUtils.setStatus(false);
        }
    }

    private void onTimerFinish() {
        Toast.makeText(this, "Countdown timer finished!", Toast.LENGTH_SHORT).show();
        prefUtils.setStartedTime(0);
        btnStop.setEnabled(false);
        btnStop.setBackgroundColor(Color.parseColor("#D5D6D6"));
        btnStop.setTextColor(Color.parseColor("#A4A5A5"));

        btnStaPa.setEnabled(false);
        btnStaPa.setBackgroundColor(Color.parseColor("#D5D6D6"));
        btnStaPa.setTextColor(Color.parseColor("#A4A5A5"));
        updatingUI();
    }

    private void updatingUI() {
        if (timerState == TimerState.RUNNING) {
            btnStaPa.setText(getString(R.string.button_pause));
            btnStaPa.setBackgroundColor(getResources().getColor(R.color.orange));
            txtStatus.setText(getString(R.string.status_run));
            txtStatus.setTextColor(getResources().getColor(R.color.green));
        } else if (timerState == TimerState.PAUSE){
            txtStatus.setText(getString(R.string.status_pause));
            txtStatus.setTextColor(getResources().getColor(R.color.orange));
        } else if (timerState == TimerState.STOPPED) {
            txtStatus.setText(getString(R.string.status_stop));
            txtStatus.setTextColor(getResources().getColor(R.color.red));
        }

        int min = timeToStart / 60;
        int sec = timeToStart % 60;

        if (min < 10) {
            txtTimeMin.setText(String.valueOf(0) + String.valueOf(min));
        } else {
            txtTimeMin.setText(String.valueOf(min));
        }

        if (sec < 10) {
            txtTimeSec.setText(String.valueOf(0) + String.valueOf(sec));
        } else {
            txtTimeSec.setText(String.valueOf(sec));
        }

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeToStart * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeToStart -= 1;
                updatingUI();
            }

            @Override
            public void onFinish() {
                timeToStart -= 1;
                timerState = TimerState.STOPPED;
                prefUtils.setStatus(false);
                onTimerFinish();
                updatingUI();
            }
        }.start();
    }

    public void setAlarmManager() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            am.setAlarmClock(new AlarmManager.AlarmClockInfo(System.currentTimeMillis()+(timeToStart*1000), sender), sender);
        } else {
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(timeToStart*1000), sender);
        }
    }

    public void removeAlarmManager() {
        Intent intent = new Intent(this, TimeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
