package com.example.tp_partie3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

//
public class MainActivity extends AppCompatActivity {
    IntentFilter filter1;
    IntentFilter filter2;
    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filter1 = new IntentFilter("ACTION_BATTERY_LOW");
        filter1 = new IntentFilter("android.intent.action.PHONE_STATE");

        ((Button) findViewById(R.id.send)).setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(),MyReceiver.class);
            intent.setAction("FAKE_EVENT_INFO");
            intent.putExtra("url","helo");
            sendBroadcast(intent);
        });

        if (ActivityCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new
                                String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[]grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
                    this.registerReceiver(new MyCallState(), filter2);
                } else {
                    this.unregisterReceiver(new MyCallState());
                }
                return;
            } }
    }




    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(new MyBroadcastBatteryLow(), filter1)
        //this.registerReceiver(new MyCallState(), filter2);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(new MyBroadcastBatteryLow());
        this.unregisterReceiver(new MyCallState());
    }

    class MyBroadcastBatteryLow extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ((TextView) findViewById(R.id.text)).setText("Evenement Batterie faible reçu");
        }
    }







    class MyCallState extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String callState =
                    intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER); //TO DO: xxx
// utilisez TelephonyManager pour obtenir le numéro appelant en tant qu’extra
            if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING)){
//appel entrant
                Date callStartTime = new Date();
                String out = "number : "+number+"\n\n"+"Instant :"+callStartTime.toString();
                ((TextView ) findViewById(R.id.text)).setText(out);
            }

        }
    }
}