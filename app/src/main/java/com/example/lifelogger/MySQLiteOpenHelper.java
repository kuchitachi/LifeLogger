package com.example.lifelogger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eon on 2016. 11. 30..
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {


    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENTS = "contents";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_TIME = "time";


    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQLiteOpenHelper가 최초 실행되었을 때

        String sql = "Create table log (" +
                "_id integer primary key autoincrement, " +
                "current text, " +
                "time integer, " +
                "latitude long, " +
                "longitude long, " +
                "title text, " +
                "contents text, " +
                "address text); ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "drop table if exists log";
        db.execSQL(sql);

        onCreate(db);
    }

}
