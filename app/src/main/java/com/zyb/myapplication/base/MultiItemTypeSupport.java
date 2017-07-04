package com.zyb.myapplication.base;

/**
 * Created by zhangyb on 2017/7/3.
 * function:
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
