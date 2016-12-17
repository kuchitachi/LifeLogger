package com.example.lifelogger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    SQLiteDatabase db;
    MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "log000003.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        call();
    }

    public void call() {

        db = helper.getReadableDatabase();
        Cursor c = db.query("log", null, null, null, null, null, null);

        LatLng location = null;

        PolylineOptions options = new PolylineOptions().width(13).color(Color.MAGENTA).geodesic(true);

        while (c.moveToNext()) {
            String _id = c.getString(c.getColumnIndex("title"));

            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            location = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location).title(_id));

            options.add(location);

        }

        mMap.addPolyline(options);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(10).build();
        //Zoom in and animate the camera.
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
