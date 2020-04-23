package com.hengxin.mall.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

/**
 * author : fflin
 * date   : 2020/4/23 16:58
 * desc   : 解决商品详情页标题与前面的icon组合后换行的问题
 * version: 1.0
 */

/**
 * ex
 *         SpannableString spannableString = new SpannableString(" " + item.getGod_des());
 *         Drawable drawable = mContext.getResources().getDrawable(R.drawable.jianjie);
 *         drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
 *         spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
 *         tv_jianjie.setText(spannableString);
 */
public class VerticalImageSpan extends ImageSpan {

    public VerticalImageSpan(Drawable drawable) {
        super(drawable);

    }

    public VerticalImageSpan(Bitmap b) {
        super(b);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }
}