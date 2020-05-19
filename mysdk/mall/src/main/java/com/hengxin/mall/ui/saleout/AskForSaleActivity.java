package com.hengxin.mall.ui.saleout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.manager.CrashBugGridLayoutManager;
import com.hengxin.mall.model.SaleOutDialogModel;
import com.hengxin.mall.model.SelectPicModel;
import com.hengxin.mall.ui.saleout.adapter.OnAdapterClick;
import com.hengxin.mall.ui.saleout.adapter.SelectPicAdapter;
import com.hengxin.mall.ui.saleout.dialog.OnListDialogClick;
import com.hengxin.mall.ui.saleout.dialog.SaleOutDialogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/29 12:43
 * desc   : 申请售后页面
 * version: 1.0
 */
public class AskForSaleActivity extends BaseActivity implements OnAdapterClick {

    private TextView askType;
    private TextView askReason;
    private List<SaleOutDialogModel> askTypeList = new ArrayList<>();
    private List<SaleOutDialogModel> askReasonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<SelectPicModel> picModels = new ArrayList<>();

    public static void startAskForSaleAty(Context context) {
        Intent intent = new Intent(context, AskForSaleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 6; i++) {
            SelectPicModel model = new SelectPicModel();
            model.fileName = Constant.SELECT_PIC_NAME;
            picModels.add(model);
        }

        SelectPicAdapter adapter = new SelectPicAdapter(this, picModels, this);
        recyclerView.setAdapter(adapter);

        SaleOutDialogModel model1 = new SaleOutDialogModel("换货", "(更换收到的商品)", true);
        SaleOutDialogModel model2 = new SaleOutDialogModel("退款", "(未收到货，只退款)", false);
        SaleOutDialogModel model3 = new SaleOutDialogModel("退款退货", "(已收到货，需要退换已收到的货物)", false);

        askTypeList.add(model1);
        askTypeList.add(model2);
        askTypeList.add(model3);

        SaleOutDialogModel model4 = new SaleOutDialogModel("多拍、错拍", "", false);
        SaleOutDialogModel model5 = new SaleOutDialogModel("质量问题", "", false);
        SaleOutDialogModel model6 = new SaleOutDialogModel("7天无理由", "", false);
        SaleOutDialogModel model7 = new SaleOutDialogModel("其他", "", false);

        askReasonList.add(model4);
        askReasonList.add(model5);
        askReasonList.add(model6);
        askReasonList.add(model7);

    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("申请售后");
        recyclerView = findViewById(R.id.ask_photo_rv);

        recyclerView.setLayoutManager(new CrashBugGridLayoutManager(this,3));

        findViewById(R.id.ask_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskForSaleActivity.this.startActivity(new Intent(AskForSaleActivity.this, AskSuccActivity.class));
            }
        });
        askType = findViewById(R.id.ask_type);
        askReason = findViewById(R.id.ask_reason);
        askType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleOutDialogHelper helper = new SaleOutDialogHelper(AskForSaleActivity.this);
                helper.showListDialog("请选择申请类型", askTypeList, new OnListDialogClick() {
                    @Override
                    public void onItemClick(View view) {
                        SaleOutDialogModel model = (SaleOutDialogModel) view.getTag();
                        if (model != null) {
                            String title = model.title;
                            if (!TextUtils.isEmpty(title)) {
                                askType.setText(title);
                            }
                        }
                        helper.dismissDilog();
                    }
                });
            }
        });

        askReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleOutDialogHelper helper = new SaleOutDialogHelper(AskForSaleActivity.this);
                helper.showListDialog("请选择申请类型", askReasonList, new OnListDialogClick() {
                    @Override
                    public void onItemClick(View view) {
                        SaleOutDialogModel model = (SaleOutDialogModel) view.getTag();
                        if (model != null) {
                            String title = model.title;
                            if (!TextUtils.isEmpty(title)) {
                                askReason.setText(title);
                            }
                        }
                        helper.dismissDilog();
                    }
                });
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_ask_sale;
    }


    @Override
    public void onUploadClick(View view) {
        // 上传照片  更新list
    }

    @Override
    public void onDeleteClick(View view) {

    }
}
