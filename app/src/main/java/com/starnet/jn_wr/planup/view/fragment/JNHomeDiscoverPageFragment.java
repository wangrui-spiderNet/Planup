package com.starnet.jn_wr.planup.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starnet.jn_wr.planup.R;

/**
 * Created by jn-wr on 2016/2/29.
 */
public class JNHomeDiscoverPageFragment extends BaseFragment {

    private View discoverView;
    private LayoutInflater mLayoutInflater;

    public static JNHomeDiscoverPageFragment newInstance(Bundle args) {
        JNHomeDiscoverPageFragment fragment = null;
        if (fragment == null) {
            fragment = new JNHomeDiscoverPageFragment();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if(discoverView==null){
            mLayoutInflater = LayoutInflater.from(getActivity());
            discoverView = mLayoutInflater.inflate(R.layout.main_viewpage_discover, null);

        }else{
            ViewGroup viewGroup=(ViewGroup)discoverView.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(discoverView);
            }
        }

        return discoverView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
