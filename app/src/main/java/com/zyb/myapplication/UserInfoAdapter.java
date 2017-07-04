package com.zyb.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.zyb.myapplication.base.MultiItemCommonAdapter;
import com.zyb.myapplication.base.MultiItemTypeSupport;
import com.zyb.myapplication.base.ViewHolder;

import java.util.List;

/**
 * Created by zhangyb on 2017/7/3.
 */
public class UserInfoAdapter extends MultiItemCommonAdapter {

    public UserInfoAdapter(Context context, List datas, MultiItemTypeSupport multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
    }

    @Override
    public void convert(ViewHolder holder, Object o) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
