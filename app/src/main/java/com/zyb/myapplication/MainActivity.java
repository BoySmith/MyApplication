package com.zyb.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LinearRecycleAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private List<String> dataList;

    private LinearRecyclerView linearRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*优化后*/
        linearRecyclerView = (LinearRecyclerView) findViewById(R.id.main_recycle_view);
        linearRecyclerView.initRecyclerView(initData());

        /*优化前*/
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycle_view);
        recyclerView.setLayoutManager(layoutManager);

        initData();
        adapter = new LinearRecycleAdapter(this, dataList);

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            //用于设置拖拽和滑动的方向
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                //设置允许拖拽item的方向，线性式布局有2个方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                //设置侧滑方向为从两个方向都可以
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            //长摁item拖拽到和另一个item重合时，调用
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView
                    .ViewHolder target) {
                Log.i("zyb", "onMove");
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                String strFrom = dataList.get(from);
                dataList.remove(from);
                dataList.add(to, strFrom);

                adapter.notifyItemMoved(from, to);//更新适配器中item的位置
                return true;
            }

            //这里处理滑动删除
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(position);
            }

            //按下和松开item时 调用
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                Log.i("zyb", "onSelectedChanged");
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY); //拖拽时设置背景色为灰色
                }
            }

            //拖拽停止时 调用
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                Log.i("zyb", "clearView");
                super.clearView(recyclerView, viewHolder);
            }

            ////当item视图变化时调用
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float
                    dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                viewHolder.itemView.setAlpha((float) 0.5); //可以在这里设置透明度
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LinearRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                itemTouchHelper.startDrag(recyclerView.getChildViewHolder(view)); //设置拖拽item
            }
        });

        recyclerView.setAdapter(adapter);
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
