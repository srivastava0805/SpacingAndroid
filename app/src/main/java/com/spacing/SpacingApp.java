package com.spacing;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class SpacingApp extends MultiDexApplication {
    public static final String  DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN = "DONT_ASK_STORAGE_PERMISSION_AGAIN";
    public static final String  DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN = "DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN";
    public static final int ASK_STORAGE_PERMISSION_REQUEST_CODE = 100;
    public static final int ASK_LOCATION_PERMISSION_REQUEST_CODE = 102;
    private static Context mContext;

    public static void putValue(String dontAskForLoocationPermissionAgain, boolean b) {

    }

    public static boolean getBoolean(String dontAskForLoocationPermissionAgain) {
        return false;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
