package com.nextdots.retargetly.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import static com.nextdots.retargetly.api.ApiConstanst.TAG;

public class GPSBroadCastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent )
    {
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            Log.d(TAG,"GPS ENABLE");
        }
        else
        {
            Log.d(TAG,"GPS DISABLE");
        }
    }
}