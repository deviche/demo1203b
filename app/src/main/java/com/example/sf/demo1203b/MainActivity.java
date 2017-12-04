package com.example.sf.demo1203b;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 原来博客地址 ：http://xiaoniaojun.cn/2017/05/08/使用RecyclerView实现底部上拉刷新.html
 */

public class MainActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> datas;
    private ArrayList<String> addDatas;
    private MyAdapter adapter;
    private  int total;
    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager  mLayoutmanager;
    private int mScrollTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initData();
        recyclerView = findViewById(R.id.rv);
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutmanager);

        adapter = new MyAdapter(context,datas);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(context,position+"ddd",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                   //回调滑动事件
                mScrollTotal += dy;
                if (dy > 0) {
                    visibleItemCount = mLayoutmanager.getChildCount();
                    totalItemCount = mLayoutmanager.getItemCount();
                    pastVisibleItems = mLayoutmanager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            adapter.more.setVisibility(View.VISIBLE);
                            // 滚动到列表底部
                            loading = false;
                            // 产生回调
                            onScrollBottom();
                        }
                    }
                }

            }
        });
    }

    private void onScrollBottom() {
        Toast.makeText(context,"正在刷新中。。。。",Toast.LENGTH_SHORT).show();
        Handler  handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addDatas = new ArrayList<>();
                for (int i = 0; i <20 ; i++) {

                    addDatas.add("我是刷新添加的第"+total+"行数据");
                    total++;
                }
                adapter.addDatas(addDatas);
                adapter.notifyDataSetChanged();
                loading = true;
                if (adapter.getItemCount()>100){
                    Toast.makeText(context,"没有更多数据了。。。。",Toast.LENGTH_SHORT).show();
                    loading = false;
                    adapter.more.setVisibility(View.INVISIBLE);
                }
            }
        },300);
        adapter.more.setVisibility(View.VISIBLE);

    }

    private void initData() {
        if (datas== null)
            datas = new ArrayList<>();

        for (int i = 0; i <30 ; i++) {
            datas.add("我是第"+i+"行数据");
        }
        total =datas.size();
    }

}
