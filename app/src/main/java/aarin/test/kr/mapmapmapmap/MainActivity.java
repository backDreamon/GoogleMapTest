package aarin.test.kr.mapmapmapmap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    double lat;
    double lng;

    Button mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapBtn = (Button) findViewById(R.id.mapButton);
        // GPS 위치 정보 받아오기
        startLocationService();

        Log.d(TAG, "LAT : " + lat + ", LNG : " + lng);

        // GoogleMap Fragment intent 출력
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });
    }

    private void startLocationService() {

        // get manager instance
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // set listener
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);
        Log.d(TAG, "Changed");

        Toast.makeText(getApplicationContext(), "Location Service started.\nyou can test using DDMS.", Toast.LENGTH_SHORT).show();
    }


    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            lat = location.getLatitude();
            lng = location.getLongitude();

            String msg = "Latitude : " + lat + "\nLongitude:" + lng;
            Log.i("GPSLocationService", msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }


}
