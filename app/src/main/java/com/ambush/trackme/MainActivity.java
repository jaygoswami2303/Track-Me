package com.ambush.trackme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button trackButton;
    Button mapButton;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if(ActivityCompat.checkSelfPermission(this,mPermission)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        trackButton = (Button) findViewById(R.id.tButton);
        mapButton = (Button) findViewById(R.id.mButton);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(MainActivity.this);

                if(gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(),"Your location is - \nLat: " + latitude + "\nLong: " + longitude,Toast.LENGTH_LONG).show();
                    mapButton.setVisibility(View.VISIBLE);
                } else {
                    gps.showSettingsAlert();
                }
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("latitude",gps.getLatitude());
                intent.putExtra("longitude",gps.getLongitude());
                intent.putExtra("time",gps.getTime());
                startActivity(intent);
            }
        });
    }
}
