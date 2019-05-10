package com.example.timereminder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timereminder.base.activity.BaseActivity;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.TaskMessage;

import org.litepal.LitePal;


public class MainActivity extends BaseActivity {
//数据库相关，考虑移动到基类里
    //TaskDatabaseHelper<TaskMessage> taskbase;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        //taskbase=new TaskDatabaseHelper<TaskMessage>();
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        findViewById(R.id.testbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskMessage tmp=new TaskMessage("读书","2019-05-09");
                TaskDatabaseHelper.addData(tmp);
                Toast.makeText(MainActivity.this,"create database!",Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TaskMessage tmp=TaskDatabaseHelper.getFirstData(TaskMessage.class);
                if(tmp==null) return;
                Toast.makeText(MainActivity.this,"delete data!"+tmp.getId(),Toast.LENGTH_LONG).show();
                tmp.setLocation("上海");
                TaskDatabaseHelper.updateData(tmp,tmp.getId());
            }
        }
        );
    }

    protected void  initData(){

    }


}
