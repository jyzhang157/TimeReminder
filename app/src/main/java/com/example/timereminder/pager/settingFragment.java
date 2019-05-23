package com.example.timereminder.pager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.timereminder.R;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.task.TaskAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class settingFragment extends BaseFragment {

    EditText mName;
    EditText mTime;
    EditText mETime;
    EditText mLocation;
    Button mAddButton;
    RecyclerView taskRecyclerView;
    LinearLayoutManager layoutManager;
    TaskAdapter<TaskMessage> adapter;
    List<TaskMessage> taskList;
    Button mDButton;
    @Override
    protected int getLayoutId(){
        return R.layout.fragment_pager_setting;
    }

    @Override
    protected void initView(){
        mName=(EditText) mRootView.findViewById(R.id.test_name);
        mTime=(EditText)mRootView.findViewById(R.id.test_date);
        mETime=(EditText)mRootView.findViewById(R.id.test_edate);
        mLocation=(EditText)mRootView.findViewById(R.id.test_location);
        mAddButton=(Button)mRootView.findViewById(R.id.test_add_button);
        mDButton=(Button)mRootView.findViewById(R.id.test_d_button);
        taskRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view);

        layoutManager=new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter=new TaskAdapter<TaskMessage>(getContext());
        adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                Date time;
                Date etime;
                String location;
                name=mName.getText().toString();
                time=convertStringToDate(mTime.getText().toString());
                etime=convertStringToDate(mETime.getText().toString());
                location=mLocation.getText().toString();
                TaskMessage task=new TaskMessage(name,time);
                task.setEndTime(etime);
                task.setLocation(location);
                TaskDatabaseHelper.addData(task);
                updateRecycleView();


            }
        });

        mDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskMessage task=new TaskMessage();
                task=TaskDatabaseHelper.getFirstData(TaskMessage.class);
            }
        });
    }

    @Override
    protected  void initData(){
        adapter.clear();
        Date historyDate,futureDate;
        historyDate=new Date(1990,1,1);
        futureDate=new Date(2000,1,1);
        taskList=TaskDatabaseHelper.findDataInTimeOrder(TaskMessage.class,historyDate,futureDate,true);
        adapter.addAll(taskList);

    }
    //再添加数据后，根据数据库更新显示列表

    private void updateRecycleView(){
        taskList.clear();
        Date historyDate,futureDate;
        historyDate=new Date(1990,1,1);
        futureDate=new Date(2000,1,1);
        taskList=TaskDatabaseHelper.findDataInTimeOrder(TaskMessage.class,historyDate,futureDate,true);
        adapter.clear();
        adapter.addAll(taskList);
    }


    //用于将String解析为Date类型
    private Date convertStringToDate(String str){
        Date date=new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
