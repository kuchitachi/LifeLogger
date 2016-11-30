package com.example.lifelogger;

import android.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class RecordActivity extends AppCompatActivity implements LocationListener {

    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    long timeWhenStopped = 0; // chronometer에 기록된 시간 저장
    boolean statOfStartStop = false; // start나 stop버튼이 연속적으로 눌려졌을때 에러막기위한 변수

    double latitude;
    double longitude;

    private static final int go =3;

    public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        Log.i("Message: ","Location changed, " + location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude());
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {}
    public void onProviderEnabled(String provider) {}
    public void onProviderDisabled(String provider) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        helper = new MySQLiteOpenHelper(RecordActivity.this, "logs.db", null, 1); // DB 생성

        Button buttonStart = (Button)findViewById(R.id.buttonStart);
        Button buttonStop = (Button)findViewById(R.id.buttonStop);

        Button buttonSave = (Button)findViewById(R.id.buttonSave);

        final Chronometer chron = (Chronometer) findViewById(R.id.chron);


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        go);
                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다

            }
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this); // real time으로 받아오는거?
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

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

        buttonSave.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                EditText etTitle = (EditText)findViewById(R.id.editTitle);
                EditText etContents = (EditText)findViewById(R.id.editContents);

                String title = etTitle.getText().toString();
                String contents = etContents.getText().toString();

                long elapsedMillis = SystemClock.elapsedRealtime() - chron.getBase();
                insert(latitude, longitude, elapsedMillis-1000, title, contents);
                call();
            }
        });
    }

    // 위치, 한 시간, 제목, 내용
    public void insert(double lati, double longi, long time, String title, String contents) {

        db = helper.getWritableDatabase(); // DB 객체를 얻어온다. 쓰기 가능.

        ContentValues values = new ContentValues();
        // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
        // 데이터 삽입은 put을 사용해서 함
        values.put("latitude", lati);
        values.put("longitude", longi);
        values.put("time", time);
        values.put("title", title);
        values.put("contents", contents);

        db.insert("log", null, values); // 테이블/널컬럼핵/데이터
    }

    public void call() {

        db = helper.getReadableDatabase();
        Cursor c = db.query("log", null, null, null, null, null, null);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            long time = c.getLong(c.getColumnIndex("time"));
            String title = c.getString(c.getColumnIndex("title"));
            String contents = c.getString(c.getColumnIndex("contents"));
            Log.i("db", "id: "+ _id + ", title: " + title + ", latitude: " + latitude +", longitude: " + longitude + "\n" +
            "time: " + time + ", contents: " + contents);
        }
    }
}
