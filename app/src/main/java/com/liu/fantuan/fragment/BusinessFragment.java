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
import com.liu.fantuan.dao.BusinessDao;
import com.liu.fantuan.dao.CaipinDao;
import com.liu.fantuan.model.Businessinfo;
import com.liu.fantuan.model.Caipininfo;

import java.util.List;


public class BusinessFragment extends Fragment {

    ListView listView;
    List<Businessinfo> list;
    public BusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_business, container, false);
        listView=view.findViewById(R.id.paihanglist);

        BusinessDao businessDao=new BusinessDao(getContext());
        list = businessDao.BusinessinfoDesc();

        //System.out.println("list.size()="+list.size());
        BusinessFragment.MyListAdapter myListAdapter=new BusinessFragment.MyListAdapter(getContext(),list);
        listView.setAdapter(myListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Businessinfo businessinfo=list.get(position);
                Intent intent=new Intent();
                /*intent.putExtra("ispsy","2");*/
                intent.putExtra("busname",businessinfo.getBusname()+"");
                intent.putExtra("buspicpath",businessinfo.getBuspicpath()+"");
                //Log.i("data",caipininfo.getDistributor_idcar()+businessinfo.getDistributor_tel()+"");
                //intent.setClass(getContext(), PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    class MyListAdapter extends BaseAdapter {
        List<Businessinfo> BusinessinfoList;
        LayoutInflater layoutInflater;
        public MyListAdapter(Context context, List<Businessinfo> dlist){
            layoutInflater=LayoutInflater.from(context);
            BusinessinfoList=dlist;
        }

        @Override
        public int getCount() {
            return BusinessinfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return BusinessinfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView==null) {
                convertView =layoutInflater.inflate(R.layout.list_item_bus,null);
                viewHolder.busnametv=convertView.findViewById(R.id.busname);
                viewHolder.buspicpathview=convertView.findViewById(R.id.buspicpath);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (BusinessFragment.ViewHolder) convertView.getTag();
            }
           /* int pos =position+1;
            viewHolder.mingcitv.setText(pos+"");
            Bitmap bitmap = BitmapFactory.decodeFile(distributorList.get(position).getDistributor_picPath());
            viewHolder.psyimageview.setImageBitmap(bitmap);
            viewHolder.psynametv.setText(distributorList.get(position).getDistributor_name());
            viewHolder.psyjdcstv.setText(distributorList.get(position).getDistributor_singularnum()+"");
            return convertView;*/
            Businessinfo businessinfo = list.get(position);
            viewHolder.busnametv.setText(businessinfo.getBusname());
            Bitmap bitmap = BitmapFactory.decodeFile(businessinfo.getBuspicpath());
            viewHolder.buspicpathview.setImageBitmap(bitmap);
            return convertView;
        }
    }

    class ViewHolder{
        public TextView busnametv;
        public ImageView buspicpathview;
    }
}