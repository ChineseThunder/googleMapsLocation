package com.example.sunmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    private LocationManager manager;  // will start7stop the listiner

    private LocationListener listener; // will handle new location events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        extracted();
    }

    private void extracted() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = location -> {
            Log.i("myLocation", "new location" + location);
            moveCamera(location.getLatitude(), location.getLongitude());
        };
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, listener);
        }
    }

    private void moveCamera(double lat, double lng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 20));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] res) {
        super.onRequestPermissionsResult(requestCode, permissions, res);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, listener);
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMapLongClickListener(latLng -> {
            googleMap.addMarker(new MarkerOptions().position(latLng).title("new marker"));
            moveCamera(latLng.latitude, latLng.longitude);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}

