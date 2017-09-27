package com.nextdots.retargetly.receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.nextdots.retargetly.Retargetly;
import com.nextdots.retargetly.api.ApiConstanst;
import com.nextdots.retargetly.api.ApiController;
import com.nextdots.retargetly.data.models.Event;

import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static com.nextdots.retargetly.api.ApiConstanst.TAG;

public class GPSBroadCastReceiver extends BroadcastReceiver implements LocationListener {

    Location location;

    boolean hasSend = false;

    Context context;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d(TAG, "GPS ENABLE");
            sendBroadcastActive();
            try {
                LocationManager locationManager = (LocationManager) context
                        .getSystemService(LOCATION_SERVICE);

                if (location == null) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
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
            sendBroadcastDisable();
            location = null;
            hasSend = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,"GPS onLocationChanged");
        if((location.getLatitude()!=0 && location.getLongitude()!=0) && !hasSend) {
            hasSend = true;
            Log.d(TAG, "Latitude: " + location.getLatitude());
            Log.d(TAG, "Longitude: " + location.getLongitude());
            callEventCoordinate(location.getLatitude()+"",location.getLongitude()+"");
        }
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

    public void callEventCoordinate(String latitude, String longitude){
        ApiController apiController  = new ApiController();

        String manufacturer   = Build.MANUFACTURER;
        String model          = Build.MODEL;
        String idiome         = Locale.getDefault().getLanguage();

        apiController.callCustomEvent(new Event(ApiConstanst.EVENT_CUSTOM, latitude, longitude , Retargetly.uid, Retargetly.application.getPackageName(), Retargetly.pid, manufacturer, model, idiome));
    }

    public void sendBroadcastActive(){
        Intent i = new Intent(ApiConstanst.GPS_ENABLE);
        this.context.sendBroadcast(i);
    }

    public void sendBroadcastDisable(){
        Intent i = new Intent(ApiConstanst.GPS_DISABLE);
        this.context.sendBroadcast(i);
    }
}