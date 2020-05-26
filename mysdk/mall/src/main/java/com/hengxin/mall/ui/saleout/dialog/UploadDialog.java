package com.hengxin.mall.ui.saleout.dialog;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import com.hengxin.mall.R;

import java.util.Objects;

/**
 * author : fflin
 * date   : 2020/3/3 17:41
 * desc   :
 * version: 1.0
 */
public class UploadDialog {

    private AlertDialog dialog;
    private Animation animation;

    public UploadDialog() {

    }

    public void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_upload, null);
        builder.setView(inflate);
        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        ImageView imageView = inflate.findViewById(R.id.iv_loading);
        animation = AnimationUtils.loadAnimation(context, R.anim.ani_upload_dialog);
        imageView.startAnimation(animation);
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            animation.cancel();
        }
    }
}
