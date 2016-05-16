package com.starnet.jn_wr.planup.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starnet.jn_wr.planup.PlanupApplication;
import com.starnet.jn_wr.planup.PuConstants;
import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.entity.Plan;
import com.starnet.jn_wr.planup.util.GsonUtil;
import com.starnet.jn_wr.planup.util.SdcardUtil;
import com.starnet.jn_wr.planup.view.adapter.FrontPageAdapter;
import com.starnet.jn_wr.planup.view.widget.xlistview.XListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jn-wr on 2016/2/29.
 */

public class JNHomeFrontPageFragment extends BaseFragment {
    private LayoutInflater mLayoutInflater;
    private View homeView;
    private XListView lvPlan;
    private FrontPageAdapter pageAdapter;
    private ArrayList<Plan> plans;

    private Handler mHandler;

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
        mHandler = new Handler();
        if(homeView==null){
            homeView = mLayoutInflater.inflate(R.layout.main_viewpage_home, null);
            lvPlan =(XListView) homeView.findViewById(R.id.lv_plans);

            plans=new ArrayList<Plan>();

            setData();

            pageAdapter=new FrontPageAdapter(getActivity(),plans);
            lvPlan.setAdapter(pageAdapter);

            addListner();
        }else{
            ViewGroup viewGroup=(ViewGroup)homeView.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(homeView);
            }
        }

        return homeView;
    }

    private void setData(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                File[] files= SdcardUtil.fileExplorer(PlanupApplication.getInstance(), PuConstants.FIRST_FONDER,PuConstants.SECOND_FONDER);
//
//                if(files!=null){
//                    for (int i=0;i<files.length;i++){
//                        File file=files[i];
//                        String plan_str = SdcardUtil.readBufferFile(PlanupApplication.getInstance(),PuConstants.FIRST_FONDER,PuConstants.SECOND_FONDER,file.getName());
//                        Plan plan= GsonUtil.getInstance().fromJson(plan_str,Plan.class);
//                        plans.add(plan);
//                    }
//                }

                plans.addAll(db.getPlans());

                lvPlan.stopRefresh();
                lvPlan.stopLoadMore();
            }
        });
    }

    private void addListner(){
        lvPlan.setPullLoadEnable(false);
        lvPlan.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                plans.clear();
                setData();
            }

            @Override
            public void onLoadMore() {
                setData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

}
