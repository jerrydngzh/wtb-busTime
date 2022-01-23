package nwHacks2022.bustimeapp.model;

import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.IOException;

public class InfoGetter {
    public static final String BUS_NUMBER = "33333";


    public static void sendMessage(String msg) throws Exception {
        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(BUS_NUMBER,null, msg,null,null);
        } catch (Exception e) {
            throw new IOException("Could not send message!");
        }
    }
}
