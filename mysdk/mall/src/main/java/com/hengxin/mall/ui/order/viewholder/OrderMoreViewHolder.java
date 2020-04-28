package com.hengxin.mall.ui.order.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.ui.order.OrderConstant;
import com.hengxin.mall.utils.ViewUtil;

/**
 * author : fflin
 * date   : 2020/4/28 18:03
 * desc   : 合并订单item
 * version: 1.0
 */
public class OrderMoreViewHolder extends RecyclerView.ViewHolder {

    private final ImageView shopIcon;
    private final TextView shopName;
    private final TextView orderStatus;
    private final LinearLayout orderMoreContainer;
    private final LinearLayout orderBtnContainer;
    private final TextView orderTotalPrice;
    private final TextView saleNotice;

    public OrderMoreViewHolder(@NonNull View itemView) {
        super(itemView);
        shopIcon = itemView.findViewById(R.id.settle_icon);
        shopName = itemView.findViewById(R.id.settle_shop_name);
        orderStatus = itemView.findViewById(R.id.order_status);
        orderMoreContainer = itemView.findViewById(R.id.order_more_container);
        orderTotalPrice = itemView.findViewById(R.id.order_total_price);
        orderBtnContainer = itemView.findViewById(R.id.order_btn_container);
        saleNotice = itemView.findViewById(R.id.salt_order_notice);
    }


    public void bindData(Context context, Object data, int position, int mOrderTag) {
        if (mOrderTag == OrderConstant.SALE_ORDER_TAG) {
            saleNotice.setVisibility(View.VISIBLE);
        } else {
            saleNotice.setVisibility(View.GONE);
        }

        shopName.setText("企叮咚");
        orderMoreContainer.removeAllViews();
        for (int i = 0; i < getTest(position); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_order_layout_goods, null);
            orderMoreContainer.addView(view);
        }

        orderBtnContainer.removeAllViews();
        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(context);
            textView.setText("测试测试" + i);
            textView.setBackgroundResource(R.drawable.bg_settle_btn);
            textView.setPadding(ViewUtil.dp2px(10), ViewUtil.dp2px(5), ViewUtil.dp2px(10), ViewUtil.dp2px(5));
            textView.setTextColor(context.getResources().getColor(R.color.white));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(ViewUtil.dp2px(10), 0, 0, 0);//4个参数回按顺序分别是左上右下
            textView.setLayoutParams(layoutParams);

            orderBtnContainer.addView(textView);
        }
    }

    private int getTest(int position) {

        return position % 3 == 0 ? 3 : 1;
    }
}
