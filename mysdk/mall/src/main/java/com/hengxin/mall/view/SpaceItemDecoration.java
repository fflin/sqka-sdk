package com.hengxin.mall.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author:author
 * date: 2018/5/7
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    Paint mPaint;

    public SpaceItemDecoration(int space) {
        this.space = space;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xffededed);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private static final int HORIZONTAL = 355;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.right = space / 2;
            outRect.left = space;
        } else {
            outRect.right = space;
            outRect.left = space / 2;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.save();
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            if ((((GridLayoutManager) parent.getLayoutManager())).getSpanCount() == 2) {
                draw(c, parent);
            }
        }
        c.restore();

    }

    //绘制横向 item 分割线
    private void draw(Canvas canvas, RecyclerView parent) {
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + space;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);//绘制图片下放的水平线
            }
            if (parent.getChildLayoutPosition(child) % 2 == 0) {
                canvas.drawRect(0, child.getTop(), left, child.getBottom() + space, mPaint);//绘制左边图片的左间线
            }
            top = child.getTop();
            bottom = child.getBottom() + space;
            left = child.getRight() + layoutParams.rightMargin;
            right = left + space;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);//绘制图片的右间线
            }
        }
    }

}
