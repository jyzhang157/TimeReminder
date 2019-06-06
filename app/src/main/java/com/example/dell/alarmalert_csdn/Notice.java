package com.example.dell.alarmalert_csdn;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Notice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                NotificationUtils notificationUtils = new NotificationUtils(this);
               notificationUtils.sendNotification("警告警告", "DEADLINE就要到了");
    }
}
