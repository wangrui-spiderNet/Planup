package com.starnet.jn_wr.planup.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.entity.Plan;
import com.starnet.jn_wr.planup.view.adapter.FrontPageAdapter;

import java.util.ArrayList;

/**
 * Created by jn-wr on 2016/2/29.
 */

public class JNHomeFrontPageFragment extends BaseFragment {
    private LayoutInflater mLayoutInflater;
    private View homeView;
    private ListView lvPlan;
    private FrontPageAdapter pageAdapter;
    private ArrayList<Plan> plans;

    public static JNHomeFrontPageFragment newInstance(Bundle args) {

        JNHomeFrontPageFragment fragment = null;
        if (fragment == null) {
            fragment = new JNHomeFrontPageFragment();
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = getLayoutInflater(savedInstanceState);

        if(homeView==null){
            homeView = mLayoutInflater.inflate(R.layout.main_viewpage_home, null);
            lvPlan =(ListView) homeView.findViewById(R.id.lv_plans);

            plans=new ArrayList<Plan>();

            for (int i=0;i<10;i++){
                Plan plan=new Plan();
                plans.add(plan);
            }

            pageAdapter=new FrontPageAdapter(getActivity(),plans);
            lvPlan.setAdapter(pageAdapter);
        }else{
            ViewGroup viewGroup=(ViewGroup)homeView.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(homeView);
            }
        }

        return homeView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

}
