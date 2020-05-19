package com.hengxin.mall.ui.saleout.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.model.SaleOutDialogModel;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/18 15:34
 * desc   : 售后对话框
 * version: 1.0
 */
public class SaleOutDialogHelper {
    private Context mContext;
    private AlertDialog alertDialog;

    public SaleOutDialogHelper(Context context) {
        this.mContext = context;
    }

    public void showListDialog(String title, List<SaleOutDialogModel> list, OnListDialogClick onListDialogClick) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_single_btn, null);
        TextView tvTitle = view.findViewById(R.id.dialog_title);
        tvTitle.setText(title);
        ListView listContent = view.findViewById(R.id.dialog_content);
        ListDialogAdapter adapter = new ListDialogAdapter(mContext, list, onListDialogClick);
        listContent.setAdapter(adapter);

        view.findViewById(R.id.dialog_confirm).setVisibility(View.GONE);


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        alertDialog = builder.create();

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.show();
    }

    public void dismissDilog() {
        if (alertDialog != null && mContext != null) {
            alertDialog.dismiss();
        }
    }
}
