package com.nextdots.retargetly;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import com.nextdots.retargetly.api.ApiConstanst;
import com.nextdots.retargetly.api.ApiController;
import com.nextdots.retargetly.data.models.Event;
import com.nextdots.retargetly.utils.DialogGpsUtils;
import com.nextdots.retargetly.utils.RetargetlyUtils;

import java.util.Locale;

import static com.nextdots.retargetly.api.ApiConstanst.TAG;


public class Retargetly implements Application.ActivityLifecycleCallbacks {

    static public Application application = null;

    private boolean isFirst = false;

    static public String pid;
    static public String uid;

    private String manufacturer;
    private String model;
    private String idiome;

    private boolean forceGPS = false;

    private Activity currentActivity;

    private ApiController apiController;

    public static void init(Application application, String uid, String pid){
        new Retargetly(application,uid,pid);
    }

    public static void init(Application application, String uid, String pid, boolean forceGPS){
        new Retargetly(application,uid,pid,forceGPS);
    }

    private Retargetly(Application application, String uid, String pid){
        this.application = application;
        this.manufacturer   = Build.MANUFACTURER;
        this.model          = Build.MODEL;
        this.idiome         = Locale.getDefault().getLanguage();
        this.uid       = uid;
        this.pid       = pid;
        this.application.registerActivityLifecycleCallbacks(this);
        apiController  = new ApiController();
    }

    private Retargetly(Application application, String uid, String pid, boolean forceGPS){
        this.application = application;
        this.manufacturer   = Build.MANUFACTURER;
        this.model          = Build.MODEL;
        this.idiome         = Locale.getDefault().getLanguage();
        this.uid       = uid;
        this.pid       = pid;
        this.application.registerActivityLifecycleCallbacks(this);
        this.forceGPS = forceGPS;
        apiController  = new ApiController();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!isFirst) {

            isFirst = true;
            apiController.callCustomEvent(new Event(uid, application.getPackageName(), pid, manufacturer, model, idiome, RetargetlyUtils.getInstalledApps(application)));
            Log.d(TAG, "First Activity " + activity.getClass().getSimpleName());

        } else {

            apiController.callCustomEvent(new Event(ApiConstanst.EVENT_CHANGE, activity.getClass().getSimpleName(), uid, application.getPackageName(), pid, manufacturer, model, idiome));
            Log.d(TAG, "Activity " + activity.getClass().getSimpleName());

        }

        if(forceGPS)
            RetargetlyUtils.checkPermissionGps(activity);

        if(currentActivity != activity) {

            currentActivity = activity;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();

                fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentResumed(FragmentManager fm, Fragment f) {

                        super.onFragmentResumed(fm, f);
                        Log.d(TAG, "Fragment: " + f.getClass().getSimpleName());
                        apiController.callCustomEvent(new Event(ApiConstanst.EVENT_CHANGE, f.getClass().getSimpleName(), uid, application.getPackageName(), pid, manufacturer, model, idiome));

                    }
                }, false);

            } else {

                android.app.FragmentManager fm = activity.getFragmentManager();

                fm.registerFragmentLifecycleCallbacks(new android.app.FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentResumed(android.app.FragmentManager fm, android.app.Fragment f) {

                        super.onFragmentResumed(fm, f);
                        Log.d(TAG, "Fragment: " + f.getClass().getSimpleName());
                        apiController.callCustomEvent(new Event(ApiConstanst.EVENT_CHANGE, f.getClass().getSimpleName(), uid, application.getPackageName(), pid, manufacturer, model, idiome));

                    }
                }, false);

            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DialogGpsUtils.closeDialogSettings();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        DialogGpsUtils.closeDialogSettings();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DialogGpsUtils.closeDialogSettings();
    }

}
