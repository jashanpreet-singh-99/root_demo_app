package com.ck.dev.rootdemoapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.dev.rootdemoapp.R;
import com.ck.dev.rootdemoapp.utils.Config;
import com.ck.dev.rootdemoapp.utils.RootExecutor;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeScreen extends Activity {

    private Button   checkRootBtn;
    private Button   executeCommandBtn;
    private EditText commandTxtView;
    private TextView outputTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_screen);
        initView();
    } // onCreate

    private void initView() {
        Config.LOG(Config.TAG_HOME_SCREEN, "Started Home Activity.", false);

        checkRootBtn         = this.findViewById(R.id.check_root_btn);
        executeCommandBtn    = this.findViewById(R.id.execute_command_btn);
        commandTxtView       = this.findViewById(R.id.command_txt_view);
        outputTxtView        = this.findViewById(R.id.output_txt_view);

        onClick();
    } // initView

    private void onClick() {
        checkRootBtn.setOnClickListener( v -> Config.LOG(Config.TAG_HOME_SCREEN, "Root check called : " + RootExecutor.checkRoot(), true));

        executeCommandBtn.setOnClickListener( v -> {
            String command = commandTxtView.getText().toString();
            if (command.isEmpty()) {
                Toast.makeText(HomeScreen.this, "No command to execute.", Toast.LENGTH_SHORT).show();
                Config.LOG(Config.TAG_HOME_SCREEN, "No command in CommandTxtView.", true);
                return;
            }

            ArrayList<String> commands = new ArrayList<>(Arrays.asList(command.split("\\s+")));
            String output = RootExecutor.execute(commands);
            Config.LOG(Config.TAG_HOME_SCREEN, "Executed Command : " + output, true);
            outputTxtView.setText(output);
        });
    } // onClick
}
