package com.liu.fantuan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liu.fantuan.db.DBOpenHelper;
import com.liu.fantuan.model.Businessinfo;

public class BusinessDao {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public BusinessDao(Context context){
        dbOpenHelper=new DBOpenHelper(context);
    }

    //商家登录
    public boolean login(String buszhanghao,String buspassword){
        db=dbOpenHelper.getReadableDatabase();//初始化
        Cursor cursor = db.rawQuery("select * from business where buszhanghao='" + buszhanghao + "' and buspassword='" + buspassword + "'", null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return  true;
        }
        cursor.close();
        db.close();
        return  false;
    }

    public Businessinfo findUserById(String buszhanghao){
        db=dbOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from business where buszhanghao='" + buszhanghao + "'", null);
        if (cursor.moveToNext()){
            Businessinfo businesdinfo=new Businessinfo();
            businesdinfo.setBusname(cursor.getString(cursor.getColumnIndex("busname")));
            businesdinfo.setBuszhanghao(cursor.getString(cursor.getColumnIndex("buszhanghao")));
            businesdinfo.setBuspassword(cursor.getString(cursor.getColumnIndex("buspassword")));
            businesdinfo.setBusdianhua(cursor.getString(cursor.getColumnIndex("busdianhua")));
            businesdinfo.setBussfz(cursor.getString(cursor.getColumnIndex("bussfz")));
            businesdinfo.setBuspicpath(cursor.getString(cursor.getColumnIndex("buspicpath")));
            return businesdinfo;
        }
        return null;
    }

    /**
     * 商家注册
     * @param Business
     * @return
     */
    public long addBusiness(Businessinfo Business){
        db=dbOpenHelper.getReadableDatabase();
        long insernumb=0;
        ContentValues cv = new ContentValues();//map类型
        cv.put("busid",Business.getBusid());
        cv.put("buszhanghao",Business.getBuszhanghao());
        cv.put("buspassword",Business.getBuspassword());
        cv.put("busname",Business.getBusname());
        cv.put("busdianhua",Business.getBusdianhua());
        cv.put("bussfz",Business.getBussfz());
        insernumb = db.insert("Business",null,cv);
        return insernumb;
    }

}
