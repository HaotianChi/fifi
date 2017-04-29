package edu.temple.fifi;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class loghandler {

    public void appendLog(Context context, String text, String time) {

        File logFile = new File(context.getFilesDir(), "log");

        if (!logFile.exists()) {//create if log does not exists
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text + "@" + time);
            buf.newLine();
            buf.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public JSONObject readLog(Context context) {

        File logFile = new File(context.getFilesDir(), "log");
        JSONObject json = new JSONObject();

        JSONArray messages = new JSONArray();
        JSONArray times = new JSONArray();

        if (!logFile.exists()) return null;

        try {

            BufferedReader br = new BufferedReader(new FileReader(logFile));
            String line;

            while ((line = br.readLine()) != null) {

                messages.put(line.split("@")[0]);
                times.put(line.split("@")[1]);

            }

            try {
                json.put("Messages", messages);
                json.put("Times", times);
            } catch (JSONException je) {
                je.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }
}
