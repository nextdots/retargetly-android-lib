package com.nextdots.retargetly;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.nextdots.retargetly.api.ApiConstanst;
import com.nextdots.retargetly.api.ApiController;
import com.nextdots.retargetly.models.Event;

import java.util.Locale;


public class Retargetly implements Application.ActivityLifecycleCallbacks {

    private Application application = null;

    private boolean isFirst = false;

    private int pid;

    private String uid;
    private String manufacturer;
    private String model;
    private String idiome;

    private ApiController apiController;

    public Retargetly(Application application, String uid, int pid){
        this.application = application;
        manufacturer   = Build.MANUFACTURER;
        model          = Build.MODEL;
        idiome         = Locale.getDefault().getLanguage();
        this.uid       = uid;
        this.pid       = pid;
        this.application.registerActivityLifecycleCallbacks(this);
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

        if(!isFirst){

            isFirst = true;
            apiController.callEvent(new Event(ApiConstanst.EVENT_OPEN, uid, application.getPackageName(), pid, manufacturer, model, idiome));
            Toast.makeText(application,"Primer Activity: "+activity.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();

        }else{

            apiController.callEvent(new Event(ApiConstanst.EVENT_CHANGE, activity.getClass().getSimpleName(), uid, application.getPackageName(), pid, manufacturer, model, idiome));
            Toast.makeText(application,"Activity: "+activity.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();

        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            FragmentManager fm = ((FragmentActivity)activity).getSupportFragmentManager();

            fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentResumed(FragmentManager fm, Fragment f) {

                    super.onFragmentResumed(fm, f);
                    Toast.makeText(application,"Fragmento: "+f.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();
                    apiController.callEvent(new Event(ApiConstanst.EVENT_CHANGE, f.getClass().getSimpleName(), uid, application.getPackageName(), pid, manufacturer, model, idiome));

                }
            },false);

        }else{

            android.app.FragmentManager fm = activity.getFragmentManager();

            fm.registerFragmentLifecycleCallbacks(new android.app.FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentResumed(android.app.FragmentManager fm, android.app.Fragment f) {

                    super.onFragmentResumed(fm, f);
                    Toast.makeText(application,"Fragmento: "+f.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();
                    apiController.callEvent(new Event(ApiConstanst.EVENT_CHANGE, f.getClass().getSimpleName(), uid, application.getPackageName(), pid, manufacturer, model, idiome));

                }
            },false);

        }

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    public static void callEventCustom(Application application, String value, String uid, int pid){
        ApiController apiController  = new ApiController();

        String manufacturer   = Build.MANUFACTURER;
        String model          = Build.MODEL;
        String idiome         = Locale.getDefault().getLanguage();

        apiController.callEvent(new Event(ApiConstanst.EVENT_OPEN, value , uid, application.getPackageName(), pid, manufacturer, model, idiome));
    }

}
