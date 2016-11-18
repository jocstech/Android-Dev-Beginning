package lab.csci4100u.jocstech.lab04broadcastreceivers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private BatteryReceiver BReceiver;
    public String Battery_Status="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        BReceiver = new BatteryReceiver();

        // Listening to battery broadcast
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(BReceiver , intentFilter);

        createNotification(1,"Battery Changed");
        updateChargingStatus(Battery_Status);
    }



        @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.BReceiver);
    }


    public void createNotification(int NID,String content){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle("Battery Information:");
        builder.setContentText(content);


        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(NID,builder.build());

    }

    public void updateChargingStatus(String update){

        TextView chargingStatus = (TextView)findViewById(R.id.textViewChargingStatus);
        chargingStatus.setText(update);
    }

    public void updateHealthStatus(String update){

        TextView healthStatus = (TextView)findViewById(R.id.textViewBatteryHealth);
        healthStatus.setText(update);
    }

}
