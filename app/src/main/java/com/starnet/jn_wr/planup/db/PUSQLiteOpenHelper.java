package com.starnet.jn_wr.planup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.starnet.jn_wr.planup.PuConstants;

public class PUSQLiteOpenHelper extends SQLiteOpenHelper {

    public PUSQLiteOpenHelper(Context context) {
        super(context, "planup.db", null, PuConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initDatabase(SQLiteDatabase db) {
        String sql = null;

        // 保存用户信息表
        sql = "CREATE TABLE tb_member (" +
                "member_id INTEGER(10) PRIMARY KEY NOT NULL," +
                "member_logo VARCHAR," +
                "member_name VARCHAR(45)," +
                "member_mail VARCHAR(255)," +
                "member_mobile VARCHAR(11), " +
                "member_ct VARCHAR(45)"+
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE tb_plan (plan_id INTEGER,type VARCHAR(255),card_content VARCHAR,acttime INTEGER,ct INTEGER) ";
        db.execSQL(sql);

    }

}
