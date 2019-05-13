package com.example.timereminder.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.timereminder.R;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.core.datastructure.TaskMessage;

import java.util.List;

public class TaskAdapter<E extends TaskMessage> extends BaseRecyclerAdapter<E> {

    public TaskAdapter(Context context){
        super(context);
    }
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type){
        return new TaskViewHolder(mInflater.inflate(R.layout.task_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, E item, int position) {
        TaskViewHolder h= (TaskViewHolder) holder;
        E task=mItems.get(position);
        h.mCheckbox.setChecked(item.isChecked());
        h.mName.setText(item.getName());
        //TODO:在这里调整显示时间的格式
        h.mTime.setText(item.getTime());

    }

    private class TaskViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckbox;
        TextView mName;
        TextView mTime;

        public TaskViewHolder(View view){
            super(view);
            mCheckbox=(CheckBox) view.findViewById(R.id.cb);
            mName=(TextView) view.findViewById(R.id.tv_name);
            mTime=(TextView) view.findViewById(R.id.tv_time);
        }
    }

}
