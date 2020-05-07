package com.hengxin.mall.ui.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.model.TagModel;
import com.hengxin.mall.ui.search.inter.OnHotWordsClick;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/7 15:33
 * desc   :
 * version: 1.0
 */
public class SearchResultHotAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<TagModel.Tag> mHotList;

    private Context mContext;
    private OnHotWordsClick mClick;

    public SearchResultHotAdapter(Context context, OnHotWordsClick click) {
        this.mContext = context;
        this.mClick = click;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_top, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mHotList != null) {
            String title = mHotList.get(i).title;
            viewHolder.topText.setText(title);
            viewHolder.topText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(title);
                    mClick.onWordsClick(v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHotList == null ? 0 : mHotList.size();
    }

    public void setData(List<TagModel.Tag> hotList) {
        this.mHotList = hotList;
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    public final TextView topText;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        topText = itemView.findViewById(R.id.search_top_text);
    }
}
