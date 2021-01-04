package com.liu.fantuan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.liu.fantuan.R;
import com.liu.fantuan.dao.CaipinDao;
import com.liu.fantuan.fragment.BusCaipinFragment;
import com.liu.fantuan.model.Caipininfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusCpAddactivity extends AppCompatActivity implements View.OnClickListener{

    EditText busid_et,cpname_et,cpjiage_et,cpbeizhu_et;
    //UserDao userDao=new UserDao(this);
    CaipinDao caipinDao = new CaipinDao(this);
    Toolbar toolbar;

    public static final int TAKE_CAMERA = 101;
    private Uri imageUri;//原图保存地址
    ImageView imageView;
    String imagdate;
    String imagepath="";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_cp_add);

        findViewById(R.id.reset).setOnClickListener(this);
        busid_et=findViewById(R.id.busid);
        cpname_et=findViewById(R.id.cpname);
        cpjiage_et=findViewById(R.id.cpjiage);
        cpbeizhu_et=findViewById(R.id.cpbeizhu);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(R.color.white);
        setSupportActionBar(toolbar);
        findViewById(R.id.goback).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);
        }
        invitview();

        findViewById(R.id.takephotobt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imagePath = new File(getFilesDir(), "myimages");//gn file_paths.xml文件中的files-path标签中的path值一致
                if (!imagePath.exists()) {
                    imagePath.mkdirs();
                }
                imagdate = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
                File newFile = new File(imagePath, imagdate + ".jpg");
                imagepath=newFile.getPath();
                System.out.println("newFile.getPath()="+newFile.getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //大于等于版本24（7.0）的场合
                    imageUri = FileProvider.getUriForFile(BusCpAddactivity.this, "com.liu.fantuan.activity.fileprovider", newFile);//此处的outputImage指定的路径要在file_paths.xml文件对应配置path指定路径的子路径
                } else {
                    //小于android 版本7.0（24）的场合
                    imageUri = Uri.fromFile(newFile);
                }
                //启动相机程序
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_CAMERA);
            }
        });
    }
    private void invitview() {
        imageView = findViewById(R.id.image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                startActivity(new Intent(BusCpAddactivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.reset:
                busid_et.setText("");
                cpname_et.setText("");
                cpjiage_et.setText("");
                cpbeizhu_et.setText("");
                break;
            case R.id.register:
                formatCheck();
                break;
        }
    }

    public void formatCheck(){
        Caipininfo caipininfo = new Caipininfo();
        caipininfo.setBusid(Integer.parseInt(busid_et.getText().toString()));
        caipininfo.setCpname(cpname_et.getText().toString());
        caipininfo.setCpjiage(Integer.parseInt(cpjiage_et.getText().toString()));
        caipininfo.setCpbeizhu(cpbeizhu_et.getText().toString());
        caipininfo.setCptupian(imagepath);
        if (caipinDao.addcaipin(caipininfo)>0){
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(BusCpAddactivity.this,BusCaipinFragment.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this,"添加失败！",Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }
}