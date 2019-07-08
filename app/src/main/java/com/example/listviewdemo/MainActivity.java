package com.example.listviewdemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mAppList = new ArrayList<>();
    private DragDelListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i <20 ; i++) {
          mAppList.add("listview"+i);
        }
        listView.setAdapter(new MyAdapter());
    }

    private void initView() {
        listView = (DragDelListView) findViewById(R.id.listView);
    }
    /**
     * 自定义ListView适配器
     */
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder=null;
            View menuView=null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.swipecontent, null);
                menuView = View.inflate(getApplicationContext(),
                        R.layout.swipemenu, null);
                convertView = new DragDelItem(convertView,menuView);
                holder=new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
            holder.tv_name.setText(mAppList.get(position));
            final int pos = position;
            holder.tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Toast.makeText(MainActivity.this, "删除:"+position, Toast.LENGTH_SHORT).show();
                    mAppList.remove(pos);
                    notifyDataSetChanged();

                }
            });
            return convertView;
        }
        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;
            TextView tv_del;
            RelativeLayout relativeLayout;
            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_del=(TextView)view.findViewById(R.id.tv_del);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_layout);
                //改变relativeLayout宽度
                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                relativeLayout.setMinimumWidth(width-60);
                view.setTag(this);
            }
        }
    }
}
