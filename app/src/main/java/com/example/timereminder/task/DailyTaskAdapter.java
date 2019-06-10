package com.example.timereminder.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timereminder.R;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.core.datastructure.ExpressMessage;
import com.example.timereminder.core.datastructure.TaskMessage;

import java.util.Date;
import java.util.Locale;

public class DailyTaskAdapter<E extends TaskMessage> extends BaseRecyclerAdapter<E> {

    public DailyTaskAdapter(Context context){
        super(context);
    }
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type){
        return new TaskViewHolder(mInflater.inflate(R.layout.daily_task_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, E item, int position) {
        TaskViewHolder h= (TaskViewHolder) holder;
        E task=mItems.get(position);
        if(null!=item.getName())
            h.mName.setText(item.getName());
        if(null!=item.getLocation()&&(item.getLocation().length()!=0))
            h.mLocation.setText(item.getLocation());
        else
            h.mLocation.setVisibility(View.GONE);
        //TODO:在这里调整显示时间的格式
        if(null!=item.getTime()){
            Date temp=item.getTime();
            h.mBDate.setText(String.format(Locale.getDefault(),"%04d年%02d月%02d日",temp.getYear()+1900,temp.getMonth()+1,temp.getDate()));
            h.mBTime.setText(String.format(Locale.getDefault()," %02d:%02d",temp.getHours(),temp.getMinutes()));
        }

        if(null!=item.getEndTime()){
            Date temp=item.getEndTime();
            h.mEDate.setText(String.format(Locale.getDefault(),"%04d年%02d月%02d日",temp.getYear()+1900,temp.getMonth()+1,temp.getDate()));
            h.mETime.setText(String.format(Locale.getDefault()," %02d:%02d",temp.getHours(),temp.getMinutes()));
        }
        if(item instanceof ExpressMessage) {
            h.mExpress.setVisibility(View.VISIBLE);
            h.mExpress.setText(Integer.toString(((ExpressMessage) item).getCode()));
        }
        else
            h.mExpress.setVisibility(View.GONE);
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mLocation;
        TextView mBDate;
        TextView mBTime;
        TextView mEDate;
        TextView mETime;
        TextView mExpress;


        public TaskViewHolder(View view){
            super(view);
            mName=(TextView) view.findViewById(R.id.dt_name);
            mLocation=(TextView) view.findViewById(R.id.dt_location);
            mBDate=(TextView) view.findViewById(R.id.dt_start_date);
            mBTime=(TextView) view.findViewById(R.id.dt_start_time);
            mEDate=(TextView) view.findViewById(R.id.dt_end_date);
            mETime=(TextView) view.findViewById(R.id.dt_end_time);
            mExpress=(TextView) view.findViewById(R.id.dt_express_code);
        }
    }

}
