package com.example.android.tape1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Button getLocationBtn,openMaps;
    TextView tv1,tv2,tv3,tv4;
    public String s,t;
    int cnt=1;
    LocationManager locationManager,locMan1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        getLocationBtn = (Button)findViewById(R.id.button1);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        openMaps = (Button)findViewById(R.id.button2);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        /*openMaps.setOnClickListener(new View.OnClickListener(){

            @Override
            abstract public void onClick1(View v1) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setPackage("com.google.android.apps.maps");
                startActivity(i);
            }
        });*/

    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //tv1.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        //if(cnt==1) {
            s = Double.toString(location.getLatitude());
            t = Double.toString(location.getLongitude());
            cnt = 2;

            tv1.setText(Double.toString(location.getLatitude()));
        tv2.setText(Double.toString(location.getLongitude()));
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            tv3.setText("Your current address is" +"\n"+addresses.get(0).getAddressLine(0));
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    void getPark() {
        try {
            locMan1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locMan1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            //tv3.setText(Double.toString(location.getLatitude()));
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onLocationChanged(Location location) {
        //tv1.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        tv1.setText(Double.toString(location.getLatitude()));
        tv2.setText(Double.toString(location.getLongitude()));
        try {
            //Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            //List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
            //addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }*/
    public void Maps(View view){
        String url = "https://www.google.com/maps/dir/?api=1&destination=" + s + "," + t + "&travelmode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}