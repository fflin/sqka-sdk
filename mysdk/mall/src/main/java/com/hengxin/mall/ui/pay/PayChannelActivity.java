package com.hengxin.mall.ui.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.model.PayChannel;
import com.hengxin.mall.model.PayChannelModel;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/27 15:55
 * desc   : 支付渠道选择页面
 * version: 1.0
 */
public class PayChannelActivity extends BaseActivity implements PayChannelConstract.View {

    private TextView tvPayPrice;
    private TextView tvPayTime;
    private LinearLayout payChannelParent;
    private static final String PRICE_EXTRA = "priceExtra";
    private static final String ORDERID_EXTRA = "orderIdExtra";
    private String checkedChannel = "";

    public static void startPayChannelAty(Context context, String price, String orderId) {
        Intent intent = new Intent(context, PayChannelActivity.class);
        intent.putExtra(PRICE_EXTRA, price);
        intent.putExtra(ORDERID_EXTRA, orderId);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String priceStr = intent.getStringExtra(PRICE_EXTRA);
        String orderId = intent.getStringExtra(ORDERID_EXTRA);
        tvPayPrice.setText("￥" + priceStr);
        tvPayTime.setText("注意：这个接口的数据返回是JsonArray");
        // 获取支付渠道列表
        PayChannelPresenter presenter = new PayChannelPresenter();
        presenter.onAttach(this);
        presenter.getChannel();
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("收银台");
        tvPayPrice = findViewById(R.id.pay_price);
        tvPayTime = findViewById(R.id.pay_time);
        payChannelParent = findViewById(R.id.pay_channel_parent);
        findViewById(R.id.pay_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拉起微信 支付宝支付
                ToastUtils.show(PayChannelActivity.this,checkedChannel);



            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_page;
    }

    @Override
    public void success(BaseResult<PayChannelModel> result) {
        Log.i("fflin", "-------response success");
        PayChannelModel data = result.data;
        List<PayChannel> data1 = data.data;
        Log.i("fflin", "data1 = " + data1.size());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onError(String message) {
        Log.i("fflin", "-------response onError");
        ToastUtils.show(this, message);
        setTextData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setTextData() {
        List<PayChannel> list = new ArrayList<>();
        PayChannel channel = new PayChannel();
        channel.channel = "支付宝";
        channel.channel_type = "ali";
        channel.status = 0;
        channel.isChecked = true;
        list.add(channel);

        PayChannel wxchannel = new PayChannel();
        wxchannel.channel = "微信";
        wxchannel.channel_type = "wx";
        wxchannel.status = 0;
        channel.isChecked = true;
        list.add(wxchannel);

        for (int i = 0; i < list.size(); i++) {
            PayChannel c = list.get(i);
            if (c.status == 0) {
                View view = LayoutInflater.from(PayChannelActivity.this).inflate(R.layout.item_pay_channel, null);
                ImageView imgIcon = view.findViewById(R.id.pay_icon);
                ImageView imgRightIcon = view.findViewById(R.id.pay_right_img);
                Glide.with(PayChannelActivity.this).load(c.channel_type.equals("wx") ? getDrawable(R.drawable.pay_wechat) : this.getDrawable(R.drawable.pay_alipay)).into(imgIcon);
                ((TextView) view.findViewById(R.id.pay_channel)).setText(c.channel);
                ((TextView) view.findViewById(R.id.pay_channel_info)).setText(c.channel + "安全支付");
                view.findViewById(R.id.pay_item_parent).setOnClickListener(v -> {
                    c.isChecked = true;
                    checkedChannel = c.channel_type;
                    }
                );
                if (c.isChecked) {
                    Log.i("fflin","checked --- "+c.channel);
                } else {
                    Log.i("fflin","checked --- * "+c.channel);
                }
                imgRightIcon.setImageResource(c.isChecked ? R.drawable.xuanzhong_btn : R.drawable.weixuanzhong_btn);
                payChannelParent.addView(view);
            }
        }


    }
}
