package com.ck.dev.rootdemoapp.activities;

import android.app.Activity;
import android.os.Bundle;

import com.ck.dev.rootdemoapp.R;
import com.ck.dev.rootdemoapp.utils.Config;

public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_screen);
        initView();
    } // onCreate

    private void initView() {
        Config.LOG(Config.TAG_HOME_SCREEN, "Started Home Activity.", false);
    } // initView
}
