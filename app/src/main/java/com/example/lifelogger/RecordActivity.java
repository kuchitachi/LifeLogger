package com.example.lifelogger;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class RecordActivity extends AppCompatActivity {

    long timeWhenStopped = 0; // chronometer에 기록된 시간 저장
    boolean statOfStartStop = false; // start나 stop버튼이 연속적으로 눌려졌을때 에러막기위한 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Button buttonStart = (Button)findViewById(R.id.buttonStart);
        Button buttonStop = (Button)findViewById(R.id.buttonStop);

        Button buttonSave = (Button)findViewById(R.id.buttonSave);

        final Chronometer chron = (Chronometer) findViewById(R.id.chron);

        buttonStart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(statOfStartStop==false) {
                    chron.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);

                    chron.start();

                    statOfStartStop=true;
                }
            }
        });

        buttonStop.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(statOfStartStop==true) {
                    timeWhenStopped = chron.getBase() - SystemClock.elapsedRealtime();

                    chron.stop();

                    statOfStartStop=false;
                }
            }
        });
    }

}
