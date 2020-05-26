package com.hengxin.mall.ui.address.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.model.AddressModel;
import com.hengxin.mall.ui.address.WriteAddressActivity;

/**
 * author : fflin
 * date   : 2020/4/29 18:52
 * desc   :
 * version: 1.0
 */
public class AddressViewHolder extends RecyclerView.ViewHolder {

    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.findViewById(R.id.add_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressModel model = new AddressModel();
                model.name = "test_name";
                model.phone = "12580";
                model.addSel = "北京市 北京市 通州区";
                model.addAll = "万达广场";
                model.addTag = "公司";
                model.isDefault = true;
                WriteAddressActivity.startEditAddAty(itemView.getContext(), model);
            }
        });
    }
}
