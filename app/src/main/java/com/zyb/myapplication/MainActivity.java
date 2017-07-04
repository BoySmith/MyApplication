package com.zyb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

        layoutManager = new GridLayoutManager(this, 6);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycle_view);
        recyclerView.setLayoutManager(layoutManager);

        initData();
        adapter = new MultiGridRecycleAdapter(this, dataList);

        recyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add("item : " + i);
        }
        Log.i("zyb", "initData");
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
