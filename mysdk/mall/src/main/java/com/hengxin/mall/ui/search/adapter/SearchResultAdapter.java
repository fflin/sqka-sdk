package com.hengxin.mall.ui.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengxin.mall.R;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.ui.search.SearchResultConstant;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/7 18:09
 * desc   :
 * version: 1.0
 */
public class SearchResultAdapter extends RecyclerView.Adapter<Holder>{
    private List<CouponNewModel> mList;
    private Context mContext;
    private final int ALONE = 1;
    private final int COLUMN = 2;

    public SearchResultAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //mall_alone_list_item(一列)  mall_column_item(两列)
        View view;
        if (getItemViewType(i) == ALONE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.mall_alone_list_item,viewGroup,false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.mall_column_item,viewGroup,false);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return SearchResultConstant.alone;
    }

    public void setData(List<CouponNewModel> couponList) {
        this.mList = couponList;
    }
}

class Holder extends RecyclerView.ViewHolder {

    public Holder(@NonNull View itemView) {
        super(itemView);
    }
}
