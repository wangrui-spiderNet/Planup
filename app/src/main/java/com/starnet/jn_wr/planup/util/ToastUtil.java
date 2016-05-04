package com.starnet.jn_wr.planup.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.starnet.jn_wr.planup.PlanupApplication;

/**
 * Created by jn-wr on 2015/5/19.
 */
public class ToastUtil {

    public static void toastShow(Context mContext,String msg){
        Toast toast= Toast.makeText(mContext.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }
}
