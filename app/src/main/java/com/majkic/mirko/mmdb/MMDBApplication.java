package com.majkic.mirko.mmdb;

import android.app.Application;
import android.content.Context;

public class MMDBApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
