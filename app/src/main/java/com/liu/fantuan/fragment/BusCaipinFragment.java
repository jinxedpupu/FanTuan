package com.liu.fantuan.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.liu.fantuan.R;
import com.liu.fantuan.activity.BusCpAddactivity;
import com.liu.fantuan.dao.CaipinDao;
import com.liu.fantuan.model.Caipininfo;
import java.util.List;


public class BusCaipinFragment extends Fragment {
    ListView listView;
    List<Caipininfo> list;
    public BusCaipinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_bus_caipin, container, false);
        listView=view.findViewById(R.id.paihanglist);

        CaipinDao caipinDao=new CaipinDao(getContext());
        list = caipinDao.CaipininfoDesc();

        view.findViewById(R.id.cpadd).setOnClickListener(new View.OnClickListener() {//添加菜品
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BusCpAddactivity.class));
            }
        });

        //System.out.println("list.size()="+list.size());
        BusCaipinFragment.MyListAdapter myListAdapter=new BusCaipinFragment.MyListAdapter(getContext(),list);
        listView.setAdapter(myListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Caipininfo caipininfo=list.get(position);
                Intent intent=new Intent();
                /*intent.putExtra("ispsy","2");*/
                intent.putExtra("cpname",caipininfo.getCpname()+"");
                intent.putExtra("cpjiage",caipininfo.getCpjiage()+"");
                intent.putExtra("cpbeizhu",caipininfo.getCpbeizhu()+"");
                intent.putExtra("cptupian",caipininfo.getCptupian()+"");
                //Log.i("data",caipininfo.getDistributor_idcar()+businessinfo.getDistributor_tel()+"");
                //intent.setClass(getContext(), PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        return view;
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
            BusCaipinFragment.ViewHolder viewHolder = new BusCaipinFragment.ViewHolder();
            if (convertView==null) {
                convertView =layoutInflater.inflate(R.layout.list_item_cpadd,null);
                viewHolder.cpnametv=convertView.findViewById(R.id.cpname);
                viewHolder.cptupianview=convertView.findViewById(R.id.cptupian);
                viewHolder.cpjiagetv=convertView.findViewById(R.id.cpjiage);
                viewHolder.cpbeizhu=convertView.findViewById(R.id.cpbeizhu);
                viewHolder.cptupianview=convertView.findViewById(R.id.cptupian);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (BusCaipinFragment.ViewHolder) convertView.getTag();
            }
           /* int pos =position+1;
            viewHolder.mingcitv.setText(pos+"");
            Bitmap bitmap = BitmapFactory.decodeFile(distributorList.get(position).getDistributor_picPath());
            viewHolder.psyimageview.setImageBitmap(bitmap);
            viewHolder.psynametv.setText(distributorList.get(position).getDistributor_name());
            viewHolder.psyjdcstv.setText(distributorList.get(position).getDistributor_singularnum()+"");
            return convertView;*/
            Caipininfo caipininfo = list.get(position);
            viewHolder.cpnametv.setText(caipininfo.getCpname());
            viewHolder.cpbeizhu.setText(caipininfo.getCpbeizhu());
            viewHolder.cpjiagetv.setText(caipininfo.getCpjiage() + "");
            Bitmap bitmap = BitmapFactory.decodeFile(caipininfo.getCptupian());
            viewHolder.cptupianview.setImageBitmap(bitmap);
            return convertView;
        }
    }

    class ViewHolder{
        public TextView cpnametv;
        public TextView cpjiagetv;
        public TextView cpbeizhu;
        public ImageView cptupianview;
    }


}