package com.hengxin.mall.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2017/11/29.
 * 防止RecyclerView更新出现crash
 */

public class CrashBugGridLayoutManager extends GridLayoutManager {


    public CrashBugGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CrashBugGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CrashBugGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
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
