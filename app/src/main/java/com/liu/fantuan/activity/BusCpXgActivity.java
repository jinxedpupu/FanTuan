package com.liu.fantuan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liu.fantuan.R;
import com.liu.fantuan.dao.CaipinDao;
import com.liu.fantuan.fragment.BusCaipinFragment;
import com.liu.fantuan.model.Caipininfo;

public class BusCpXgActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText cpnametv,cpjiagetv,cpbeizhutv;
    String cpname, cpjiage, cpbeizhu;
    int cpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_cp_xg);
        cpnametv = findViewById(R.id.cpname);
        cpjiagetv = findViewById(R.id.cpjiage);
        cpbeizhutv =findViewById(R.id.cpbeizhu);
        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        Intent intent = getIntent();
        cpname=intent.getStringExtra("cpname");
        cpjiage=intent.getStringExtra("cpjiage");
        cpbeizhu=intent.getStringExtra("cpbeizhu");
        String id = intent.getStringExtra("cpid");
        cpid=Integer.parseInt(id);

        cpnametv.setText(cpname);
        cpjiagetv.setText(cpjiage);
        cpbeizhutv.setText(cpbeizhu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                startActivity(new Intent(BusCpXgActivity.this, BusCpAddactivity.class));
                finish();
                break;
            case R.id.reset:
                cpnametv.setText("");
                cpjiagetv.setText("");
                cpbeizhutv.setText("");
                break;
            case R.id.register:
                formatCheck();
                break;
        }
    }

    private  void formatCheck() {//修改菜品信息
        CaipinDao caipinDao = new CaipinDao(this);
        cpname = cpnametv.getText().toString();
        cpjiage = cpjiagetv.getText().toString();
        cpbeizhu = cpbeizhutv.getText().toString();
        int cpJiage = Integer.parseInt(cpjiage);
        caipinDao.xiugai(cpname, cpJiage, cpbeizhu, cpid);
        Intent intent = new Intent(BusCpXgActivity.this, BusMainActivity.class);
        startActivity(intent);
        finish();
    }
}