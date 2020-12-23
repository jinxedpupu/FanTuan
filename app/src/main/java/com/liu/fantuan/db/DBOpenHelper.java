package com.liu.fantuan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    final static String DBNAME="express_db.db";
    final static int VERSION=1;
    public DBOpenHelper(@Nullable Context context) {//创建数据库
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        db.execSQL("create table 'user'('userid' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' varchar(20)," +
                "'userzhanghao' varchar(20)," +
                "'userpassword' varchar(20) not null," +
                "'userdianhua' varchar(11)," +
                "'userdizhi' varchar(100)," +
                "'userpicpath' varchar(200))");
        //预存数据
        db.execSQL("insert into user values(1,'张三','u_zs123456','123456','18181145027','四川省成都市','')");

        //创建商家表
        db.execSQL("create table 'business'('busid' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'busname' varchar(20)," +
                "'buszhanghao' varchar(20)," +
                "'buspassword' varchar(20) not null," +
                "'busdianhua' varchar(11)," +
                "'bussfz' varchar(100)," +
                "'buspicpath' varchar(200))");
        //预存数据
        db.execSQL("insert into business values(1,'黄焖鸡米饭','b_hmj123456','123456','18181145027','510683200006111810','')");


        //创建菜品表
        db.execSQL("create table 'caipin'('cpid' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'busid' INTEGER(6)," +
                "'cpbeizhu' varchar(20)," +
                "'cpname' varchar(11)," +
                "'cpprice' INTEGER(6)," +
                "'cptupian' varchar(200))");
        //预存数据
        db.execSQL("insert into caipin values(1,1,'黄焖鸡米饭','黄焖鸡',1,'')");

        //创建订单表
        db.execSQL("create table 'dindan'('ddid' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'ddbh' varchar(20)," +
                "'ddtime' time(20)," +
                "'userid' INTEGER(6)," +
                "'cpid' INTEGER(200))");
        //预存数据
        db.execSQL("insert into dindan values(1,'10001',2020-12-16,1,1)");

        //创建菜品评价表
        db.execSQL("create table 'pingjia'('pjid' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'cpid' INTEGER(6)," +
                "'userid' INTEGER(6)," +
                "'pingjia' varchar(11)," +
                "'ddid' INTEGER(6))");
        //预存数据
        db.execSQL("insert into pingjia values(1,1,1,'美味实惠',1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
