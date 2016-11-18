package lab.csci4100u.jocstech.lab04broadcastreceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by jocs on 2016-10-17.
 */



public class BatteryReceiver extends BroadcastReceiver {

    private Context contexts;

    @Override
    public void onReceive(Context context, Intent intent) {
        contexts = context;
        String statusMsg="";
        String healthMsg="";
        String pluginMsg="";
        String tempMsg="";

        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            boolean isHealthy = status == BatteryManager.BATTERY_HEALTH_GOOD;


            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            boolean wirelessCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;

            float temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.0f;
            tempMsg=temperature+"ºC";

            // Charging logic
            if (status != -1) {
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        statusMsg = "Unknown";
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        statusMsg = "Charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        statusMsg = "Discharging";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        statusMsg = "Not Charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        statusMsg = "Full";
                        break;
                    default:
                        statusMsg = "Unknown";
                        break;
                }
                Toast.makeText(context, "Charging Status: " + statusMsg, Toast.LENGTH_LONG).show();
                createNotification(0,"Charging Status: " + statusMsg);
            }

            // Health logic
            if (health != -1) {

                switch (status) {
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        healthMsg = "Good";
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        healthMsg = "Failed";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        healthMsg = "Dead";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        healthMsg = "Overvoltage";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        healthMsg = "Overheated";
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        healthMsg = "Unknown";
                        break;
                    default:
                        healthMsg = "Unknown";
                        break;
                }
                Toast.makeText(context, "Health Status: " + healthMsg, Toast.LENGTH_LONG).show();
                createNotification(1,"Health Status: " + healthMsg);
            }

            // Charging plugin logic
            if(chargePlug !=-1){
                switch (chargePlug){
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        pluginMsg = "AC Power Plugged in";
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB:
                        pluginMsg = "USB Power Plugged in";
                        break;
                    case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                        pluginMsg = "Wireless Power Plugged in";
                        break;
                    default:
                        pluginMsg = "Unknown Type";
                        break;
                }
                Toast.makeText(context, "Plugin Type: " + pluginMsg, Toast.LENGTH_LONG).show();
                createNotification(2,"Plugin Type: " + pluginMsg);
            }

            // Temperature logic
            Toast.makeText(context, "Battery Temperature: " + tempMsg, Toast.LENGTH_LONG).show();
            createNotification(3,"Battery Temperature: " + tempMsg + "ºC");


        }//end if




    }//end onRev

    public void createNotification(int NID,String content){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(contexts);

        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle("Battery Information:");
        builder.setContentText(content);


        NotificationManager NM = (NotificationManager) contexts.getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(NID,builder.build());

    }



}
