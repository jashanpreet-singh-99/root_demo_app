package com.ck.dev.rootdemoapp.utils;

import android.util.Log;

public class Config {

    private static final boolean DEBUG_MODE = true;

    public static void LOG(String tag, String message, boolean error) {
        if (DEBUG_MODE)
            return;
        if (error)
            Log.e(tag, message);
        else
            Log.d(tag, message);
    } // LOG

}
