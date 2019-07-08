package com.example.listviewdemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SideslipListView mSideslipListView;

    /**
     * 初始化数据
     */
    private ArrayList<String> mDataList = new ArrayList<String>() {
        {
            for (int i = 0; i < 50; i++) {
                add("ListView item  " + i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSideslipListView = (SideslipListView) findViewById(R.id.sideslipListView);
        mSideslipListView.setAdapter(new CustomAdapter());//设置适配器
        //设置item点击事件
        mSideslipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSideslipListView.isAllowItemClick()) {
                    Log.i(TAG, mDataList.get(position) + "被点击了");
                    Toast.makeText(MainActivity.this, mDataList.get(position) + "被点击了",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //设置item长按事件
        mSideslipListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSideslipListView.isAllowItemClick()) {
                    Log.i(TAG, mDataList.get(position) + "被长按了");
                    Toast.makeText(MainActivity.this, mDataList.get(position) + "被长按了",
                            Toast.LENGTH_SHORT).show();
                    return true;//返回true表示本次事件被消耗了，若返回
                }
                return false;
            }
        });
    }

    /**
     * 自定义ListView适配器
     */
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
                viewHolder.txtv_delete = (TextView) convertView.findViewById(R.id.txtv_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(mDataList.get(position));
            final int pos = position;
            viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, pos + "被删除了",
                            Toast.LENGTH_SHORT).show();
                    mDataList.remove(pos);
                    notifyDataSetChanged();
                    mSideslipListView.turnNormal();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        public TextView textView;
        public TextView txtv_delete;
    }

}
