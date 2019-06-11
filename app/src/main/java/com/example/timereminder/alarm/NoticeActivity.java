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
        TaskMessage task=(TaskMessage) intent.getSerializableExtra("task_message");
        ExpressMessage express=(ExpressMessage) intent.getSerializableExtra("express_message");
        if(task!=null){
            AlarmHelper.setAlarm(context,(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE),task);
        }
        else if(express!=null){
            AlarmHelper.setAlarm(context,(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE),express);
        }
    }
}
