package com.starnet.jn_wr.planup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starnet.jn_wr.planup.PuConstants;
import com.starnet.jn_wr.planup.entity.Plan;

import java.util.ArrayList;

public class PUDatabaseDao {

    private static PUSQLiteOpenHelper mHelper;
    private static SQLiteDatabase mDatabase;
    private static Cursor mCursor;
    private static PUDatabaseDao userDao;

    public static PUDatabaseDao getInstance(Context context) {
        if (userDao == null) {
            return userDao = new PUDatabaseDao(context);
        }
        return userDao;
    }

    public PUDatabaseDao(Context context) {
        mHelper = new PUSQLiteOpenHelper(context.getApplicationContext());
    }

    /**
     * 保存页面
     * @param plan
     */
    public static void savePlan(Plan plan){
        mDatabase = mHelper.getWritableDatabase();
        String planId = plan.getId();//plan_id INTEGER,type VARCHAR(255),card_content VARCHAR,ct INTEGER
        ContentValues cv = new ContentValues();
        cv.put("plan_id", planId);
        cv.put("type", plan.getType());
        cv.put("card_content", plan.getContent());
        cv.put("ct", plan.getCt());
        cv.put("acttime",plan.getActtime());

        mDatabase.insert(PuConstants.TB_PLAN, null, cv);
        close();
    }

    /**
     * 获取数据库计划
     * @return
     */
    public static ArrayList<Plan> getPlans(){
        mDatabase = mHelper.getWritableDatabase();
        ArrayList<Plan> plans=new ArrayList<Plan>();
        Plan plan;

        mCursor = mDatabase.rawQuery("SELECT * FROM " + PuConstants.TB_PLAN, new String[]{});

        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                plan=new Plan();

                plan.setId(mCursor.getString(mCursor.getColumnIndex("plan_id")));
                plan.setCt(mCursor.getString(mCursor.getColumnIndex("ct")));
                plan.setContent(mCursor.getString(mCursor.getColumnIndex("card_content")));
                plan.setActtime(mCursor.getString(mCursor.getColumnIndex("acttime")));
                plan.setType(mCursor.getInt(mCursor.getColumnIndex("type")));
                plans.add(plan);
            } while (mCursor.moveToNext());
        }
        close();
        return plans;
    }

    /**
     * 更新计划
     */
    public static void upDatePlan(){
        mDatabase = mHelper.getWritableDatabase();
    }

    /**
     * 删除计划
     * @param id
     */
    public static void deletePlan(String id){
        mDatabase = mHelper.getWritableDatabase();
        mDatabase.execSQL("delete from "+PuConstants.TB_PLAN+" where plan_id=?", new String[]{id, });
        close();
    }

    /**
     * 关闭连接
     */
    private static void close(){
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
    }

}
