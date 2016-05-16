package com.starnet.jn_wr.planup.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starnet.jn_wr.planup.PlanupApplication;
import com.starnet.jn_wr.planup.db.PUDatabaseDao;


/**
 * Created by jn-wr on 2015/10/13.
 */
public class BaseFragment extends Fragment implements View.OnClickListener{

    public Context context;
    protected PUDatabaseDao db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        db = PUDatabaseDao.getInstance(PlanupApplication.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
