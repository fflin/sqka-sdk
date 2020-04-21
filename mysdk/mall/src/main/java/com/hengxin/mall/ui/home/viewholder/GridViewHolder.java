package com.hengxin.mall.ui.home.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.hengxin.mall.R;
import com.hengxin.basic.model.HomeModel;
import com.hengxin.mall.utils.FrcosUtils;
import com.hengxin.mall.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class GridViewHolder extends RecyclerView.ViewHolder {


    private OnGridSelectClick onGridSelectClick;
    private final GridView gridView;
    ImageTitleAdapter adapter;

    public GridViewHolder(View itemView, Context mContext) {
        super(itemView);
        gridView = itemView.findViewById(R.id.page_grid_view);
        adapter = new ImageTitleAdapter(mContext);
        gridView.setAdapter(adapter);
    }

    public void setOnGridSelectClick(OnGridSelectClick onGridSelectClick) {
        this.onGridSelectClick = onGridSelectClick;
    }

    public void bindView(Object data) {
        HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) data;
        adapter.addDate(homePageItem.list);

    }

    private class ImageTitleAdapter extends BaseAdapter {
        private List<HomeModel.HomePageItem> date = new ArrayList<>();
        private Context mContext;

        public ImageTitleAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return date.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final GridItemViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(R.layout.page_home_gridview_item, parent, false);
                holder = new GridItemViewHolder();
                holder.imageView = convertView.findViewById(R.id.pageTypeIv);
                holder.textView = convertView.findViewById(R.id.pageTypeTv);
                convertView.setTag(holder);
            } else {
                holder = (GridItemViewHolder) convertView.getTag();
                convertView.setOnClickListener(null);
            }
            HomeModel.HomePageItem homePageItem = date.get(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeModel.HomePageItem homePageItem1 = date.get(position);
                    if (onGridSelectClick != null) {
                        onGridSelectClick.onSelect(homePageItem1);
                    }
                }
            });
            holder.textView.setText(homePageItem.title);
//            holder.imageView.setImageURI(homePageItem.pic);
            FrcosUtils.setControllerListener(holder.imageView,homePageItem.pic, ViewUtil.dp2px(44),false);
            return convertView;
        }

        public void addDate(List arrayList) {
            if (date.size() > 0) {
                date.clear();
            }
            date.addAll(arrayList);
            ImageTitleAdapter.this.notifyDataSetChanged();
        }
    }

    private class GridItemViewHolder {
        TextView textView;
        SimpleDraweeView imageView;
    }

    public interface OnGridSelectClick {
        void onSelect(HomeModel.HomePageItem homePageItem);
    }
}