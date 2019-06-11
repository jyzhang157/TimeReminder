package com.example.timereminder.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.example.timereminder.R;
import com.example.timereminder.core.datastructure.ExpressMessage;
import com.example.timereminder.core.datastructure.TaskMessage;

import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static java.lang.Math.toIntExact;

public class AlarmHelper {


    public AlarmHelper(){};
//    public void setAlarmManager(AlarmManager am){
//        mAlarmManeger=am;
//    }
//
//    public AlarmManager getAlarmManager(){
//        return mAlarmManeger;
//    }

    /**
     * 设定提醒
     * @param context
     * @param nManager
     * @param task 自定义的一个实体类，封装了提醒相关的东西，如提醒的编号ID，提醒的时间等
     */
    public static void setAlarm(Context context, NotificationManager nManager, TaskMessage task,int id)
    {

        // 注册AlarmManager的定时服务
        Intent intent=new Intent();// Constants.ACITON_REMIND是自定义的一个action

        // 使用Reminder实体的ID作为PendingIntent的requestCode可以保证PendingIntent的唯一性
        PendingIntent pi=PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设定的时间是Reminder实体中封装的时间
        Notification notification=new NotificationCompat.Builder(context,context.getString(R.string.channel_id))
                .setContentTitle(task.getName())
                .setContentText(task.getDescription().toString())
                .setWhen(task.getTime().getTime())
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build();
        nManager.notify(id,notification);
    }


    public static void setAlarm(Context context, NotificationManager nManager, ExpressMessage express, int id)
    {

        // 注册AlarmManager的定时服务
        Intent intent=new Intent();// Constants.ACITON_REMIND是自定义的一个action

        // 使用Reminder实体的ID作为PendingIntent的requestCode可以保证PendingIntent的唯一性
        PendingIntent pi=PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设定的时间是Reminder实体中封装的时间
        Notification notification=new NotificationCompat.Builder(context,context.getString(R.string.channel_id))
                .setContentTitle(express.getName())
                .setContentText(express.getCode())
                .setWhen(express.getTime().getTime())
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build();
        nManager.notify(id,notification);
    }
    /**
     * 取消提醒
     * @param context
     * @param nManager
     * @param id
     */
    public static void cancelAlarm(Context context,NotificationManager nManager,int id)
    {
        // 取消AlarmManager的定时服务
        Intent intent=new Intent();// 和设定闹钟时的action要一样

        // 这里PendingIntent的requestCode、intent和flag要和设定闹钟时一样
        PendingIntent pi=PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        nManager.cancel(id);
    }
}
