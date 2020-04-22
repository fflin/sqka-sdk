package com.hengxin.mall.ui.home.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.mall.ui.home.adapter.ActivityViewAdapter;

import java.util.ArrayList;


/**
 * author : fflin
 * date   : 2019/4/2 14:21
 * desc   : 为您推荐item
 * version: 1.0
 */
public class ActivityViewHolder extends RecyclerView.ViewHolder implements OnCallBackDetail {
    public RecyclerView recyclerView;
    private Context mContext;

    public ActivityViewHolder(Context context, View convertView) {
        super(convertView);
        this.mContext = context;
        recyclerView = itemView.findViewById(R.id.rv_activity);
    }

    public void bindingData(Object data) {
        // 设置数据
        HomeModel.HomePageItem item = (HomeModel.HomePageItem) data;
        ArrayList<CouponNewModel> models = (ArrayList<CouponNewModel>) item.coupon_list;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (models != null) {
            ActivityViewAdapter adapter = new ActivityViewAdapter(mContext,models,this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClickJumpChaoRenDetail(Object date) {
        CouponNewModel model = (CouponNewModel) date;
    }

    @Override
    public void onLinkFestivalTaobao(Object date) {

    }

    @Override
    public void onCheckUpdateApp() {

    }
}
