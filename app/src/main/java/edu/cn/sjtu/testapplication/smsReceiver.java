package edu.cn.sjtu.testapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class smsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Bundle bundle = intent.getExtras();
        SmsMessage broadcastMessage;
        if(bundle != null){
            Object[] smsObj =(Object[]) bundle.get("pdus");
            for(Object object : smsObj){
                broadcastMessage = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(broadcastMessage.getTimestampMillis());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                String receiveText = broadcastMessage.getDisplayMessageBody();
                System.out.println("number="+broadcastMessage.getDisplayOriginatingAddress()
                        +", body="+receiveText
                        +", time="+receiveTime);
                Intent mIntent = new Intent("ReadSMS");
                mIntent.putExtra("sms",receiveText);
                context.sendBroadcast(mIntent);
            }
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
