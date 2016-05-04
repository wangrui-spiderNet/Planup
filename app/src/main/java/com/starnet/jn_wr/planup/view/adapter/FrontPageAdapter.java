package com.starnet.jn_wr.planup.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.entity.Plan;

import java.util.ArrayList;

/**
 * Created by jn-wr on 2016/5/3.
 */
public class FrontPageAdapter extends BaseAdapter {

    private ArrayList<Plan> plans;
    private Context mcontext;

    public FrontPageAdapter( Context mcontext,ArrayList<Plan> plans) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_front_page,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder{

        TextView tvDay;
        TextView tvDate;
        TextView tvTime;
        TextView tvContent;

        public ViewHolder(View view){
            tvContent=(TextView)view.findViewById(R.id.plan_content);
            tvDay=(TextView)view.findViewById(R.id.plan_day);
            tvDate=(TextView)view.findViewById(R.id.plan_day);
            tvTime=(TextView)view.findViewById(R.id.plan_time);
        }
    }
}
