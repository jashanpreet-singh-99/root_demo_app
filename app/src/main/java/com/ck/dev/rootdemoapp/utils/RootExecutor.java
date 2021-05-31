package com.ck.dev.rootdemoapp.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class RootExecutor {

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
            }
        } catch (IOException e) {
            returnVal = false;
            Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while getting Root access : " + e.getMessage(), true);
        }
        return returnVal;
    } // checkRoot

}
