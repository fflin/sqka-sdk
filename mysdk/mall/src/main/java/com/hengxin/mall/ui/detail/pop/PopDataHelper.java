package com.hengxin.mall.ui.detail.pop;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.basic.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/26 10:45
 * desc   : 设置规格弹窗数据，并回调给详情页面
 * version: 1.0
 */
public class PopDataHelper {

    private Context mContext;
    private TextView tvPrice, tvCount, tvSpecify, tvSelCount, popConfirm;
    private ImageView imageView;// 商品图片
    private Button popSubstract, popPlus;
    private LinearLayout popSpecifyContainer;// 包裹规格布局 container_specify
    private int maxCount = 10;
    List<SpecTextData> spectTextList = new ArrayList<>();

    public PopDataHelper(Context context) {
        this.mContext = context;
        makeTestData();
    }


    public void initPopView(View popView, PopClickListener listener) {
        imageView = popView.findViewById(R.id.pop_img);
        tvPrice = popView.findViewById(R.id.pop_price);//价格要加货币符号
        tvCount = popView.findViewById(R.id.pop_count);
        tvSpecify = popView.findViewById(R.id.pop_specify);//已选中的规格，字符串拼接
        popSubstract = popView.findViewById(R.id.pop_subtract);
        popPlus = popView.findViewById(R.id.pop_plus);
        tvSelCount = popView.findViewById(R.id.pop_sel_count);
        popSpecifyContainer = popView.findViewById(R.id.pop_specify_container);
        popConfirm = popView.findViewById(R.id.pop_confirm);
        popConfirm.setOnClickListener(v -> {
            // 回调
            listener.onPopClick("");
        });

        popSubstract.setOnClickListener(v -> {
            int selCount = Integer.parseInt(tvSelCount.getText().toString().trim());
            if (selCount == 1) {
                ToastUtils.show(mContext, "请最少选择一件商品！");
            } else {
                tvSelCount.setText(String.valueOf(selCount - 1));
            }
        });

        popPlus.setOnClickListener(v -> {
            int selCount = Integer.parseInt(tvSelCount.getText().toString().trim());
            if (selCount == maxCount) {
                ToastUtils.show(mContext, "没有更多库存了");
            } else {
                tvSelCount.setText(String.valueOf(selCount + 1));
            }
        });
    }

    private void makeTestData() {
        List<SpecText> list1 = new ArrayList<>();
        list1.add(new SpecText("规格1", true));
        list1.add(new SpecText("规格2", false));
        list1.add(new SpecText("规格33333333", false));
        list1.add(new SpecText("规格4", false));

        List<SpecText> list2 = new ArrayList<>();
        list2.add(new SpecText("颜色1", true));
        list2.add(new SpecText("颜色2", false));
        list2.add(new SpecText("颜色3333333", false));
        list2.add(new SpecText("颜色4", false));
        spectTextList.add(new SpecTextData("规格", list1));
        spectTextList.add(new SpecTextData("颜色", list2));
    }

    // 数据使用测试数据
    public void initPopData(String selTitle, SpecText specText) {
        // 点击选择后重新设置数据
        if (specText != null) {
            for (int i = 0; i < spectTextList.size(); i++) {
                SpecTextData specTextData = spectTextList.get(i);
                List<SpecText> info = specTextData.info;
                String title = specTextData.title;
                if (selTitle.equals(title)) {
                    for (int j = 0; j < info.size(); j++) {
                        SpecText specText1 = info.get(j);
                        specText1.selected = specText == specText1;
                    }
                }
            }
            popSpecifyContainer.removeAllViews();
        }

        //规格还没写
        tvSpecify.setText("");

        // 数据到页面显示
        for (int i = 0; i < spectTextList.size(); i++) {
            View containerView = LayoutInflater.from(mContext).inflate(R.layout.container_specity, null);
            popSpecifyContainer.addView(containerView);
            FlexboxLayout flexBox = containerView.findViewById(R.id.container_flexbox);
            TextView containerTitle = containerView.findViewById(R.id.container_title);
            String title = spectTextList.get(i).title;
            containerTitle.setText(title);
            List<SpecText> info = spectTextList.get(i).info;
            for (int j = 0; j < info.size(); j++) {
                flexBox.addView(getTestTextView(title, info.get(j)));
            }
        }
    }

    private View getTestTextView(String title, SpecText specText) {

        RelativeLayout layout = new RelativeLayout(mContext);
        TextView tView = new TextView(mContext);
        tView.setPadding(ViewUtil.dp2px(15), ViewUtil.dp2px(4), ViewUtil.dp2px(15), ViewUtil.dp2px(4));
        tView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tView.setClickable(true);
        tView.setText(specText.text);
        if (specText.selected) {
            tView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_specify_text_selected));
            tView.setTextColor(mContext.getResources().getColor(R.color.detail_specify_text_stoke));
        } else {
            tView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_specify_text_normal));
            tView.setTextColor(mContext.getResources().getColor(R.color.detail_specify_text_1F));
        }
        layout.addView(tView);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tView.getLayoutParams();
        layoutParams.setMargins(0, 10, 20, 10);

        tView.setOnClickListener(v -> {
            initPopData(title, specText);
        });

        return layout;
    }
}

class SpecTextData {
    String title;
    List<SpecText> info;

    public SpecTextData(String title, List<SpecText> info) {
        this.info = info;
        this.title = title;
    }
}

class SpecText {
    String text;
    boolean selected;

    public SpecText(String text, boolean selected) {
        this.text = text;
        this.selected = selected;
    }
}
