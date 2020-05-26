package com.hengxin.mall.ui.detail;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.constant.DetailConstant;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.model.DetailModel;
import com.hengxin.mall.model.DetailTypedModel;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.mall.ui.detail.adapter.GoodsDetailItem;
import com.hengxin.mall.ui.detail.pop.PopClickListener;
import com.hengxin.mall.ui.detail.pop.PopDataHelper;
import com.hengxin.mall.ui.settlement.SettleListActivity;
import com.hengxin.basic.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/23 15:57
 * desc   : 商品详情
 * version: 1.0
 */
public class GoodsDetailActivity extends BaseActivity implements GoodsDetailContract.View, PopClickListener {
    private static final String EXTRA_ID = "extraId";
    private RecyclerView goodsRecylerView;//商品详情
    private List<String> picList = new ArrayList<>();
    private VLRAdapter adapter;
    private PopupWindow specifyPopWindow;
    private PopDataHelper popDataHelper = new PopDataHelper(this);

    public static void startDetailActivity(Context context, String goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(EXTRA_ID, goodsId);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String goodsId = intent.getStringExtra(EXTRA_ID);
        ToastUtils.show(this, "goodsId = " + goodsId);
        GoodsDetailPresenter presenter = new GoodsDetailPresenter();
        presenter.onAttach(this);
        presenter.getDetail(goodsId);
        GoodsDetailItem item = new GoodsDetailItem(this);
        adapter = new VLRAdapter(item);
        goodsRecylerView.setLayoutManager(new CrashBugLinearLayoutManager(this));
        goodsRecylerView.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("商品详情");

        goodsRecylerView = findViewById(R.id.detail_recycler);

        findViewById(R.id.detail_bot_back).setOnClickListener(v -> finish());
        findViewById(R.id.detail_bot_car).setOnClickListener(v -> ToastUtils.show(GoodsDetailActivity.this, "跳购物车页面"));
        findViewById(R.id.detail_bot_car_add).setOnClickListener(v -> ToastUtils.show(GoodsDetailActivity.this, "加入购物车"));
        initSpecifyPop();
        findViewById(R.id.detail_bot_buy).setOnClickListener(v -> {
            specifyPopWindow.showAtLocation(findViewById(R.id.detail_bot_buy), Gravity.BOTTOM,0, 0);
            setWindowBg(0.4f);

            specifyPopWindow.setOnDismissListener(() -> setWindowBg(1f));
        });
    }

    private void setWindowBg(float v2) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = v2;
        getWindow().setAttributes(lp);
    }

    private void initSpecifyPop() {
        View popView = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.pop_goods_specify, null);
        //pop高度这里控制
        specifyPopWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dp2px(430) , true);
        specifyPopWindow.setOutsideTouchable(true);
        popDataHelper.initPopView(popView, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (specifyPopWindow != null) {
            specifyPopWindow.dismiss();
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_mall_detail;
    }

    @Override
    public void success(BaseResult<HomeModel> result) {

    }

    @Override
    public void failed(String message) {

    }

    @Override
    public void onErrorTest(DetailModel model) {
        setDataByType(model);
        popDataHelper.initPopData(null, null);
    }


    private void setDataByType(DetailModel model) {
        List<DetailTypedModel> list = new ArrayList<>();

        DetailTypedModel headerTypedData = new DetailTypedModel();
        DetailModel headerModel = new DetailModel();
        headerModel.banner_list = model.banner_list;
        headerModel.price = model.price;
        headerModel.old_price = model.old_price;
        headerModel.goods_name = model.goods_name;
        headerTypedData.type = DetailConstant.TYPE_HEADER;
        headerTypedData.model = headerModel;
        list.add(headerTypedData);

        for (int i = 0; i < model.pic_list.size(); i++) {
            DetailTypedModel infoTypedData = new DetailTypedModel();
            infoTypedData.type = DetailConstant.TYPE_INFO;
            DetailModel infoModel = new DetailModel();
            infoModel.picInfo = model.pic_list.get(i);
            infoTypedData.model = infoModel;
            list.add(infoTypedData);
        }

        DetailTypedModel sloganTyped = new DetailTypedModel();
        sloganTyped.type = DetailConstant.TYPE_SLOGAN;
        list.add(sloganTyped);
        Log.i("fflin","list.size = "+list.size());

        adapter.reLoadData(list,false);
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {
        ToastUtils.show(this, message);
    }

    @Override
    public void onPopClick(Object obj) {
        if (specifyPopWindow != null && specifyPopWindow.isShowing()) {
            specifyPopWindow.dismiss();
            startActivity(new Intent(this, SettleListActivity.class));
        }
    }
}
