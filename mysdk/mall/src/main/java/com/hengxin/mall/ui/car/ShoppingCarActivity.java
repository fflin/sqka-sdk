package com.hengxin.mall.ui.car;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.Log;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.model.AuthHistoryModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/6/1 12:08
 * desc   :  购物车页面, 包含多选 删除，可结算  adapter3种样式，单店单商品 单店多商品 失效商品（失效商品展示在最后一页）
 * version: 1.0
 */
public class ShoppingCarActivity extends BaseActivity implements ShoppingCarContract.View {
    @Override
    protected void initData() {
        ShoppingCarPresenter presenter = new ShoppingCarPresenter();
        presenter.onAttach(this);
        presenter.getGoodsList("1");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayout() {
        return R.layout.aty_shopping_car;
    }

    @Override
    public void success(BaseResult<AuthHistoryModel> result) {

    }

    @Override
    public void failed(String message) {
        try {
            InputStream stream = getAssets().open("shop_car.json");
            String json = readStreamToString(stream);
            Log.i("fflin","json  = "+json);
            if (!TextUtils.isEmpty(json)) {
                AuthHistoryModel authHistoryModel = new Gson().fromJson(json, AuthHistoryModel.class);
                if (authHistoryModel != null) {
                    List<AuthHistoryModel.CouponsBean> coupons = authHistoryModel.coupons;
                    if (coupons != null) {
                        Log.i("fflin","coupons.size = "+coupons.size());
                        // 设置页面数据

                    } else {
                        Log.i("fflin","coupons = null");
                    }
                } else {
                    Log.i("fflin","auth model = null");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {

    }

    public static String readStreamToString(InputStream inputStream) throws IOException {
        //创建字节数组输出流 ，用来输出读取到的内容
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //创建读取缓存,大小为1024
        byte[] buffer = new byte[1024];
        //每次读取长度
        int len = 0;
        //开始读取输入流中的文件
        while( (len = inputStream.read(buffer) ) != -1){ //当等于-1说明没有数据可以读取了
            byteArrayOutputStream.write(buffer,0,len); // 把读取的内容写入到输出流中
        }
        //把读取到的字节数组转换为字符串
        String result = byteArrayOutputStream.toString();

        //关闭输入流和输出流
        inputStream.close();
        byteArrayOutputStream.close();
        //返回字符串结果
        return result;
    }

}
