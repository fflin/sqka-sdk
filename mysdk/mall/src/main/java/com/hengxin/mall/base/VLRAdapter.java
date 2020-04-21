package com.hengxin.mall.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.hengxin.basic.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by  on 2017/6/5.
 */

public class VLRAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<T> mTotalList = new ArrayList<>();


    private RBaseItem mItem;

    public RBaseItem getmItem() {
        return mItem;
    }

    public VLRAdapter(RBaseItem mItem) {
        this.mItem = mItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        return mItem.getHolder(mItem.creatView(viewGroup, itemType), itemType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        mItem.binding(mTotalList.get(position), viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mTotalList.size();
    }


    @Override
    public int getItemViewType(int position) {
        //bugly:13608 Invalid index 39, size is 20
        if (mTotalList.size() > position) {
            return mItem.getItemViewType(mTotalList.get(position), position);
        } else {
            return 0;
        }
    }


    /**
     * @param isClean 是否清空以前的数据,分页加载传false,普通加载传true
     */
    public void reLoadData(List<T> list, boolean isClean) {
        if (isClean) {
            mTotalList.clear();
        }
        mTotalList.addAll(list);
        notifyDataSetChanged();
    }

    public void reLoadData(List<T> list) {
        mTotalList.clear();
        notifyItemRangeRemoved(0, mTotalList.size() - 1);
        mTotalList.addAll(list);
        Log.i("notifyDataSetChanged", ".notifyDataSetChanged..");
        notifyDataSetChanged();
    }

    public void reLoadData(List<T> list, int postion) {
        postion = postion == 0 ? -1 : postion;
        for (int i = 0; i <= postion; i++) {
            list.add(i, mTotalList.get(i));
        }
        mTotalList.clear();
        mTotalList.addAll(list);
        notifyItemRangeChanged(postion + 1, mTotalList.size() - 1, "postion");


    }


    public void reLoadNotifyData(List<T> list, int postion) {
        for (int i = 0; i <= postion; i++) {
            list.add(i, mTotalList.get(i));
        }
        try {
            notifyItemRangeRemoved(postion + 1, mTotalList.size() - 1);
            mTotalList.clear();
            mTotalList.addAll(list);
            notifyItemRangeChanged(postion + 1, mTotalList.size() - 1, "postion");
        } catch (Exception e) {
            Log.i("reLoadData", "reLoadData");
        }

    }

    public void reLoadNotifyOtherData(List<T> list) {
        int size = mTotalList.size();
        for (int i = 0; i < size; i++) {
            list.add(i, mTotalList.get(i));
        }
        notifyItemRangeRemoved(size + 1, mTotalList.size() - 1);
        mTotalList.clear();
        mTotalList.addAll(list);
        notifyItemRangeChanged(size + 1, mTotalList.size() - 1, "postion");

    }

    public void clearData() {
        mTotalList.clear();
        notifyDataSetChanged();
    }

    public void insert(T obj, int position) {
        mTotalList.add(position, obj);
        notifyItemInserted(position);
    }

    public void delete(int position) {
        mTotalList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeDate(int position) {
        mTotalList.remove(position);
    }

    public T getPostionIndex(int postion) {
        if (mTotalList.size() > postion) {
            return mTotalList.get(postion);
        } else return null;
    }

    public List<T> getmTotalList() {
        return mTotalList;
    }
}
