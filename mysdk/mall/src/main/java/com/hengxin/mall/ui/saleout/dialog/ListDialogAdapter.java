package com.hengxin.mall.ui.saleout.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.model.SaleOutDialogModel;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/18 16:03
 * desc   :
 * version: 1.0
 */
public class ListDialogAdapter extends BaseAdapter {
    private Context mContext;
    private List<SaleOutDialogModel> mList;
    private OnListDialogClick mClick;

    public ListDialogAdapter(Context context, List<SaleOutDialogModel> list, OnListDialogClick onItemClick) {
        this.mContext = context;
        this.mList = list;
        this.mClick = onItemClick;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sale_out_dialog, parent, false);
        }
        CheckBox checkBox = convertView.findViewById(R.id.dialog_check_box);
        TextView tvTitle = convertView.findViewById(R.id.dialog_title);
        TextView tvSubTitle = convertView.findViewById(R.id.dialog_sub_title);
        tvTitle.setText(mList.get(position).title);
        String subTitle = mList.get(position).subTitle;
        if (!TextUtils.isEmpty(subTitle)) {
            tvSubTitle.setText(subTitle);
            tvSubTitle.setVisibility(View.VISIBLE);
        } else {
            tvSubTitle.setVisibility(View.GONE);
        }

        /*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setChecked(position, isChecked);
            }
        });
        checkBox.setChecked(isChecked(position));*/


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setData(position, isChecked);
                notifyDataSetChanged();
            }

            private void setData(int position, boolean isChecked) {
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(i).isChecked = isChecked;
                    } else {
                        mList.get(i).isChecked = false;
                    }
                }
            }
        });

        checkBox.setChecked(mList.get(position).isChecked);

        convertView.findViewById(R.id.dialog_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(mList.get(position));
                mClick.onItemClick(v);
            }
        });



        return convertView;
    }

}

