package com.zyb.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyb on 2017/6/22.
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDataset;
    private LayoutInflater mInflater;

    private List<Integer> mHeight;

    //提供一个合适的构造方法
    public StaggeredAdapter(Context context, List<String> dataset) {
        this.mContext = context;
        this.mDataset = dataset;
        mInflater = LayoutInflater.from(context);

        mHeight = new ArrayList<Integer>();
        for (int i = 0; i < mDataset.size(); i++) {
            mHeight.add((int) (100 + Math.random() * 300));
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.item, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    /**
     * 建立起MyViewHolder中视图与数据的关联
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(mDataset.get(position));
        ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
        lp.height = mHeight.get(position);
        viewHolder.itemView.setLayoutParams(lp);
    }

    /**
     * 获取item的数目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //自定义的ViewHoder，持有item的所有控件
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }
}
