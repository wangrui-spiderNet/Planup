package com.starnet.jn_wr.planup.util;

/**
 * Created by jn-wr on 2015/12/4.
 */
public class GsonUtil {

    public static com.google.gson.Gson gson;
    public static com.google.gson.Gson getInstance(){
        if(gson==null){
            gson =new com.google.gson.Gson();
        }
        return gson;
    }
}
