package com.example.demogeocoder;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {
    LocationManager lm;
    EditText et1,et2,et3;
    Button b1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.e1);
        et2=findViewById(R.id.e2);
        et3=findViewById(R.id.e3);
        b1=findViewById(R.id.button);
        b1.setOnClickListener(this);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},123);
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, this);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat,lng;
        lat=location.getLatitude();
        lng=location.getLongitude();
        Test.lat=lat;
        Test.lng=lng;
        et1.setText(""+lat);
        et2.setText(""+lng);

        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList=geocoder.getFromLocation(lat,lng,10);
            Address adr=addressList.get(0);
            StringBuffer sb=new StringBuffer();
            sb.append(adr.getAddressLine(0));
            sb.append(";"+adr.getLocality());
            sb.append(";"+adr.getCountryName());
            sb.append(";"+adr.getPostalCode());
            et3.setText(""+sb);
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        Intent it=new Intent(this,MapsActivity.class);
        startActivity(it);
    }
}