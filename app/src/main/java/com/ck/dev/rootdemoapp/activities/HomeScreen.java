package com.ck.dev.rootdemoapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ck.dev.rootdemoapp.R;
import com.ck.dev.rootdemoapp.utils.Config;

public class HomeScreen extends Activity {

    private Button checkRootBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_screen);
        initView();
    } // onCreate

    private void initView() {
        Config.LOG(Config.TAG_HOME_SCREEN, "Started Home Activity.", false);

        checkRootBtn = this.findViewById(R.id.check_root_btn);

        onClick();
    } // initView

    private void onClick() {
        checkRootBtn.setOnClickListener( v -> {

        });
    } // onClick
}
