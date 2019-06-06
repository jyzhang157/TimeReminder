package com.example.dell.alarmalert_csdn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Notification.Builder;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.dell.alarmalert_csdn.NotificationUtils;
import java.util.TimeZone;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mManager;
    Button sendn;
    public static final String id = "channel_1";
    public static final String name = "名字";
    private NotificationUtils notificationUtils;

    TextView show1;
    TextView show2;
    TextView show3;
    Button setTime1;
    Button setTime2;
    Button setTime3;
    Button delete1;
    Button delete2;
    Button delete3;
    String show1String = null;
    String show2String = null;
    String show3String = null;
    String defalutString = "目前无设置";
    AlertDialog builder=null;
    Calendar c=Calendar.getInstance();
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this,R.raw.clockmusic2);
        SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
        show1String = settings.getString("TIME1", defalutString);
        show2String = settings.getString("TIME2", defalutString);
        show3String = settings.getString("TIME3", defalutString);

        Notice();
        InitSetTime1();
        InitSetTime2();
        InitSetTime3();
        InitDelete1();
        InitDelete2();
        InitDelete3();


        show1.setText(show1String);
        show2.setText(show2String);
        show3.setText(show3String);
    }
    private void InitSetTime1(){
        show1 =(TextView)findViewById(R.id.show1);
        setTime1 = (Button)findViewById(R.id.settime1);
        setTime1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setTimeInMillis(System.currentTimeMillis());

                int year=2019;
                int month=5;//
                int day=6;
                int hourofday=19;
                int minute=47;
                int second=0;


                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                c.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                calendar.set(Calendar.HOUR_OF_DAY,hourofday);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,second);

                Intent intent = new Intent(MainActivity.this, CallNotice.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), sender);
                }
               //System.currentTimeMillis()
                //calendar.getTimeInMillis()
                String tmpS=format(hourofday)+"："+format(minute);
                show1.setText(tmpS);   //显示闹钟设置的时间

                //SharedPreferences保存数据，并提交
                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", tmpS);
                editor.commit();

                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                        Toast.LENGTH_SHORT)
                        .show();

            }
        });
    }
    private void InitDelete1(){
        delete1 = (Button)findViewById(R.id.delete1);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                show1.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }

    private void InitSetTime2(){
        show2 = (TextView)findViewById(R.id.show2);
        setTime2 = (Button)findViewById(R.id.settime2);
        setTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTimeInMillis(System.currentTimeMillis());
                int year=2019;
                int month=5;
                int day=6;
                int hourofday=19;
                int minute=07;
                int second=0;

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                c.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                calendar.set(Calendar.HOUR_OF_DAY,hourofday);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,second);


                /*NotificationManager manager=
                        (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification =new NotificationCompat.Builder(MainActivity.this,"default")
                        .setContentTitle("测试notification")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .build();
                manager.notify(1,notification);*/

                /*int PUSH_NOTIFICATION_ID = (0x001);
                String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
                String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, 0);
                builder.setContentTitle("通知标题")//设置通知栏标题
                        .setContentIntent(pendingIntent) //设置通知栏点击意图
                        .setContentText("通知内容")
                        .setTicker("通知内容") //通知首次出现在通知栏，带上升动画效果的
                        .setWhen(calendar.getTimeInMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setSmallIcon(R.mipmap.ic_launcher)//设置通知小ICON
                        .setChannelId(PUSH_CHANNEL_ID)
                        .setDefaults(Notification.DEFAULT_ALL);

                Notification notification = builder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                if (notificationManager != null) {
                    notificationManager.notify(PUSH_NOTIFICATION_ID, notification);
                }
*/

                Intent intent = new Intent(MainActivity.this, Notice.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), sender);
                }

                String tmpS=format(hourofday)+"："+format(minute);
                show2.setText(tmpS);   //显示闹钟设置的时间

                //SharedPreferences保存数据，并提交
                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", tmpS);
                editor.commit();

                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
    private void InitDelete2(){
        delete2 = (Button)findViewById(R.id.delete2);
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                show2.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }
    private void InitSetTime3(){
        show3 = (TextView)findViewById(R.id.show3);
        setTime3 = (Button)findViewById(R.id.settime3);
        setTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setTimeInMillis(System.currentTimeMillis());
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);


                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute)
                            {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.set(Calendar.SECOND,0);
                                c.set(Calendar.MILLISECOND,0);

                                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                                PendingIntent sender=PendingIntent.getBroadcast(
                                        MainActivity.this,0, intent, 0);
                                AlarmManager am;
                                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
                                }

                                String tmpS=format(hourOfDay)+"："+format(minute);
                                show3.setText(tmpS);

                                //SharedPreferences保存数据，并提交
                                SharedPreferences time1Share = getPreferences(0);
                                SharedPreferences.Editor editor = time1Share.edit();
                                editor.putString("TIME1", tmpS);
                                editor.commit();

                                Toast.makeText(MainActivity.this,"设置闹钟时间为"+tmpS,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        },mHour,mMinute,true).show();
            }
        });
    }
    private void InitDelete3(){
        delete3 = (Button)findViewById(R.id.delete3);
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallAlarm.class);
                PendingIntent sender=PendingIntent.getBroadcast(
                        MainActivity.this,0, intent, 0);
                AlarmManager am;
                am =(AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(MainActivity.this,"闹钟时间删除",
                        Toast.LENGTH_SHORT).show();
                show3.setText("目前无设置");

                SharedPreferences time1Share = getPreferences(0);
                SharedPreferences.Editor editor = time1Share.edit();
                editor.putString("TIME1", "目前无设置");
                editor.commit();
            }
        });
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mediaPlayer.stop();
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mediaPlayer.stop();
            builder = new AlertDialog.Builder(MainActivity.this)

                    .setTitle("温馨提示：")
                    .setMessage("您是否要退出程序？")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    mediaPlayer.stop();
                                    MainActivity.this.finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    mediaPlayer.stop();
                                    builder.dismiss();
                                }
                            }).show();
        }
        return true;
    }
    private void Notice() {
        sendn = (Button)findViewById(R.id.notification);
        sendn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        NotificationUtils notificationUtils = new NotificationUtils(MainActivity.this);
                        notificationUtils.sendNotification("警告警告", "DEADLINE就要到了");
        }
        });
    }


    private String format(int x)
    {
        String s=""+x;
        if(s.length()==1) s="0"+s;
        return s;
    }

}
