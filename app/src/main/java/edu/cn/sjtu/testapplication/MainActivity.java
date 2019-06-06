package edu.cn.sjtu.testapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.cn.sjtu.testapplication.sms.smsMatch.SMSMatch;

public class MainActivity extends AppCompatActivity {

    private EditText editText01;
    private Button buttonA;
    private TextView textView02;
    private String Message;
    private SMSMatch smsMatch;

    private  MyReceiver myReceiver;     //注册了一个动态广播来传递信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText01 = findViewById(R.id.editText);
        buttonA = findViewById(R.id.button);
        textView02 = findViewById(R.id.textView2);

        //动态广播接收短信
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ReadSMS");
        registerReceiver(myReceiver,filter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message = editText01.getText().toString();
                smsMatch = new SMSMatch(Message);
                String keyContent = smsMatch.getKeyContent();
                textView02.setText(keyContent);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private class MyReceiver extends BroadcastReceiver{     //动态广播形式接受短信广播数据

        @Override
        public void onReceive(Context context, Intent intent){
            if(intent.getAction().equals("ReadSMS")){
                System.out.println("收到广播");
                String messageFromsmsReceiver = intent.getStringExtra("sms");
                if(!TextUtils.isEmpty(messageFromsmsReceiver)){
                    editText01.setText(messageFromsmsReceiver);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
