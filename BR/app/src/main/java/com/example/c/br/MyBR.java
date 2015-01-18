package com.example.c.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by c on 2015-01-18.
 */
public class MyBR extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msge = null;
        String str = "";

        if(bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            msge = new SmsMessage[pdus.length];

            for(int i=0; i<msge.length; i++){
                msge[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                str += "sms from : "+msge[i].getOriginatingAddress();
                str += "message : "+msge[i].getMessageBody()+"\n";
            }

            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }
}
