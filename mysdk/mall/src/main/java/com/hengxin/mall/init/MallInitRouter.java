package com.hengxin.mall.init;

import android.content.Context;
import android.content.Intent;

import com.hengxin.mall.ui.car.ShoppingCarActivity;
import com.hengxin.mall.ui.home.MallHomeActivity;
import com.hengxin.mall.ui.order.detail.OrderDetailActivity;

/**
 * author : fflin
 * date   : 2020/4/20 16:19
 * desc   :
 * version: 1.0
 */
public class MallInitRouter {
    public static void jumpBy() {

    }

    public static void jumpByIndex(Context mContext, int index) {
        switch (index) {
            case 1:
                Intent intent = new Intent(mContext, MallHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;

            case 2:
                break;

            case 3:
                Intent testIntent = new Intent(mContext, ShoppingCarActivity.class);
                testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(testIntent);
                break;

            case 4:
                OrderDetailActivity.startOrderDetailAty(mContext,"123");
                break;
        }
    }
}
