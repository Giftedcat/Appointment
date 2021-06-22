package com.giftedcat.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.giftedcat.appointment.utils.AppointDateUtil;
import com.giftedcat.appointment.utils.DateUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    TextView tvStartTime;
    TextView tvEndTime;

    private List<Long> timeStampList;
    private List<String> hours;
    private List<String> minutes;

    private int[] selectTime;

    OptionsPickerView pvTime;

    private int select_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        timeStampList = AppointDateUtil.getTimeStampList();
        hours = AppointDateUtil.getHours();
        minutes = AppointDateUtil.getMinutes();
        selectTime = AppointDateUtil.getNormalPosition();

        initView();
        pvTime.setNPicker(AppointDateUtil.getDateList(), hours, minutes);//添加数据
        tvStartTime.setText(DateUtil.stampToAppointDate(getSelectStamp()));

        tvEndTime.setText(DateUtil.stampToAppointDate(getSelectStamp() + 30 * 60 * 1000));
    }

    private void initView(){
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);

        tvStartTime.setOnClickListener(new MyOnclick());
        tvEndTime.setOnClickListener(new MyOnclick());
        findViewById(R.id.tv_confirm).setOnClickListener(new MyOnclick());
        pvTime = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectTime[0] = options1;
                selectTime[1] = options2;
                selectTime[2] = options3;
                switch (select_num){
                    case 0:
                        /** 选择开始时间*/
                        if (getSelectStamp() < System.currentTimeMillis()){
                            Toast.makeText(mContext, "不能选择更早的时间", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tvStartTime.setText(DateUtil.stampToAppointDate(getSelectStamp()));
                        tvEndTime.setText(DateUtil.stampToAppointDate(getSelectStamp() + 30 * 60 * 1000));
                        break;
                    case 1:
                        /** 选择结束时间*/
                        if (getSelectStamp() <= DateUtil.dateToStamp(tvStartTime.getText().toString())){
                            Toast.makeText(mContext, "不能选择比开始时间更早的时间", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tvEndTime.setText(DateUtil.stampToAppointDate(getSelectStamp()));
                        break;
                }
            }
        }).setCancelText("返回")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(20)
                .setTitleSize(20)//标题文字大小
                .setContentTextSize(18)//滚轮文字大小
                .setTitleText("开始时间")//标题文字
                .setTitleColor(0xFF1E1E1E)//标题文字颜色
                .setSubmitColor(0XFF044473)//确定按钮文字颜色
                .setCancelColor(0xFF044473)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
//                .setLinkage(false)//设置是否联动，默认true
                .setLabels("", "", "")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(true, true, true)//循环与否
                .setSelectOptions(selectTime[0], selectTime[1], selectTime[2])  //设置默认选中项
//                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
    }

    /**
     * 根据选择的项，获取相应的时间戳
     * */
    private long getSelectStamp(){
        return AppointDateUtil.cumulativeTime(timeStampList.get(selectTime[0]), Long.parseLong(hours.get(selectTime[1])), Long.parseLong(minutes.get(selectTime[2])));
    }

    /**
     * 显示时间选择弹窗
     * */
    private void showPickerView(int num){
        select_num = num;
        switch (num){
            case 0:
                pvTime.setTitleText("开始时间");
                break;
            case 1:
                pvTime.setTitleText("结束时间");
                break;
        }
        pvTime.show();
    }

    private class MyOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_start_time:
                    /** 选择开始时间*/
                    showPickerView(0);
                    break;
                case R.id.tv_end_time:
                    /** 选择结束时间*/
                    showPickerView(1);
                    break;
                case R.id.tv_confirm:
                    /** 提交*/
                    long start_time = DateUtil.dateToStamp(tvStartTime.getText().toString());
                    long end_time = DateUtil.dateToStamp(tvEndTime.getText().toString());
                    Toast.makeText(mContext, "开始时间：" + start_time + "\r\n结束时间：" + end_time, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}