package com.example.lifelogger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ShowResultActivity extends AppCompatActivity {

    SQLiteDatabase db;
    MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "log001.db", null, 1);
    ListView lvProducts;

    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        lvProducts = (ListView)findViewById(R.id.lv_name_age);

        Cursor cursor = fetchAll();
        /*

        String [] columns = new String [] {
                "title", "contents", "latitude",
                "longitude", "time"
        };

        int [] to = new int[] {
                R.id.editTitle, R.id.editContents
        };

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.activity_show_result,
                cursor,
                columns,
                to,
                0);

        lvProducts.setAdapter(dataAdapter);
        */
    }

    public void goMaps (View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    // select
    public void select() {

        db = helper.getReadableDatabase();
        Cursor c = db.query("log", new String[] {"title", "contents", "latitude", "longitude", "time"}, null, null, null, null, null);

        ListView list = (ListView) findViewById(R.id.lv_name_age);

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            long time = c.getLong(c.getColumnIndex("time"));
            String title = c.getString(c.getColumnIndex("title"));
            String contents = c.getString(c.getColumnIndex("contents"));

        }
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

    public Cursor fetchAll() {

        db = helper.getReadableDatabase();
        Cursor mCursor = db.query("log", null,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
