package com.starnet.jn_wr.planup.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starnet.jn_wr.planup.PlanupApplication;
import com.starnet.jn_wr.planup.PuConstants;
import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.entity.Plan;
import com.starnet.jn_wr.planup.util.DateUtil;
import com.starnet.jn_wr.planup.util.GsonUtil;
import com.starnet.jn_wr.planup.util.SdcardUtil;
import com.starnet.jn_wr.planup.util.StringUtil;
import com.starnet.jn_wr.planup.util.SystemUtil;
import com.starnet.jn_wr.planup.util.ToastUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by jn-wr on 2016/5/3.
 */
public class AddPlanActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private ImageView iv_left, iv_right;
    private TextView tv_time, tv_count,tv_repeat_weeks;
    private EditText et_content;
    private RelativeLayout layout_repeat;

    private int select_month;
    private int select_day;
    private int select_hour;
    private int select_minute;
    private String date;
    private long timelong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        init();
        addListener();
    }

    private void init() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_repeat_weeks = (TextView) findViewById(R.id.tv_repeat_weeks);
        et_content = (EditText) findViewById(R.id.et_content);
        layout_repeat = (RelativeLayout) findViewById(R.id.layout_repeat);
        tv_time.setText(DateUtil.getCurrentTime("yyyy-MM-dd HH:mm ") + "(" + DateUtil.WEEKS[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1] + ")");
        timelong =new Date().getTime();
    }

    private void addListener() {
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_count.setOnClickListener(this);
        layout_repeat.setOnClickListener(this);
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_count.setText((500 - et_content.getText().length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + (monthOfYear+1 ) + "-" + dayOfMonth + "  ";
        select_month = monthOfYear+1;
        select_day = dayOfMonth;
        showTimeChooser();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        try {
            timelong = DateUtil.stringToDateLong(date + hourOfDay + ":" + minute, DateUtil.TIME_FORMAT_LINE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timelong);

            select_hour =hourOfDay;
            select_minute=minute;
            int week=calendar.get(Calendar.DAY_OF_WEEK);

            if(minute==0){
                tv_time.setText(date + hourOfDay + ":" + minute+"0 ("+ DateUtil.WEEKS[week-1]+")");
            }else{
                tv_time.setText(date + hourOfDay + ":" + minute+" ("+ DateUtil.WEEKS[week-1]+")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        if (iv_right == view) {

            if(checkValues()){
                savePlan();
            }

        } else if (iv_left == view) {
            onBackPressed();
        } else if (tv_time == view) {
            showDateChooser();
        } else if (layout_repeat == view) {
            showPopwindow();
        }
    }

    private void savePlan(){
        Plan plan=new Plan();
        plan.setActtime(""+timelong);
        plan.setContent(et_content.getText().toString());
        plan.setIscomplete(false);
        plan.setRepeatDays(tv_repeat_weeks.getText().toString());

        SdcardUtil.creatFile(PlanupApplication.getInstance(), PuConstants.FIRST_FONDER, PuConstants.SECOND_FONDER);
        SdcardUtil.writeIntoSDcard(
                PlanupApplication.getInstance(),
                PuConstants.FIRST_FONDER, PuConstants.SECOND_FONDER,
                plan.getActtime(),
                GsonUtil.getInstance().toJson(plan));

        onBackPressed();
    }

    private boolean checkValues(){
        if(!StringUtil.isEmpty(et_content.getText().toString())){
            return true;
        }else{
            return false;
        }
    }

    private void showDateChooser() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd=null;
        if(select_month>0){
            dpd = DatePickerDialog.newInstance(
                    AddPlanActivity.this,
                    now.get(Calendar.YEAR),
                    select_month-1,
                    select_day
            );
        }else{
            dpd = DatePickerDialog.newInstance(
                    AddPlanActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(false);
        dpd.setMinDate(now);

        dpd.setAccentColor(getResources().getColor(R.color.main_color));

        Calendar[] dates2 = new Calendar[13];
        for (int i = -6; i <= 6; i++) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.WEEK_OF_YEAR, i);
            dates2[i + 6] = date;
        }
        dpd.setHighlightedDays(dates2);
        dpd.setOnDateSetListener(this);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void showTimeChooser() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddPlanActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), 0);
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.enableSeconds(false);
//        tpd.setStartTime();
        if(select_hour>0){
            tpd.setStartTime(select_hour, select_minute);
        }

        tpd.setAccentColor(getResources().getColor(R.color.main_color));
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.setOnTimeSetListener(this);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    private PopupWindow window;
    private TreeMap<Integer,String> weeks=new TreeMap<Integer,String>();
    private void showPopwindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_weeks_chooser, null);

        CheckBox checkBox_one=(CheckBox)view.findViewById(R.id.checkbox_one);
        CheckBox checkBox_two=(CheckBox)view.findViewById(R.id.checkbox_two);
        CheckBox checkBox_three=(CheckBox)view.findViewById(R.id.checkbox_three);
        CheckBox checkBox_four=(CheckBox)view.findViewById(R.id.checkbox_four);
        CheckBox checkBox_five=(CheckBox)view.findViewById(R.id.checkbox_five);
        CheckBox checkBox_six=(CheckBox)view.findViewById(R.id.checkbox_six);
        CheckBox checkBox_seven=(CheckBox)view.findViewById(R.id.checkbox_seven);
        Button btn_cancel=(Button)view.findViewById(R.id.btn_cancel);
        Button btn_sure=(Button)view.findViewById(R.id.btn_sure);

        weeks.clear();

        checkBox_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeks.put(0, DateUtil.WEEKS[0]);
                }else{
                    weeks.remove(0);
                }
            }
        });

        checkBox_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeks.put(1, DateUtil.WEEKS[1]);
                }else{
                    weeks.remove(1);
                }
            }
        });

        checkBox_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeks.put(2, DateUtil.WEEKS[2]);
                }else{
                    weeks.remove(2);
                }
            }
        });
        checkBox_four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeks.put(3, DateUtil.WEEKS[3]);
                }else{
                    weeks.remove(3);
                }
            }
        });
        checkBox_five.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weeks.put(4, DateUtil.WEEKS[4]);
                } else {
                    weeks.remove(4);
                }
            }
        });
        checkBox_six.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeks.put(5, DateUtil.WEEKS[5]);
                }else{
                    weeks.remove(5);
                }
            }
        });
        checkBox_seven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weeks.put(6, DateUtil.WEEKS[6]);
                }else{
                    weeks.remove(6);
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weeks.size() != 0) {
                    tv_repeat_weeks.setText("");
                    Iterator it = weeks.keySet().iterator();

                    while (it.hasNext()) {
                        tv_repeat_weeks.append(weeks.get(it.next()) + " ");
                    }

                    window.dismiss();
                } else {
                    ToastUtil.toastShow(AddPlanActivity.this, "请选择重复的周");
                }
            }
        });

        window = new PopupWindow(view,
                getWindow().getDecorView().getWidth(),
                WindowManager.LayoutParams.WRAP_CONTENT);

        window.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);

        window.showAtLocation(AddPlanActivity.this.findViewById(R.id.layout_create_plan),
                Gravity.CLIP_HORIZONTAL, 0, SystemUtil.dip2px2(appContext, 0));

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }
}
