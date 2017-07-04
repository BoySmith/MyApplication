package com.zyb.myapplication;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangyb on 2017/7/3.
 */
public class MultiGridRecycleAdapter extends RecyclerView.Adapter<MultiGridRecycleAdapter.MyViewHolder> {

    public static final int TYPE_ITEM_ONE_LEFT = 0;
    public static final int TYPE_ITEM_ONE_UP = 1;
    public static final int TYPE_ITEM_TWO_UP = 2;

    private List<String> mDataList;
    private LayoutInflater mInflater;

    public MultiGridRecycleAdapter(Context context, List<String> dataList) {
        mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MultiGridRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //根据 viewType 区分不同的 布局
        if (viewType == TYPE_ITEM_ONE_LEFT) {
            view = mInflater.inflate(R.layout.item_picture_left, parent, false);
        } else {
            view = mInflater.inflate(R.layout.item_picture_up, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MultiGridRecycleAdapter.MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //此函数在调用 RecyclerView.setAdapter 时调用
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridManager = ((GridLayoutManager) manager);

            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_ITEM_ONE_LEFT:
                        case TYPE_ITEM_ONE_UP:
                            return 6;

                        case TYPE_ITEM_TWO_UP:
                            return 3;

                        default:
                            return 6;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 3) { //前三行显示 图片在左、文字在右布局
            return TYPE_ITEM_ONE_LEFT;

        } else if (position < 6) { //第 4、5、6 行显示 图片在上、文字在下布局
            return TYPE_ITEM_ONE_UP;

        } else { // 其他行显示 两列，图片在上、文字在下布局
            return TYPE_ITEM_TWO_UP;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
