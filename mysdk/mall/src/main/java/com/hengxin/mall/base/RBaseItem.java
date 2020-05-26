package com.hengxin.mall.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengxin.mall.inter.OnCallBackDetail;


/**
 * Created by  on 2017/6/5.
 */

public abstract class RBaseItem<T> {
    public Context mContext;

    protected OnCallBackDetail callBackDetail;

    public RBaseItem(Context context) {
        this.mContext = context;
    }

    /**
     * 该方法给控件设置数据
     * 首先获得holder对象
     * holder =convertview.gettag();
     */
    public abstract void binding(T data, RecyclerView.ViewHolder baseHolder, int position);


    /**
     * 创建holder的方法
     */
    public abstract RecyclerView.ViewHolder getHolder(View convertView, int itemType);

    /**
     * 多种itemtype 根据此方法返回类型区分
     * 重写此方法 把super删掉
     */
    public int getItemViewType(T data, int position) {
        return 0;
    }

    /**
     * 创建itemView的方法
     */
    public View creatView(ViewGroup parent, int itemType) {
        return LayoutInflater.from(parent.getContext()).inflate(getItemLayout(itemType), parent, false);
    }

    /**
     * 返回Item的布局Id;
     */
    public abstract int getItemLayout(int itemType);

    public Context getContext() {
        return mContext;
    }

    public void setCallBackDetail(OnCallBackDetail callBackDetail) {
        this.callBackDetail = callBackDetail;
    }
}
