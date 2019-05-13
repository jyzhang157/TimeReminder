package com.example.timereminder.pager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.timereminder.R;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.task.TaskAdapter;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class notificationFragment extends BaseFragment {

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    Calendar mCalendar;

    List<TaskMessage> taskList;

    public static notificationFragment newInstance() {
        return new notificationFragment();
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_pager_notification;
    }

    @Override
    protected void initView(){
        mTextMonthDay = (TextView) mRootView.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) mRootView.findViewById(R.id.tv_year);
        mTextLunar = (TextView) mRootView.findViewById(R.id.tv_lunar);
        mTextCurrentDay = (TextView) mRootView.findViewById(R.id.tv_current_day);
        mCalendar = Calendar.getInstance();

        mTextYear.setText(String.valueOf(mCalendar.get(Calendar.YEAR)));
        mTextMonthDay.setText((mCalendar.get(Calendar.MONTH)+1) + "月" + mCalendar.get(Calendar.DATE) + "日");
        mTextLunar.setText("今日");

        //TODO:添加一些基本的事件类型，用于测试显示列表，后续注意删除
        taskList=new ArrayList<TaskMessage>();
        TaskMessage task1=new TaskMessage("读书","2019-5-12 01:06:55.300");
        taskList.add(task1);
        TaskMessage task2=new TaskMessage("上学","2019-5-12 01:06:55.300");
        taskList.add(task2);
        TaskMessage task3=new TaskMessage("码代码","2019-5-12 01:06:55.300");
        taskList.add(task3);
        TaskMessage task4=new TaskMessage("读书","2019-5-12 01:06:55.300");
        taskList.add(task4);
        TaskMessage task5=new TaskMessage("上学","2019-5-12 01:06:55.300");
        taskList.add(task5);
        TaskMessage task6=new TaskMessage("码代码","2019-5-12 01:06:55.300");
        taskList.add(task6);
        TaskMessage task7=new TaskMessage("读书","2019-5-12 01:06:55.300");
        taskList.add(task7);
        TaskMessage task8=new TaskMessage("上学","2019-5-12 01:06:55.300");
        taskList.add(task8);
        TaskMessage task9=new TaskMessage("码代码","2019-5-12 01:06:55.300");
        taskList.add(task9);

        RecyclerView taskRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        TaskAdapter<TaskMessage> adapter=new TaskAdapter<TaskMessage>(getContext());
        adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    protected  void initData(){
    }
}
