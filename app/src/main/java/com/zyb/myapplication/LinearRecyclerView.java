package com.zyb.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by zhangyb on 2017/6/27.
 */
public class LinearRecyclerView extends RecyclerView {

    private Context myContext;
    private LinearRecycleAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private RecyclerView.LayoutManager layoutManager;

    public LinearRecyclerView(Context context) {
        super(context, null, 0);
        myContext = context;
    }

    public void initRecyclerView(List<String> dataList) {
        layoutManager = new LinearLayoutManager(myContext, VERTICAL, false);
        setLayoutManager(layoutManager);

        adapter = new LinearRecycleAdapter(myContext, dataList);
        setAdapter(adapter);

        addOnScrollListener(new ImageAutoLoadScrollListener());
        addItemMoveListener();
    }

    private void addItemMoveListener() {
        itemTouchHelper = new ItemTouchHelper(new ItemMoveListener());
        itemTouchHelper.attachToRecyclerView(this);
    }

    private class ItemMoveListener extends ItemTouchHelper.Callback {

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

            String strFrom = adapter.getDataList().get(from);
            adapter.getDataList().remove(from);
            adapter.getDataList().add(to, strFrom);

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
    }

    private class ImageAutoLoadScrollListener extends OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            switch (newState) {
                case SCROLL_STATE_IDLE: //item停止滚动时
                    break;

                case SCROLL_STATE_DRAGGING: //item正在滚动时，手指未松开
                    break;

                case SCROLL_STATE_SETTLING: //item通过惯性滚动时，手指已松开
                    break;
            }
        }
    }
}
