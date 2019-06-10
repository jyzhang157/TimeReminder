package com.example.timereminder.pager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.timereminder.AddActivity;
import com.example.timereminder.AddTaskActivity;
import com.example.timereminder.EditActivity;
import com.example.timereminder.MainActivity;
import com.example.timereminder.R;
import com.example.timereminder.ShowActivity;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.ExpressMessage;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.task.ExpressAdapter;
import com.example.timereminder.task.TaskAdapter;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class notificationFragment extends BaseFragment implements
    MainActivity.OnUpdateNotificationFragment{

    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    Calendar mCalendar;
    RecyclerView taskRecyclerView;
    LinearLayoutManager layoutManager;
    TaskAdapter<TaskMessage> adapter;

    RecyclerView ExpressRecyclerView;
    LinearLayoutManager expressLayoutManager;
    ExpressAdapter<ExpressMessage> expressAdapter;

    List<TaskMessage> taskList;
    List<ExpressMessage> expressList;

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


        mRootView.findViewById(R.id.iv_func).setVisibility(View.INVISIBLE);

        ((MainActivity)getActivity()).setOnUpdateNotificationFragment(this);
        //TODO:添加一些基本的事件类型，用于测试显示列表，后续注意删除
        taskList=new ArrayList<TaskMessage>();
        taskRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter=new TaskAdapter<TaskMessage>(getContext());
        adapter.addAll(taskList);
        taskRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, long itemId) {
                Intent intent=new Intent(getActivity(), ShowActivity.class);
                intent.putExtra("task_message",taskList.get(position));
                startActivityForResult(intent,2);
            }
        });

        expressList=new ArrayList<ExpressMessage>();
        ExpressRecyclerView=(RecyclerView) mRootView.findViewById(R.id.recycler_view_express);
        expressLayoutManager=new LinearLayoutManager(getContext());
        expressLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ExpressRecyclerView.setLayoutManager(expressLayoutManager);
        expressAdapter=new ExpressAdapter<ExpressMessage>(getContext());
        expressAdapter.addAll(expressList);
        ExpressRecyclerView.setAdapter(expressAdapter);

        expressAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, long itemId) {
                Intent intent=new Intent(getActivity(), ShowActivity.class);
                intent.putExtra("express_message",expressList.get(position));
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected  void initData(){
        onUpdate();
    }
//TODO:这里用于接受添加任务活动的返回值
//    @Override
//    public void onActivityResult(int requestCode, int resultCode , Intent data){
//        switch (requestCode){
//            case 1:
//                if(resultCode==RESULT_OK) {
//                    String returnedData = data.getStringExtra("item_return");
//                    Log.d("show time", returnedData);
//                }
//                break;
//            default:
//        }
//    }
    @Override
    public void onUpdate(){
        taskList= TaskDatabaseHelper.getAllDataInTimeOrder(TaskMessage.class,true);
        adapter.clear();
        adapter.addAll(taskList);

        expressList=TaskDatabaseHelper.getAllDataInTimeOrder(ExpressMessage.class,true);
        expressAdapter.clear();
        expressAdapter.addAll(expressList);
    }
}
