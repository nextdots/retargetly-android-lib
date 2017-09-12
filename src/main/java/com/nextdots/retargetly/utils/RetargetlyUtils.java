package com.nextdots.retargetly.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.nextdots.retargetly.R;
import com.nextdots.retargetly.Retargetly;
import com.nextdots.retargetly.api.ApiConstanst;
import com.nextdots.retargetly.api.ApiController;
import com.nextdots.retargetly.data.listeners.CustomEventListener;
import com.nextdots.retargetly.data.models.Event;
import java.util.List;
import java.util.Locale;

public class RetargetlyUtils {

    public static void callCustomEvent(String value){
        callEvent(value,null);
    }

    public static void callCustomEvent(String value, CustomEventListener customEventListener){
        callEvent(value,customEventListener);
    }

    private static void callEvent(String value, CustomEventListener customEventListener){
        ApiController apiController  = new ApiController();

        String manufacturer   = Build.MANUFACTURER;
        String model          = Build.MODEL;
        String idiome         = Locale.getDefault().getLanguage();

        apiController.callCustomEvent(new Event(ApiConstanst.EVENT_CUSTOM, value , Retargetly.uid, Retargetly.application.getPackageName(), Retargetly.pid, manufacturer, model, idiome),customEventListener);
    }

    public static String getInstalledApps(Application application) {
        String result = "";
        List<PackageInfo> packs = application.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((isSystemPackage(p) == false)) {
                result += p.applicationInfo.loadLabel(application.getPackageManager()).toString() + ", ";
            }
        }
        return result;
    }

    private static boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }


    public static boolean hasLocationEnabled(Activity activity){
        LocationManager locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return true;
        }else{
            return false;
        }
    }

    public static void openDialogSettings(final Activity activity){
        new AlertDialog.Builder(activity)
                .setMessage(activity.getResources().getString(R.string.alert_location))
                .setCancelable(false)
                .setPositiveButton(activity.getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(callGPSSettingIntent);
                    }
                }).show();
    }


    public static void checkPermissionGps(Activity activity){
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (!hasLocationEnabled(activity)) {
                openDialogSettings(activity);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
        }
    }
}
