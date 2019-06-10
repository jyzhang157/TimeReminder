package com.example.timereminder.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.timereminder.R;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.core.datastructure.ExpressMessage;
import com.example.timereminder.core.datastructure.TaskMessage;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter<E extends TaskMessage> extends BaseRecyclerAdapter<E> {

    View.OnClickListener mOnClickListener;

    public TaskAdapter(Context context){
        super(context);
    }
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type){
        View view=mInflater.inflate(R.layout.task_item, parent, false);
//        if(mOnClickListener!=null)
//            view.setOnClickListener(mOnClickListener);
        return new TaskViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, E item, int position) {
        TaskViewHolder h= (TaskViewHolder) holder;
        E task=mItems.get(position);
        if(null!=item.getName())
            h.mCheckbox.setChecked(item.isChecked());
        if(null!=item.getName())
            h.mName.setText(item.getName());
        if(null!=item.getLocation()&&(item.getLocation().length()!=0))
            h.mLocation.setText(item.getLocation());
        else
            h.mLocation.setVisibility(View.GONE);
        //TODO:在这里调整显示时间的格式
        if(null!=item.getTime()){
            Date temp=item.getTime();
            h.mSDate.setText(String.format(Locale.getDefault(),"%04d年%02d月%02d日",temp.getYear()+1900,temp.getMonth()+1,temp.getDate()));
            h.mSTime.setText(String.format(Locale.getDefault()," %02d:%02d",temp.getHours(),temp.getMinutes()));
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
        CheckBox mCheckbox;
        TextView mName;
        TextView mSDate;
        TextView mSTime;
        TextView mEDate;
        TextView mETime;
        TextView mLocation;
        TextView mExpress;

        public TaskViewHolder(View view){
            super(view);
            mCheckbox=(CheckBox) view.findViewById(R.id.cb);
            mName=(TextView) view.findViewById(R.id.dt_name);
            mSDate=(TextView) view.findViewById(R.id.dt_sdate);
            mSTime=(TextView) view.findViewById(R.id.dt_stime);
            mEDate=(TextView) view.findViewById(R.id.dt_edate);
            mETime=(TextView) view.findViewById(R.id.dt_etime);
            mLocation=(TextView) view.findViewById(R.id.dt_location);
            mExpress=(TextView) view .findViewById(R.id.dt_express);
        }
    }
//    public void setOnClickListener(View.OnClickListener listener){
//        this.mOnClickListener=listener;
//    }
}
