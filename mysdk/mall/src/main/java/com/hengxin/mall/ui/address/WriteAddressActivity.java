package com.hengxin.mall.ui.address;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.pickcity.Interface.OnCityItemClickListener;
import com.hengxin.pickcity.CityConfig;
import com.hengxin.pickcity.CityPicker;
import com.hengxin.pickcity.bean.CityBean;
import com.hengxin.pickcity.bean.DistrictBean;
import com.hengxin.pickcity.bean.ProvinceBean;

/**
 * author : fflin
 * date   : 2020/4/29 17:57
 * desc   : 添加收货地址
 * version: 1.0
 */
public class WriteAddressActivity extends BaseActivity {

    private CityPicker cityPicker;

    public static void toWriteAddAty(Context context) {
        Intent intent = new Intent(context, WriteAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        cityPicker = new CityPicker();
        cityPicker.init(WriteAddressActivity.this);
        CityConfig config = new  CityConfig.Builder().build();
        config.setShowType(CityConfig.ShowType.PRO_CITY_DIS);
        cityPicker.setConfig(config);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("添加收货地址");
        TextView tvSelectAdd = findViewById(R.id.write_address);
        findViewById(R.id.write_address_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);
                        tvSelectAdd.setText(province.getName()+"\r"+city.getName()+"\r"+district.getName());
                    }
                });
                cityPicker.showCityPicker();
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_write_address;
    }
}
