package com.hengxin.mall.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengxin.mall.R;
import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.ui.home.viewholder.AloneCouponListViewHolder;

import java.util.ArrayList;


/**
 * author : fflin
 * date   : 2019/4/2 14:40
 * desc   :
 * version: 1.0
 */
public class ActivityViewAdapter extends RecyclerView.Adapter<AloneCouponListViewHolder> {

    private Context mContext;
    private ArrayList<CouponNewModel> item;
    private OnCallBackDetail mLister;
    public ActivityViewAdapter(Context context, ArrayList<CouponNewModel> item, OnCallBackDetail listener) {
        this.mContext = context;
        this.item = item;
        this.mLister = listener;
    }

    @NonNull
    @Override
    public AloneCouponListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mall_alone_list_item,parent,false);
        AloneCouponListViewHolder holder = new AloneCouponListViewHolder(view, mContext);
        holder.setOnCallBackDetail(mLister);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AloneCouponListViewHolder holder, int position) {
        holder.setNewListDate(item.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

}
