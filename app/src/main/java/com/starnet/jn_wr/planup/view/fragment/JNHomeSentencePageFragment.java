package com.starnet.jn_wr.planup.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starnet.jn_wr.planup.R;

/**
 * Created by jn-wr on 2016/2/29.
 */
public class JNHomeSentencePageFragment extends BaseFragment {

    private View adviseView;

    private LayoutInflater mLayoutInflater;

    public static JNHomeSentencePageFragment newInstance(Bundle args) {
        JNHomeSentencePageFragment fragment = null;
        if (fragment == null) {
            fragment = new JNHomeSentencePageFragment();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = LayoutInflater.from(getActivity());

        if (adviseView == null) {
            initViewAll();
        } else {

            ViewGroup parent = (ViewGroup) adviseView.getParent();
            if (parent != null) {
                parent.removeView(adviseView);
            }
        }

        return adviseView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViewAll() {
        adviseView = mLayoutInflater.inflate(R.layout.main_viewpage_advise, null);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
