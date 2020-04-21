package com.hengxin.mall.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by Administrator on 2017/11/29.
 * 防止RecyclerView更新出现crash
 */

public class CrashBugLinearLayoutManager extends LinearLayoutManager {
    public CrashBugLinearLayoutManager(Context context) {
        super(context);
    }

    public CrashBugLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CrashBugLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

    }
}
