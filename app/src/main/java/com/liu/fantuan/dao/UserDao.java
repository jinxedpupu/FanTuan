package com.liu.fantuan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liu.fantuan.db.DBOpenHelper;
import com.liu.fantuan.model.Userinfo;

public class UserDao {

    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    public UserDao(Context context){
        dbOpenHelper=new DBOpenHelper(context);
    }
    //用户登录
    public boolean login(String userzhanghao,String userpassword){
        db=dbOpenHelper.getReadableDatabase();//c初始化
        Cursor cursor = db.rawQuery("select * from user where userzhanghao='" + userzhanghao + "' and userpassword='" + userpassword + "'", null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return  true;
        }
        cursor.close();
        db.close();
        return  false;
    }

    public Userinfo findUserById(String userzhanghao){
        db=dbOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from user where userzhanghao='" + userzhanghao + "'", null);
        if (cursor.moveToNext()){
            Userinfo userinfo = new Userinfo();
            userinfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userinfo.setUserzhanghao(cursor.getString(cursor.getColumnIndex("userzhanghao")));
            userinfo.setUserpassword(cursor.getString(cursor.getColumnIndex("userpassword")));
            userinfo.setUserdianhua(cursor.getString(cursor.getColumnIndex("userdianhua")));
            userinfo.setUserdizhi(cursor.getString(cursor.getColumnIndex("userdizhi")));
            userinfo.setUserpicpath(cursor.getString(cursor.getColumnIndex("userpicpath")));
            return userinfo;
        }
        return null;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    public long addUser(Userinfo user){
        db=dbOpenHelper.getReadableDatabase();
        long insernumb=0;
        ContentValues cv = new ContentValues();//map类型
        /*cv.put("userid",user.getUserid());*/
        cv.put("userzhanghao",user.getUserzhanghao());
        cv.put("userpassword",user.getUserpassword());
        cv.put("username",user.getUsername());
        cv.put("userdianhua",user.getUserdianhua());
        cv.put("userdizhi",user.getUserdizhi());
        cv.put("userpicpath",user.getUserpicpath());
        insernumb = db.insert("user",null,cv);
        return insernumb;
    }
}
