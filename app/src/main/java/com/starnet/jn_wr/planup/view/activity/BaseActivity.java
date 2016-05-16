package com.starnet.jn_wr.planup.view.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.starnet.jn_wr.planup.db.PUDatabaseDao;

public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public Context appContext;

    protected int width;
    protected int height;

    public PUDatabaseDao db;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();

        db=PUDatabaseDao.getInstance(this);
        getDisplay();
    }

    private void getDisplay() {
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }


}
