package com.hengxin.mall.ui.search.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengxin.basic.util.ViewUtil;
import com.hengxin.mall.R;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.ui.search.SearchResultConstant;
import com.hengxin.mall.ui.search.inter.OnResultTopClick;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/7 16:39
 * desc   : 搜索结果绑定数据
 * version: 1.0
 */
public class ResultDataHelper {

    public ResultDataHelper() {

    }

    //销量等  还少一个选中装态
    public void bindTopData(Context mContext, LinearLayout parent, List<ConditionListModel.SortList> sortList, OnResultTopClick onClick) {
        parent.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        for (int i = 0; i < sortList.size(); i++) {
            ConditionListModel.SortList bean = sortList.get(i);
            String name = bean.name;
            int postion = bean.postion;//暂存选中状态
            boolean hasSwitch = bean.has_switch;
            TextView textView = buildTextView(mContext, name, postion == i);

            if (hasSwitch) {
                LinearLayout outLinear = buildOuterLinear(mContext, params);
                ImageView imageView = new ImageView(mContext);
                if (postion == i) {
                    imageView.setImageResource(bean.def_order.equals(bean.desc) ? R.drawable.ic_sort_price_desc : R.drawable.ic_sort_price_asc);
                } else {
                    imageView.setImageResource(R.drawable.ic_sort_price_none);
                }

                outLinear.addView(textView);
                outLinear.addView(imageView);
                parent.addView(outLinear);
                outLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setTag(bean);
                        onClick.onSortClick(v);
                    }
                });
            } else {
                textView.setLayoutParams(params);
                parent.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setTag(bean);
                        onClick.onSortClick(v);
                    }
                });
            }
        }

        // 加个图片 切换列表展示方式
        ImageView switchImg = new ImageView(mContext);
        switchImg.setImageResource(SearchResultConstant.alone == 1 ? R.drawable.list_btn_switchsigle : R.drawable.list_btn_switchdouble);
        switchImg.setMaxHeight(ViewUtil.dp2px(20));
        switchImg.setMaxWidth(ViewUtil.dp2px(20));
        switchImg.setPadding(ViewUtil.dp2px(5), ViewUtil.dp2px(5), ViewUtil.dp2px(5), ViewUtil.dp2px(5));
        parent.addView(switchImg);
        switchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onShowTypeClick(v);
                switchImg.setImageResource(SearchResultConstant.alone == 1 ? R.drawable.list_btn_switchsigle : R.drawable.list_btn_switchdouble);
            }
        });

        // 加个筛选
        TextView filterTextView = buildTextView(mContext, "筛选", false);
        LinearLayout linearLayout = buildOuterLinear(mContext, params);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.filter);

        linearLayout.addView(filterTextView);
        linearLayout.addView(imageView);
        parent.addView(linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onFilterClick(v);
            }
        });
    }

    private TextView buildTextView(Context context, String text, boolean isSelect) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        textView.setGravity(Gravity.CENTER);
        ColorStateList csl = context.getResources().getColorStateList(R.color.selector_color_sort);
        textView.setTextColor(csl);
        textView.setSelected(isSelect);
        return textView;
    }

    private LinearLayout buildOuterLinear(Context mContext, LinearLayout.LayoutParams params) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);
        return linearLayout;
    }
}
