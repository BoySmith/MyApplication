package com.zyb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zyb.myapplication.base.CommonAdapter;
import com.zyb.myapplication.base.MultiItemCommonAdapter;
import com.zyb.myapplication.base.MultiItemTypeSupport;
import com.zyb.myapplication.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MultiGridRecycleAdapter adapter;
    private List<String> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycle_view);
        recyclerView.setLayoutManager(layoutManager);

        initData();
        adapter = new MultiGridRecycleAdapter(this, dataList);

//        recyclerView.setAdapter(adapter);

        /*
        * 下面这两个方法 原理就是封装 ViewHolder、Adapter，利用抽象方法、接口等 把onBindView 等方法提取出来，让用户来实现
        * 好处是不需要用户自己创建 Adapter 和 ViewHolder，如果应用中许多地方用到 RecyclerView，此方法可以节省很多时间和代码量
        * 坏处应该是不够灵活，如果用户想在 Adapter的其他 public方法中搞些事情，就需要单独写一个类继承自 CommonAdapter，并用接口的方式回调出去
        * 或者重新写个Adapter 继承自 RecyclerView.Adapter 而不是 CommonAdapter。
        * */

        //使用 CommonAdapter，传入 layout id 和data，好吃是不需要自己创建一个Adapter类 和 ViewHolder，不需复写 onCreateViewHolder等大量重复代码
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_picture_up, dataList) {
            @Override
            public void convert(ViewHolder holder, final String data) {
                holder.setText(R.id.text, data);
                holder.setOnClickListener(holder.itemView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "data : " + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //使用 MultiItemCommonAdapter，回调 getItemViewType，让用户传入不同的 viewType，并根据 viewType 在 getLayoutId 中传入 layout id
        recyclerView.setAdapter(new MultiItemCommonAdapter<String>(this, dataList, new MultiItemTypeSupport<String>() {
            @Override
            public int getLayoutId(int viewType) {
                //根据不同 viewType 返回不同的 layout 给 viewHolder类 拿去创建 ViewHolder。
                if (viewType == 0) {
                    return R.layout.item_picture_left;
                }
                return R.layout.item_picture_up;
            }

            @Override
            public int getItemViewType(int position, String s) {
                //前三行的 item 和 后面的 item 使用不同的 item
                if (position < 3) {
                    return 0;
                }
                return 1;
            }
        }) {
            @Override
            public void convert(ViewHolder holder, String data) {
                holder.setText(R.id.text, data);
            }
        });
    }

    private List<String> initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add("item : " + i);
        }
        return dataList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_listView:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new LinearRecycleAdapter(this, dataList));
                break;

            case R.id.action_gridView:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                recyclerView.setAdapter(new LinearRecycleAdapter(this, dataList));
//                recyclerView.setAdapter(new StaggeredAdapter(this, dataList));
                break;

            case R.id.action_sta_gridView:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(new StaggeredAdapter(this, dataList));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
