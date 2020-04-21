package com.hengxin.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2018/2/28.
 */

public class AutoHightGridView extends GridView {
    public AutoHightGridView(Context context) {
        super(context);
    }

    public AutoHightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
