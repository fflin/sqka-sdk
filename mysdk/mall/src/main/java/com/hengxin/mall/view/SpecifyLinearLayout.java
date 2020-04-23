package com.hengxin.mall.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * author : fflin
 * date   : 2020/4/23 17:24
 * desc   : 用在商品详情页面选择规格的布局，分为左中右
 * version: 1.0
 */
public class SpecifyLinearLayout extends LinearLayout {
    public SpecifyLinearLayout(Context context) {
        super(context);
    }

    public SpecifyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecifyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
