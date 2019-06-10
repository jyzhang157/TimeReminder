package com.example.timereminder.pager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timereminder.R;
import com.example.timereminder.base.adapter.BaseRecyclerAdapter;
import com.example.timereminder.base.fragment.BaseFragment;
import com.example.timereminder.core.database.TaskDatabaseHelper;
import com.example.timereminder.core.datastructure.TaskMessage;
import com.example.timereminder.core.datepicker.DatePickerFragment;
import com.example.timereminder.core.datepicker.TimePickerFragment;
import com.example.timereminder.task.TaskAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.DensityUtil;

public class settingFragment extends BaseFragment
        {

            @Override
            protected int getLayoutId(){
                return R.layout.setting;
            }

            @Override
            protected void initView(){
                final TextView rmd = (TextView) mRootView.findViewById(R.id.remind_way);
                LinearLayout set = (LinearLayout) mRootView.findViewById(R.id.layout_set);
                set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showset(rmd);
                    }
                });

                LinearLayout about = (LinearLayout) mRootView.findViewById(R.id.layout_about);
                about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showdialog();
                    }
                });

                LinearLayout feed = (LinearLayout) mRootView.findViewById(R.id.layout_feedback);
                feed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        feedbackdialog();
                    }
                });
            }

            protected void initData(){

            }

            private void showdialog() {
                final Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_normal, null);
                bottomDialog.setContentView(contentView);

                TextView cancel = contentView.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                    }
                });

                bottomDialog.setCancelable(true);
                bottomDialog.setCanceledOnTouchOutside(false);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 20f);
                params.bottomMargin = DensityUtil.dp2px(getContext(), 10f);
                contentView.setLayoutParams(params);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
            }

            private void feedbackdialog() {
                final Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_normal, null);
                bottomDialog.setContentView(contentView);
                TextView title = contentView.findViewById(R.id.title);
                title.setText("反馈与建议");
                TextView content = contentView.findViewById(R.id.content);
                content.setText("GitHub: github.com/jyzhang157/TimeReminder/\n邮箱：xxxxx@sjtu.edu.cn");
                TextView cancel = contentView.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                    }
                });

                bottomDialog.setCancelable(true);
                bottomDialog.setCanceledOnTouchOutside(false);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 20f);
                params.bottomMargin = DensityUtil.dp2px(getContext(), 10f);
                contentView.setLayoutParams(params);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
            }

            private void showset (final TextView textview) {
                final Dialog bottomDialog = new Dialog(getContext(), R.style.BottomDialog);
                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_circle, null);
                TextView ring = contentView.findViewById(R.id.ring);
                TextView shock = contentView.findViewById(R.id.shock);

                TextView cancel = contentView.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                    }
                });

                TextView none = contentView.findViewById(R.id.no_remind);
                none.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textview.setText("不提醒");
                        bottomDialog.dismiss();
                    }
                });


                ring.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textview.setText("仅响铃");
                        bottomDialog.dismiss();
                    }
                });


                shock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textview.setText("仅振动");
                        bottomDialog.dismiss();
                    }
                });

                TextView both = contentView.findViewById(R.id.ring_shock);
                both.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textview.setText("响铃并振动");
                        bottomDialog.dismiss();
                    }
                });
                bottomDialog.setContentView(contentView);
                bottomDialog.setCancelable(true);
                bottomDialog.setCanceledOnTouchOutside(false);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getContext(), 20f);
                params.bottomMargin = DensityUtil.dp2px(getContext(), 10f);
                contentView.setLayoutParams(params);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
            }
}
