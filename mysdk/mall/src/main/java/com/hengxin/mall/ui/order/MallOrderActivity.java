package com.hengxin.mall.ui.order;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;


/**
 * author : fflin
 * date   : 2020/4/28 10:29
 * desc   :
 * version: 1.0
 */
public class MallOrderActivity extends BaseActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private MallOrderAllFragment allFragment;
    private MallOrderSaleFragment saleFragment;

    @Override
    protected void initData() {
        allFragment = new MallOrderAllFragment();
        saleFragment = new MallOrderSaleFragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.order_container, allFragment);
        transaction.commit();
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("优惠购订单");
        RadioButton radioAll = findViewById(R.id.radio_all);
        RadioButton radioSale = findViewById(R.id.radio_sale);
        RadioGroup group = findViewById(R.id.radio_group);
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (checkedId == R.id.radio_all) {
                transaction.replace(R.id.order_container, allFragment != null ? allFragment : new MallOrderAllFragment());
            } else {
                transaction.replace(R.id.order_container, saleFragment != null ? saleFragment : new MallOrderSaleFragment());
            }
            transaction.commit();
        });

        radioAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radioAll.setTextColor(isChecked ? getResources().getColor(R.color.white) : getResources().getColor(R.color.gradient_start));
            }
        });
        radioSale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radioSale.setTextColor(isChecked ? getResources().getColor(R.color.white) : getResources().getColor(R.color.gradient_start));
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_mall_order;
    }
}
