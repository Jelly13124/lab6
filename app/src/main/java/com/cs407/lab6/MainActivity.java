package com.cs407.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity {
    private final LatLng mDestinationLatlng = new LatLng(43.075287, -89.404271);
    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.addMarker(new MarkerOptions().position(mDestinationLatlng).title("Destination"));
            displayMyLocation();
        });
    }
    private void displayMyLocation () {
        // Check if permission granted
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission==PackageManager.PERMISSION_DENIED) {
            System.out.println("1111111111");
            ActivityCompat. requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            fusedLocationClient.getLastLocation().addOnCompleteListener(this, task -> {
                Location mLastKnowLocation = task.getResult();
                System.out.println("1111111111");
                if(task.isSuccessful() && mLastKnowLocation != null){
                    mMap.addPolyline(new PolylineOptions().add(new LatLng(mLastKnowLocation.getLatitude(),
                            mLastKnowLocation.getLongitude()),mDestinationLatlng));
                }

            });
        }
    }

}

