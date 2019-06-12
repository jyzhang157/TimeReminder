package com.example.timereminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.example.timereminder.alarm.AlarmHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

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
                //避免在编辑事件时传入短信打断之前的过程
//                Intent mIntent = new Intent("ReadSMS");
//                mIntent.putExtra("sms",receiveText);
//                context.sendBroadcast(mIntent);

                AlarmHelper.setAlarm(context,(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE),receiveText);
            }
        }

        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
