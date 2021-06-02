package com.ck.dev.rootdemoapp.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class RootExecutor {

    /*
    Check whether the device has root permission or not.
    the function will return false if the user denied the access.
     */
    public static boolean checkRoot() {
        boolean returnVal = false;
        Process suProcess;

        try {
            suProcess = Runtime.getRuntime().exec("su");

            DataOutputStream outputStream = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream inputStream   = new DataInputStream(suProcess.getInputStream());
            if (outputStream != null && inputStream != null) {
                outputStream.writeBytes("id\n");
                outputStream.flush();

                String currentUid = inputStream.readLine();
                boolean exitSU = false;

                if (currentUid == null) {
                    returnVal = false;
                    Config.LOG(Config.TAG_ROOT_EXECUTOR, " Unable to get Root access.", true);
                } else if (currentUid.contains("uid=0")) {
                    returnVal = true;
                    Config.LOG(Config.TAG_ROOT_EXECUTOR, " Root access GRANTED.", false);
                } else {
                    returnVal = false;
                    exitSU = true;
                    Config.LOG(Config.TAG_ROOT_EXECUTOR, " Root permission denied.", true);
                }
                if (exitSU) {
                    outputStream.writeBytes("exit/n");
                    outputStream.flush();
                }
            } else {
                Config.LOG(Config.TAG_ROOT_EXECUTOR, " suProcess Stream empty.", true);
            }
        } catch (IOException e) {
            returnVal = false;
            Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while getting Root access : " + e.getMessage(), true);
        }
        return returnVal;
    } // checkRoot

    /*
    Helps to run commands in the root mode.
    The commands are passed in form of an array of strings.
    The function returns true if the commands executed successfully.
     */
    public static boolean execute(ArrayList<String> commands) {
        boolean returnVal = false;
        try {
            if (commands != null && commands.size() > 0) {
                Process suProcess = Runtime.getRuntime().exec("su");

                DataOutputStream outputStream = new DataOutputStream(suProcess.getOutputStream());

                for (String command: commands) {
                    outputStream.writeBytes(command + "\n");
                    outputStream.flush();
                }
                outputStream.writeBytes("exit\n");
                outputStream.flush();

                try {
                    returnVal = suProcess.waitFor() != 255;
                } catch (InterruptedException e) {
                    Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while executing command in Root mode : " + e.getMessage(), true);
                }
            }
        } catch (IOException e) {
            Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while getting Root access : " + e.getMessage(), true);
        }
        return returnVal;
    } // execute

}
