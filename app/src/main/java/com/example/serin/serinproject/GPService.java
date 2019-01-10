package com.example.serin.serinproject;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class GPService extends Service {
    private LocationManager lm = null;
    private static final int LOCATION_INTERVAL = 10000;


    // Création du Listener
    private class LocationListener implements android.location.LocationListener {
        Location lastLoc;
        double lati;
        double longi;

        public LocationListener(String provider) {
            lastLoc = new Location(provider);

        }

        @Override
        public void onLocationChanged(Location location) {
            lastLoc.set(location);
            lati = lastLoc.getLatitude();
            longi = lastLoc.getLongitude();
            String toLog = ""+lati+";"+longi+"\n";
            Log.i("2SU","Ecriture:"+toLog);

            try {
                FileOutputStream fos = openFileOutput("GPSPos", Context.MODE_PRIVATE);
                fos.write(toLog.getBytes());
                fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    LocationListener locList = new LocationListener(LocationManager.GPS_PROVIDER);


    //Méthodes du Service
    public void onCreate() {
        initializeLocationManager();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, 10f, locList);

    }
    public void onDestroy()
    {

    }

    public int onStartCommand(Intent intent,int flags,int startId)
    {

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initializeLocationManager() {
        if (lm == null)
        {
            lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
