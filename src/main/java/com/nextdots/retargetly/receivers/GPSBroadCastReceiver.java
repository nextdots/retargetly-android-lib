package com.nextdots.retargetly.receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static com.nextdots.retargetly.api.ApiConstanst.TAG;

public class GPSBroadCastReceiver extends BroadcastReceiver implements LocationListener {

    private Criteria criteria;
    private String provider;

    @Override
    public void onReceive(Context context, Intent intent) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Log.d(TAG, "GPS ENABLE");
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            provider = manager.getBestProvider(criteria, true);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1,
                        0, this);
                setMostRecentLocation(manager.getLastKnownLocation(provider));
            }
        }
        else
        {
            Log.d(TAG,"GPS DISABLE");
        }
    }

    private void setMostRecentLocation(Location lastKnownLocation) {
        Log.d(TAG, "latitude "+lastKnownLocation.getLatitude()+"");
        Log.d(TAG, "longitude "+lastKnownLocation.getLongitude()+"");
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}