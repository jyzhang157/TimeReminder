package com.example.activitytest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.hide();
        }

        EditText issue = (EditText) findViewById(R.id.edittext_title);/*事件标题*/
        issue.setFocusable(false);
        issue.setText("学术讲座"); /*测试用，实际值从数据库读取*/

        EditText location = (EditText) findViewById(R.id.edittext_location);/*地点*/
        location.setFocusable(false);
        location.setText("机动学院高田会堂");

        boolean flag = true; //从数据库读取
        Switch exp = (Switch) findViewById(R.id.switch_express);/*快递选择按钮,是快递则显示取货码，否则不显示*/
        final EditText expcode = (EditText) findViewById(R.id.edittext_express_code);/*取货码*/
        LinearLayout expinfo = (LinearLayout) findViewById(R.id.layout_express);
        if(flag == true){
            exp.setChecked(true);
            exp.setClickable(false);
            expcode.setVisibility(View.VISIBLE);
            expcode.setFocusable(false);
            expcode.setText("F43C36"); //从数据库读取
        }
        else{
            expinfo.setVisibility(View.GONE);
        }

        EditText descrip = (EditText) findViewById(R.id.edittext_description);/*备注为空则不显示*/
        descrip.setFocusable(false);
        TextView startdate = (TextView) findViewById(R.id.textview_start_date);/*开始日期*/
        startdate.setClickable(false);
        TextView enddate = (TextView) findViewById(R.id.textview_end_date);/*结束日期*/
        enddate.setClickable(false);
        TextView starttime = (TextView) findViewById(R.id.textview_start_time);/*开始时间*/
        starttime.setClickable(false);
        TextView endtime = (TextView) findViewById(R.id.textview_end_time);/*结束时间*/
        endtime.setClickable(false);
        Spinner remind = (Spinner) findViewById(R.id.spinner_reminder);//提醒
        remind.setClickable(false);


        TextView title = (TextView) findViewById(R.id.textview_title);/*更改页面标题*/
        title.setText("查看活动");

        ImageButton exit = (ImageButton) findViewById(R.id.button_exit);/*更改返回按钮图标*/
        exit.setImageResource(R.drawable.ic_arrow_back_black_24dp);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        ImageButton save = (ImageButton) findViewById(R.id.button_ok);/*隐藏保存按钮*/
        save.setVisibility(View.INVISIBLE);

        Button delete = (Button) findViewById(R.id.button_delete); /*删除*/
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
            }
        });

        Button edit = (Button) findViewById(R.id.button_edit);  /*点击编辑按钮跳转编辑界面*/
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ShowActivity.this, EditActivity.class);
                startActivityForResult(intent,1);
            }
        });


    }
    private void showNormalDialog(){
        final android.support.v7.app.AlertDialog.Builder normalDialog = new AlertDialog.Builder(ShowActivity.this);
        normalDialog.setMessage("确认删除此活动?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除数据并退出
                finish();
            }
        });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.show();
    }
}
