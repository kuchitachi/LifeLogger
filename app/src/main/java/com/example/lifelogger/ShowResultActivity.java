package com.example.lifelogger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowResultActivity extends AppCompatActivity {

    SQLiteDatabase db;

    MySQLiteOpenHelper mHelper;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        list = (ListView) findViewById(R.id.lv_result);

        mHelper = new MySQLiteOpenHelper(this, "log000003.db", null, 1);

        Cursor cursor;

        db = mHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM log", null);

        startManagingCursor(cursor);

        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor,
                new String[] { "title", "contents" }, new int[] { android.R.id.text1, android.R.id.text2 });

        ListViewExampleClickListener listViewExampleClickListener = new ListViewExampleClickListener();

        list.setOnItemClickListener(listViewExampleClickListener);

        list.setAdapter(adapter);

    }

    private class ListViewExampleClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(
                    getApplicationContext(),
                    "Not implemented yet...",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void goMaps (View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
