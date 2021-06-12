package com.spectra.fieldforce.application;

import android.content.Context;

import androidx.multidex.MultiDexApplication;


public class App extends MultiDexApplication {
    public static boolean DEBUG=true;
    public static boolean READMOCKJSON=false;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        context=getApplicationContext();
    }

    private static com.spectra.fieldforce.application.App sInstance;
    public static Context context;

    public static synchronized com.spectra.fieldforce.application.App getInstance(){return sInstance;}

}
