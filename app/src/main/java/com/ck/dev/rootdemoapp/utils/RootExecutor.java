package com.ck.dev.rootdemoapp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class RootExecutor {

    /*
    Check whether the device has root permission or not.
    the function will return false if the user denied the access.
     */
    public static boolean checkRoot() {
        boolean returnVal;
        Process suProcess;

        try {
            suProcess = Runtime.getRuntime().exec("su");

            DataOutputStream outputStream = new DataOutputStream(suProcess.getOutputStream());
            BufferedReader inputStream   = new BufferedReader(new InputStreamReader(suProcess.getInputStream()));
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
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            returnVal = false;
            Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while getting Root access : " + e.getMessage(), true);
        }
        return returnVal;
    } // checkRoot

    /*
    Helps to run commands in the root mode.
    The commands are passed in form of an array of strings.
    This function return the output of the command executed.
     */
    public static String execute(ArrayList<String> commands) {
        try {
            if (commands != null && commands.size() > 0) {
                Process suProcess = Runtime.getRuntime().exec("su");

                DataOutputStream outputStream = new DataOutputStream(suProcess.getOutputStream());
                BufferedReader inputStream   = new BufferedReader(new InputStreamReader(suProcess.getInputStream()));

                for (String command: commands) {
                    outputStream.writeBytes(command + "\n");
                    outputStream.flush();
                }
                String data = readDataFromStream(inputStream);
                outputStream.writeBytes("exit\n");
                outputStream.flush();

                outputStream.close();
                inputStream.close();
                try {
                    if (suProcess.waitFor() != 255) {
                        return data;
                    } else {
                        return "Command execution failed.";
                    }
                } catch (InterruptedException e) {
                    Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while executing command in Root mode : " + e.getMessage(), true);
                    return "Error while executing command in Root mode : " + e.getMessage();
                }
            }
            return "Unknown Command.";
        } catch (IOException e) {
            Config.LOG(Config.TAG_ROOT_EXECUTOR, " Error while getting Root access : " + e.getMessage(), true);
            return "Error : " + e.getMessage();
        }
    } // execute

    private static String readDataFromStream(BufferedReader bufferedReader) {
        try {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null && bufferedReader.ready()) {
                Config.LOG(Config.TAG_ROOT_EXECUTOR, "Read : " + line + " : " + bufferedReader.ready(), false);
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            Config.LOG(Config.TAG_ROOT_EXECUTOR, "Error while reading stream : " + e.getMessage(), false);
            return "Error in reading data.";
        }
    }

}
