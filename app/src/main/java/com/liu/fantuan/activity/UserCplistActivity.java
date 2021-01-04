package com.liu.fantuan.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.liu.fantuan.R;
import com.liu.fantuan.dao.CaipinDao;
import com.liu.fantuan.model.Caipininfo;
import java.util.List;


public class UserCplistActivity extends AppCompatActivity {

    private ImageView buspicpathtv;
    private TextView busnametv,busdianhuatv;
    private ListView listView;
    List<Caipininfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cp_list);
        init();
    }

    //实例化
    private void init() {
        buspicpathtv=findViewById(R.id.buspicpath);
        busnametv=findViewById(R.id.busname);
        busdianhuatv=findViewById(R.id.busdianhua);
        listView=findViewById(R.id.caipinglist);

        Intent intent = getIntent();
        String busname=intent.getStringExtra("busname");
        String buspicpath=intent.getStringExtra("buspicpath");
        String busdianhua=intent.getStringExtra("busdianhua");
        String id = intent.getStringExtra("busid");
        int busid=Integer.parseInt(id);

        busnametv.setText(busname);
        busdianhuatv.setText(busdianhua);
        Bitmap bitmap = BitmapFactory.decodeFile(buspicpath);
        buspicpathtv.setImageBitmap(bitmap);

        CaipinDao caipinDao=new CaipinDao(UserCplistActivity.this);
        list = caipinDao.CaipininfoDesc(busid);
        UserCplistActivity.MyListAdapter myListAdapter=new UserCplistActivity.MyListAdapter(UserCplistActivity.this,list);
        listView.setAdapter(myListAdapter);
    }


    public UserCplistActivity() {
        // Required empty public constructor
    }

    class MyListAdapter extends BaseAdapter {
        List<Caipininfo> CaipininfoList;
        LayoutInflater layoutInflater;
        public MyListAdapter(Context context, List<Caipininfo> dlist){
            layoutInflater=LayoutInflater.from(context);
            CaipininfoList=dlist;
        }

        @Override
        public int getCount() {
            return CaipininfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return CaipininfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView==null) {
                convertView =layoutInflater.inflate(R.layout.list_item_cpxs,null);
                viewHolder.cpnametv=convertView.findViewById(R.id.cpname);
                viewHolder.cptupianview=convertView.findViewById(R.id.cptupian);
                viewHolder.cpjiagetv=convertView.findViewById(R.id.cpjiage);
                viewHolder.cpbeizhutv=convertView.findViewById(R.id.cpbeizhu);

                convertView.setTag(viewHolder);
            }else {
                viewHolder= (UserCplistActivity.ViewHolder) convertView.getTag();
            }
            Caipininfo caipininfo = list.get(position);
            viewHolder.cpnametv.setText(caipininfo.getCpname());
            viewHolder.cpbeizhutv.setText(caipininfo.getCpbeizhu());
            viewHolder.cpjiagetv.setText(caipininfo.getCpjiage() + "");
            Bitmap bitmap = BitmapFactory.decodeFile(caipininfo.getCptupian());
            viewHolder.cptupianview.setImageBitmap(bitmap);
            return convertView;
        }
    }

    class ViewHolder{
        public TextView cpnametv;
        public TextView cpjiagetv;
        public TextView cpbeizhutv;
        public ImageView cptupianview;
    }
}