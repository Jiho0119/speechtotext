package com.speechtotext;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication sInstance;
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = sInstance.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return MyApplication.sInstance;
    }
}