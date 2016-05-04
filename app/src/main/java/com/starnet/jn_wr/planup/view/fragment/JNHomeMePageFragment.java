package com.starnet.jn_wr.planup.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.view.widget.JNRoundImageView;

/**
 * Created by jn-wr on 2016/2/29.
 */
public class JNHomeMePageFragment extends BaseFragment {
    private View meUserView;
    private JNRoundImageView meUserIvHead;

    private LayoutInflater mLayoutInflater;
    private String bindMobileType1, bindMobileType2;

    public static JNHomeMePageFragment newInstance(Bundle args) {

        JNHomeMePageFragment fragment = null;
        if (fragment == null) {
            fragment = new JNHomeMePageFragment();
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = LayoutInflater.from(getActivity());

        if (meUserView == null) {
            meUserView = mLayoutInflater.inflate(R.layout.main_viewpage_me_user, null);
            initMeView();

        } else {
            ViewGroup viewGroup = (ViewGroup) meUserView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(meUserView);
            }
        }

        return meUserView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initMeView() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }


}
