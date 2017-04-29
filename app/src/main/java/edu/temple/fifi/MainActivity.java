package edu.temple.fifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loghandler logger = new loghandler();
        JSONObject json = logger.readLog(getApplicationContext());
        JSONArray messages;
        JSONArray times;

        if (json != null) {
            try {
                messages = json.getJSONArray("Messages");
                times = json.getJSONArray("Times");

                Log.d("Message ", messages.getString(0));
                Log.d("Times ", times.getString(0));

                TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
                tl.setColumnStretchable(0, true);
                tl.setColumnStretchable(1, true);

                for (int i = 0; i < messages.length(); i++) {

                    TableRow row = new TableRow(this);

                    TextView msg = new TextView(this);
                    msg.setText(messages.getString(i));

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy @ HH:mm", Locale.US);


                    TextView tim = new TextView(this);
                    tim.setText(sdf.format(new Date(Long.valueOf(times.getString(i)))));

                    row.addView(msg);
                    row.addView(tim);

                    tl.addView(row);
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("fbMessage"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void updateUI(Intent i) {

        String message = i.getStringExtra("Message");
        String time = i.getStringExtra("Time");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy @ HH:mm", Locale.US);


        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
        tl.setColumnStretchable(0, true);
        tl.setColumnStretchable(1, true);

        TableRow row = new TableRow(this);

        TextView msg = new TextView(this);
        msg.setText(message);

        TextView tim = new TextView(this);
        tim.setText(sdf.format(new Date(Long.valueOf(time))));

        row.addView(msg);
        row.addView(tim);

        tl.addView(row);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.this.updateUI(intent);
        }
    };

}
