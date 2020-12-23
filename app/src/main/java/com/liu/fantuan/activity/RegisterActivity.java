package com.liu.fantuan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.fantuan.R;
import com.liu.fantuan.dao.UserDao;
import com.liu.fantuan.model.Userinfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText userid_et,username_et,password_et,telphone_et,address_et;
    UserDao userDao=new UserDao(this);
    Toolbar toolbar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.reset).setOnClickListener(this);
        userid_et=findViewById(R.id.userid);
        password_et=findViewById(R.id.password);
        username_et=findViewById(R.id.username);
        telphone_et=findViewById(R.id.telphone);
        address_et=findViewById(R.id.address);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(R.color.white);
        setSupportActionBar(toolbar);
        findViewById(R.id.goback).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.reset:
                userid_et.setText("");
                password_et.setText("");
                username_et.setText("");
                telphone_et.setText("");
                address_et.setText("");
                break;
            case R.id.register:
                formatCheck();
                break;
        }
    }

    private  void formatCheck() {
        Pattern p = Pattern.compile("^u_[a-zA-Z_0-9]{3,9}");
        Matcher m = p.matcher(userid_et.getText().toString());
        Pattern p_phone = Pattern.compile("^1[3,5,8,7][0-9]{9}");
        Matcher m_phone = p_phone.matcher(telphone_et.getText().toString());
        Userinfo user = userDao.findUserById(userid_et.getText().toString());
        if (userid_et.getText().toString().indexOf("u")<0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else {
            handleRegister(user, m, m_phone);
        }
    }

    public  void handleRegister(Object o,Matcher m,Matcher m_phone){
        if (o != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("该用户名已存在");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m.matches()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("提示");
                builder.setMessage("输入账号格式错误");
                builder.setPositiveButton("确定", null);
                builder.show(); }
        else if (!m_phone.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入电话格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        }else {
            Userinfo user = new Userinfo();
            user.setUserzhanghao(userid_et.getText().toString());
            user.setUserpassword(password_et.getText().toString());
            user.setUsername(username_et.getText().toString());
            user.setUserdianhua(telphone_et.getText().toString());
            user.setUserdizhi(address_et.getText().toString());
            //user.setUser_picpath(imagepath);
            if (userDao.addUser(user)>0){
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                //intent.setClass(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("userzhanghao",user.getUserzhanghao());
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"注册失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }

}