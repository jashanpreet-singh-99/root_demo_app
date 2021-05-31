package com.ck.dev.rootdemoapp.utils;

import android.util.Log;

public class Config {

    public static final String TAG_SPLASH_SCREEN = "message_activity_splash_screen";
    public static final String TAG_HOME_SCREEN   = "message_activity_home_screen";
    public static final String TAG_ROOT_EXECUTOR = "message_root_executor";

    private static final boolean DEBUG_MODE = true;

    public static void LOG(String tag, String message, boolean error) {
        if (!DEBUG_MODE)
            return;
        if (error)
            Log.e(tag, message);
        else
            Log.d(tag, message);
    } // LOG

}
