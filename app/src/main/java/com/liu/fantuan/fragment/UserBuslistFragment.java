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
import android.widget.Toast;
import com.liu.fantuan.R;
import com.liu.fantuan.activity.UserCplistActivity;
import com.liu.fantuan.dao.BusinessDao;
import com.liu.fantuan.model.Businessinfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserBuslistFragment extends Fragment {

    Banner banner;//banner组件
    List mlist;//图片资源
    List<String> mlist1;//轮播标题

    ListView listView;
    List<Businessinfo> list;
    public UserBuslistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_bus_list, container, false);
        listView=view.findViewById(R.id.paihanglist);

        BusinessDao businessDao=new BusinessDao(getContext());
        list = businessDao.BusinessinfoDesc();

        //System.out.println("list.size()="+list.size());
        UserBuslistFragment.MyListAdapter myListAdapter=new UserBuslistFragment.MyListAdapter(getContext(),list);
        listView.setAdapter(myListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Businessinfo businessinfo=list.get(position);
                Intent intent=new Intent();
                intent.putExtra("busname",businessinfo.getBusname()+"");
                intent.putExtra("buspicpath",businessinfo.getBuspicpath()+"");
                intent.putExtra("busdianhua",businessinfo.getBusdianhua());
                intent.putExtra("busid",String.valueOf(businessinfo.getBusid()));
                intent.setClass(Objects.requireNonNull(getContext()), UserCplistActivity.class);
                startActivity(intent);
            }
        });

        mlist = new ArrayList<>();
        mlist.add(R.mipmap.lunbo1);
        mlist.add(R.mipmap.lunbo2);
        mlist.add(R.mipmap.lunbo3);
        mlist1 = new ArrayList<>();
        mlist1.add("");
        mlist1.add("");
        mlist1.add("");
        banner = view.findViewById(R.id.main_banner);
        banner.setImageLoader(new com.liu.fantuan.util.GiledImageLoader());   //设置图片加载器
        banner.setImages(mlist);//设置图片源
        banner.setBannerTitles(mlist1);//设置标题源
        banner.setDelayTime(2000);//设置轮播事件，单位毫秒
        banner.setBannerAnimation(Transformer.Stack);
        banner.setOnBannerListener(new OnBannerListener() {
            public void OnBannerClick(int position) {
                Toast.makeText(getActivity(), "点击了轮播第" + position + "个图片" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
        banner.start();//开始轮播，一定要调用此方法。

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
                viewHolder= (UserBuslistFragment.ViewHolder) convertView.getTag();
            }
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