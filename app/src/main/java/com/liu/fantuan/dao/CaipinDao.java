package com.liu.fantuan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liu.fantuan.db.DBOpenHelper;
import com.liu.fantuan.model.Caipininfo;

import java.util.ArrayList;
import java.util.List;

public class CaipinDao {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public CaipinDao(Context context){
        dbOpenHelper=new DBOpenHelper(context);
    }

    public List<Caipininfo> CaipininfoDesc() {
        List<Caipininfo> resultList = new ArrayList<>();
        db = dbOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from caipin", null);
        while (cursor.moveToNext()) {
            Caipininfo caipininfo = new Caipininfo();
            caipininfo.setCpid(cursor.getInt(cursor.getColumnIndex("cpid")));
            caipininfo.setBusid(cursor.getInt(cursor.getColumnIndex("cpid")));
            caipininfo.setCpname(cursor.getString(cursor.getColumnIndex("cpname")));
            caipininfo.setCpjiage(cursor.getInt(cursor.getColumnIndex("cpjiage")));
            caipininfo.setCpbeizhu(cursor.getString(cursor.getColumnIndex("cpbeizhu")));
            caipininfo.setCptupian(cursor.getString(cursor.getColumnIndex("cptupian")));
            resultList.add(caipininfo);
        }
        cursor.close();
        db.close();
        return resultList;
    }

}
