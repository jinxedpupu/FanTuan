package com.liu.fantuan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.fantuan.R;
import com.liu.fantuan.dao.BusinessDao;
import com.liu.fantuan.dao.UserDao;
import com.liu.fantuan.model.Businessinfo;
import com.liu.fantuan.model.Userinfo;

public class LoginActivity extends AppCompatActivity {
    private EditText username_et, password_et, distributorname_et, distributorpassword_et;
    Button login_bt;
    TextView regise_tv;
    SharedPreferences sp = null;
    SharedPreferences.Editor editor = null;
    UserDao userDao = new UserDao(this);
    BusinessDao businessDao = new BusinessDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_et = findViewById(R.id.username);
        password_et = findViewById(R.id.password);
        login_bt = findViewById(R.id.login);
        regise_tv = findViewById(R.id.register);
        Intent intent = this.getIntent();
        String userid = intent.getStringExtra("userId");
        username_et.setText(userid);
        sp = getSharedPreferences("userdata", Context.MODE_PRIVATE);//他是要创建一个xml：userdata.xml
        editor = sp.edit();
        editor.clear();

        /*
         * 用户登录-商家登录
         * */
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_et.getText().toString();
                String userpassword = password_et.getText().toString();
                if (username == null || username.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
                } else if (userpassword == null || userpassword.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                }
                if (username.startsWith("u")) {
                    UserDao userDao = new UserDao(LoginActivity.this);
                    Userinfo userinfo = userDao.findUserById(username);
                    if (userinfo == null) {
                        Toast.makeText(LoginActivity.this, "该用户名不存在", Toast.LENGTH_LONG).show();
                    } else if (userinfo != null) {
                        if (userDao.login(username, userpassword)) {
                            editor.putString("userId", username);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            //弹窗AlertDialog.Builder
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("登录提示信息");
                            builder.setMessage("密码错误，登陆失败！");
                            builder.setPositiveButton("确定", null);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();

                            Toast.makeText(LoginActivity.this, "用户名错误，登录失败", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (username.startsWith("b")) {
                    BusinessDao businessDao = new BusinessDao(LoginActivity.this);
                    Businessinfo businessinfo = businessDao.findUserById(username);
                    if (businessinfo == null) {
                        Toast.makeText(LoginActivity.this, "该商家用户名不存在", Toast.LENGTH_LONG).show();
                    } else if (businessinfo != null) {
                        if (businessDao.login(username, userpassword)) {
                            editor.putString("userId", username);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            //弹窗AlertDialog.Builder
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("登录提示信息");
                            builder.setMessage("密码错误，登陆失败！");
                            builder.setPositiveButton("确定", null);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                            Toast.makeText(LoginActivity.this, "用户名错误，登录失败", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        /*
         * 注册
         * */
        regise_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                //AlertDialog alertDialog = builder.create();
                builder.setTitle("请选择用户类型");
                String[] types = new String[]{"普通用户","商家用户"};
                builder.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            //页面跳转Intent
                            Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                            startActivity(intent);
                            finish();//关掉上一层界面，没有则是覆盖上一层界面
                        }else if(which==1){
                            Intent intent =new Intent(LoginActivity.this,BusinessRegisterActivity.class);
                            startActivity(intent);
                            finish();//关掉上一层界面，没有则是覆盖上一层界面
                        }
                    }
                });
                builder.show();
            }
        });
    }
}
