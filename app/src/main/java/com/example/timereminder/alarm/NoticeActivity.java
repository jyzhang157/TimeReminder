package com.example.timereminder.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.timereminder.R;
import com.example.timereminder.core.datastructure.ExpressMessage;
import com.example.timereminder.core.datastructure.TaskMessage;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NoticeActivity extends BroadcastReceiver {

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        String task=(String) intent.getStringExtra("task_message_name");
        String express=(String) intent.getStringExtra("express_message_name");
        if(task!=null){
            int task_id=(int) intent.getLongExtra("task_message_id",0);
            Long task_time=(Long) intent.getLongExtra("task_message_time",0);
            String task_descrip=(String) intent.getStringExtra("task_message_descrip");
            TaskMessage temp= new TaskMessage(task,new Date(task_time),task_descrip);
            AlarmHelper.setAlarm(context,(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE),temp,task_id);
        }
        else if(express!=null){
            int express_time=(int) intent.getLongExtra("express_message_time",0);
            String express_code=(String) intent.getStringExtra("express_message_code");
            ExpressMessage temp=new ExpressMessage();
            temp.setName(express);
            temp.setTime(new Date(express_time));
            temp.setCode(express_code);
            AlarmHelper.setAlarm(context,(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE), temp,(-express_time-1));
        }
    }
}
