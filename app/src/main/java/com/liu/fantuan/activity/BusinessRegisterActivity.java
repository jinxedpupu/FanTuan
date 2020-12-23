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
import com.liu.fantuan.dao.BusinessDao;
import com.liu.fantuan.model.Businessinfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText distributorid_et,distributorname_et,distributorpassword_et,distributortelphone_et,distributoridcar_et;
    BusinessDao businessDao = new BusinessDao(this);
    Toolbar toolbar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);
        distributorid_et = findViewById(R.id.distributorid);
        distributorname_et = findViewById(R.id.distributorname);
        distributorpassword_et = findViewById(R.id.distributorpassword);
        distributortelphone_et = findViewById(R.id.distributortelphone);
        distributoridcar_et = findViewById(R.id.distributoridcar);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(R.color.white);
        setSupportActionBar(toolbar);
        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.goback).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                startActivity(new Intent(BusinessRegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.reset:
                distributorid_et.setText("");
                distributorpassword_et.setText("");
                distributorname_et.setText("");
                distributortelphone_et.setText("");
                distributoridcar_et.setText("");
                break;
            case R.id.register:
                formatCheck();
                break;
        }
    }


    //设置格式
    private  void formatCheck() {
        Pattern p = Pattern.compile("^b_[a-zA-Z_0-9]{3,9}");
        Matcher m = p.matcher(distributorid_et.getText().toString());
        Pattern p_phone = Pattern.compile("^1[3,5,8,7][0-9]{9}");
        Matcher m_phone = p_phone.matcher(distributortelphone_et.getText().toString());
        Pattern p_idcar = Pattern.compile("(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)");
        Matcher m_idcar = p_idcar.matcher(distributoridcar_et.getText().toString());
        Businessinfo businessinfo = businessDao.findUserById(distributorid_et.getText().toString());
        if (distributorid_et.getText().toString().indexOf("b")<0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else {
            handleRegister(businessinfo, m, m_phone,m_idcar);
        }
    }

    //验证格式
    public  void handleRegister(Object o,Matcher m,Matcher m_phone,Matcher m_idcar){
        if (o != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("该用配送员已存在");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入账号格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        } else if (!m_phone.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入电话格式错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        }else if(!m_idcar.matches()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegisterActivity.this);
            builder.setTitle("提示");
            builder.setMessage("输入身份证号码错误");
            builder.setPositiveButton("确定", null);
            builder.show();
        }else {
            Businessinfo businessinfo = new Businessinfo();
            businessinfo.setBuszhanghao(distributorid_et.getText().toString());
            businessinfo.setBuspassword(distributorpassword_et.getText().toString());
            businessinfo.setBusname(distributorname_et.getText().toString());
            businessinfo.setBusdianhua(distributortelphone_et.getText().toString());
            businessinfo.setBussfz(distributoridcar_et.getText().toString());
            //businessDao.setDistributor_picPath(imagepath);
            if (businessDao.addBusiness(businessinfo)>0){
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BusinessRegisterActivity.this,LoginActivity.class);
                intent.putExtra("buszhanghao",businessinfo.getBuszhanghao());
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"注册失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }

}