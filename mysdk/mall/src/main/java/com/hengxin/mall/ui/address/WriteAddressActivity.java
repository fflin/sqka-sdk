package com.hengxin.mall.ui.address;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.model.AddressModel;
import com.hengxin.pickcity.CityConfig;
import com.hengxin.pickcity.CityPicker;
import com.hengxin.pickcity.Interface.OnCityItemClickListener;
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
    private String selTag;
    private ImageView imgDefault;
    private TextView tvSelectAdd;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddAll;
    private TextView tagSchool;
    private TextView tagCompany;
    private TextView tagHome;

    public static void startEditAddAty(Context context, AddressModel model) {
        Intent intent = new Intent(context, WriteAddressActivity.class);
        intent.putExtra("model", model);
        context.startActivity(intent);
    }

    public static void toWriteAddAty(Context context) {
        Intent intent = new Intent(context, WriteAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        cityPicker = new CityPicker();
        cityPicker.init(WriteAddressActivity.this);
        CityConfig config = new CityConfig.Builder().build();
        config.setShowType(CityConfig.ShowType.PRO_CITY_DIS);
        cityPicker.setConfig(config);
        AddressModel model = (AddressModel) getIntent().getSerializableExtra("model");
        if (model != null) {
            tvName.setText(model.name);
            tvPhone.setText(model.phone);
            tvSelectAdd.setText(model.addSel);
            tvAddAll.setText(model.addAll);
            String addTag = model.addTag;
            if (!TextUtils.isEmpty(addTag)) {
                setTagState(tagSchool, tagHome, tagCompany);
            }
            imgDefault.setSelected(model.isDefault);
        }
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("添加收货地址");
        tvName = findViewById(R.id.add_name);
        tvPhone = findViewById(R.id.add_phone);
        tvAddAll = findViewById(R.id.write_address_all);
        tvSelectAdd = findViewById(R.id.write_address);
        findViewById(R.id.write_address_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);
                        tvSelectAdd.setText(province.getName() + "\r" + city.getName() + "\r" + district.getName());
                    }
                });
                cityPicker.showCityPicker();
            }
        });
        tagCompany = findViewById(R.id.write_tag_company);
        tagHome = findViewById(R.id.write_tag_home);
        tagSchool = findViewById(R.id.write_tag_school);
        tagCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTagState(tagCompany, tagHome, tagSchool);
            }
        });
        tagHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTagState(tagHome, tagCompany, tagSchool);
            }
        });
        tagSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTagState(tagSchool, tagHome, tagCompany);
            }
        });
        imgDefault = findViewById(R.id.add_is_default);
        imgDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDefault.setSelected(!imgDefault.isSelected());
            }
        });
        findViewById(R.id.add_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString().trim();
                String addSelect = tvSelectAdd.getText().toString().trim();
                String phone = tvPhone.getText().toString().trim();
                String addAll = tvAddAll.getText().toString().trim();
                if (checkEmpty(name, "请输入收货人姓名") || checkEmpty(phone, "请输入收货人电话")
                        || checkEmpty(addSelect, "请选择收获地址") || checkEmpty(addAll, "请输入详细地址")) {
                    return;
                }
                boolean isDefault = imgDefault.isSelected();
                ToastUtils.show(WriteAddressActivity.this, "name =" + name + "\n phone = " + phone + "\n addSel = " + addSelect + "\n addAll = " + addAll + "\n tag = " + selTag + "\n default = " + isDefault);
            }
        });
    }

    private boolean checkEmpty(String text, String msg) {
        if (text.isEmpty()) {
            ToastUtils.show(WriteAddressActivity.this, msg);
            return true;
        }
        return false;
    }

    private void setTagState(TextView selectText, TextView nText1, TextView nText2) {
        selectText.setSelected(true);
        selectText.setTextColor(getResources().getColor(R.color.red_theme));
        nText1.setSelected(false);
        nText1.setTextColor(getResources().getColor(R.color.net_error_color));
        nText2.setSelected(false);
        nText2.setTextColor(getResources().getColor(R.color.net_error_color));
        selTag = selectText.getText().toString().trim();
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_write_address;
    }
}
