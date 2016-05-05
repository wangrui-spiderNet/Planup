package com.starnet.jn_wr.planup.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.jn_wr.planup.PuConstants;
import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.entity.Plan;
import com.starnet.jn_wr.planup.util.DateUtil;
import com.starnet.jn_wr.planup.util.SdcardUtil;
import com.starnet.jn_wr.planup.view.widget.swipe.SimpleSwipeListener;
import com.starnet.jn_wr.planup.view.widget.swipe.SwipeLayout;
import com.starnet.jn_wr.planup.view.widget.swipe.adapter.BaseSwipeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jn-wr on 2016/5/3.
 */
public class FrontPageAdapter extends BaseSwipeAdapter {

    private ArrayList<Plan> plans;
    private Context mcontext;

    public FrontPageAdapter(Context mcontext, ArrayList<Plan> plans) {
        this.plans = plans;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return plans.size();
    }

    @Override
    public Object getItem(int position) {
        return plans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.item_front_page, parent, false);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        // 当隐藏的删除menu被打开的时候的回调函数
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                Toast.makeText(mcontext, "Open", Toast.LENGTH_SHORT).show();
            }
        });
        // 双击的回调函数
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout,
                                      boolean surface) {
//                Toast.makeText(mcontext, "DoubleClick",
//                        Toast.LENGTH_SHORT).show();
            }
        });
        // 添加删除布局的点击事件
        v.findViewById(R.id.ll_menu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Toast.makeText(mcontext, "delete", Toast.LENGTH_SHORT).show();
                // 点击完成之后，关闭删除menu
                swipeLayout.close();
                SdcardUtil.deleteFoder(mcontext, PuConstants.FIRST_FONDER, PuConstants.SECOND_FONDER, plans.get(position).getActtime());
                plans.remove(position);
                notifyDataSetChanged();
            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView tvDay;
        TextView tvDate;
        TextView tvTime;
        TextView tvContent;
        tvContent = (TextView) convertView.findViewById(R.id.plan_content);
        tvDay = (TextView) convertView.findViewById(R.id.plan_day);
        tvDate = (TextView) convertView.findViewById(R.id.plan_date);
        tvTime = (TextView) convertView.findViewById(R.id.plan_time);

        String time = DateUtil.transferLongToDate("yyyy-MM-dd hh:mm", Long.parseLong(plans.get(position).getActtime()));
        tvDay.setText(time.substring(0, 10));
        tvTime.setText(time.substring(11, time.length()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(plans.get(position).getActtime()));
        tvDate.setText(DateUtil.WEEKS[calendar.get(Calendar.DAY_OF_WEEK)]);
        tvContent.setText(plans.get(position).getContent());
    }

}
