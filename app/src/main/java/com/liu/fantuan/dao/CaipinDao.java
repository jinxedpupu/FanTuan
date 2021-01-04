package com.liu.fantuan.dao;

import android.content.ContentValues;
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

    public List<Caipininfo> CaipininfoDesc(int busid) {
        List<Caipininfo> resultList = new ArrayList<>();
        db = dbOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        /*Cursor cursor = db.rawQuery("select * from caipin", null);*/
        Cursor cursor = db.rawQuery("select * from caipin where busid='" + busid + "'", null);
        while (cursor.moveToNext()) {
            Caipininfo caipininfo = new Caipininfo();
            caipininfo.setCpid(cursor.getInt(cursor.getColumnIndex("cpid")));
            caipininfo.setBusid(cursor.getInt(cursor.getColumnIndex("busid")));
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

    /**
     * 菜品添加
     * @param caipin
     * @return
     */
    public long addcaipin(Caipininfo caipin){
        db=dbOpenHelper.getReadableDatabase();
        long insernumb=0;
        ContentValues cv = new ContentValues();//map类型
        cv.put("busid",caipin.getBusid());
        cv.put("cpname",caipin.getCpname());
        cv.put("cpjiage",caipin.getCpjiage());
        cv.put("cpbeizhu",caipin.getCpbeizhu());
        cv.put("cptupian",caipin.getCptupian());
        insernumb = db.insert("caipin",null,cv);
        return insernumb;
    }


    public Caipininfo findUserById(String cpname){
        db=dbOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from caipin where cpname='" + cpname + "'", null);
        if (cursor.moveToNext()){
            Caipininfo caipininfo=new Caipininfo();
            caipininfo.setCpname(cursor.getString(cursor.getColumnIndex("cpname")));
            caipininfo.setCpjiage(cursor.getInt(cursor.getColumnIndex("cpjiage")));
            caipininfo.setCptupian(cursor.getString(cursor.getColumnIndex("cotupian")));
            caipininfo.setBusid(cursor.getInt(cursor.getColumnIndex("busid")));
            return caipininfo;
        }
        return null;
    }
}
