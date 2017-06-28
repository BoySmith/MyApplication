package com.zyb.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by lk on 16/7/12.
 */
public class PWRecyclerView extends RecyclerView {

    private OnScrolledLinstener onScrolledLinstener;
    private RecyclerView.Adapter adapter;
    private LayoutManager layout;

    public PWRecyclerView(Context context) {
        this(context, null);
    }

    public PWRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PWRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new ImageAutoLoadScrollListener());
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        this.layout = layout;
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(Adapter adapter) {
//        if(adapter instanceof  RecyclerView.Adapter) {
//            this.adapter = ( RecyclerView.Adapter) adapter;
//        }
        super.setAdapter(adapter);
    }

    public class ImageAutoLoadScrollListener extends OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (onScrolledLinstener != null) {
                onScrolledLinstener.onScrolled(recyclerView, dx, dy);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.  
                    //对于滚动不加载图片的尝试  
//                    adapter.notifyDataSetChanged();
                    Log.i("zyb", "SCROLL_STATE_IDLE");
                    break;

                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as
                // user touch input.
                    Log.i("zyb", "SCROLL_STATE_DRAGGING");
                    break;

                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not
                // under
                    Log.i("zyb", "SCROLL_STATE_SETTLING");
                    break;
            }
        }
    }


    public void setOnScrollLinstener(OnScrolledLinstener onScrolledLinstener) {
        this.onScrolledLinstener = onScrolledLinstener;
    }

    public interface OnScrolledLinstener {
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }


}