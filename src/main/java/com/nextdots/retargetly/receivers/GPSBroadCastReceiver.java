package com.nextdots.retargetly.receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;
import static com.nextdots.retargetly.api.ApiConstanst.TAG;

public class GPSBroadCastReceiver extends BroadcastReceiver implements LocationListener {

    Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(TAG, "GPS ENABLE");
            try {
                LocationManager locationManager = (LocationManager) context
                        .getSystemService(LOCATION_SERVICE);

                if (location == null) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                Log.d(TAG, location.getLatitude()+"");
                                Log.d(TAG, location.getLongitude()+"");
                            }
                        }
                    }
                }
            }catch (Exception e){

            }
        }
        else
        {
            Log.d(TAG,"GPS DISABLE");
            location = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,"GPS onLocationChanged");
        Log.d(TAG, location.getLatitude()+" Change");
        Log.d(TAG, location.getLongitude()+" Change");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG,"GPS onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(TAG,"GPS onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(TAG,"GPS onProviderDisabled");
    }
}