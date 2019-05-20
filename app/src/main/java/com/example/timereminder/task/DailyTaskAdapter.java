package com.example.timereminder.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timereminder.R;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.core.datastructure.TaskMessage;

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
        h.mName.setText(item.getName());
        h.mLocation.setText(item.getLocation());
        //TODO:在这里调整显示时间的格式
        h.mBTime.setText(item.getTime());
        h.mETime.setText(item.getEndTime());

    }

    private class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mLocation;
        TextView mBTime;
        TextView mETime;


        public TaskViewHolder(View view){
            super(view);
            mName=(TextView) view.findViewById(R.id.dt_name);
            mLocation=(TextView) view.findViewById(R.id.dt_location);
            mBTime=(TextView) view.findViewById(R.id.dt_btime);
            mETime=(TextView) view.findViewById(R.id.dt_etime);
        }
    }

}
