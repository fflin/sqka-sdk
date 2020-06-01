package com.hengxin.mall.ui.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hengxin.mall.R;
import com.hengxin.mall.ui.search.inter.OnFilterBrandClick;

import java.util.ArrayList;

/**
 * author : fflin
 * date   : 2020/5/27 16:11
 * desc   : 目前只展示品牌，最终以接口为准，可能有N多展示条件
 * version: 1.0
 */
public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterViewHolder> {

    private Context mContext;
    private ArrayList<String> mList;
    private OnFilterBrandClick mClick;
    private ArrayList<String> selectedBrand = new ArrayList<>();

    public SearchFilterAdapter(Context context, ArrayList<String> list, OnFilterBrandClick click) {
        this.mContext = context;
        this.mList = list;
        this.mClick = click;
    }

    @NonNull
    @Override
    public SearchFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchFilterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_filter_text, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilterViewHolder holder, int position) {
        holder.filterText.setText(mList.get(position));
        holder.itemView.setOnClickListener(v -> {
            v.setTag(mList.get(position)); // 需要传回去一个list，因为可以多选
            mClick.onBrandClick(v);
            holder.itemView.setSelected(!holder.itemView.isSelected());
            if (holder.itemView.isSelected()) {
                selectedBrand.add(mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}

class SearchFilterViewHolder extends RecyclerView.ViewHolder {

    public final TextView filterText;

    public SearchFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        filterText = itemView.findViewById(R.id.filter_text);
    }
}
