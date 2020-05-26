package com.hengxin.mall.ui.saleout.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.model.UpLoadFileModel;
import com.hengxin.mall.ui.saleout.Constant;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/19 11:39
 * desc   :
 * version: 1.0
 */
public class SelectPicAdapter extends RecyclerView.Adapter<SelectPicViewHolder> {

    private Context mContext;
    private List<UpLoadFileModel.FilesBean> mList;
    private OnAdapterClick mClick;
    public SelectPicAdapter(Context context, List<UpLoadFileModel.FilesBean> picModels, OnAdapterClick onAdapterClick) {
        this.mContext = context;
        this.mList = picModels;
        this.mClick = onAdapterClick;
    }

    @NonNull
    @Override
    public SelectPicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SelectPicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_select_pic,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectPicViewHolder holder, int i) {
        final UpLoadFileModel.FilesBean data = mList.get(i);
        String fileName = data.fileName;
        if (!TextUtils.isEmpty(fileName)) {
            if (fileName.equals(Constant.SELECT_PIC_NAME)) {
                holder.closeImg.setVisibility(View.GONE);
                holder.showImg.setImageResource(R.drawable.upload_img);
            } else {
                holder.closeImg.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.fileUrl).placeholder(R.drawable.placeholder_square).into(holder.showImg);
            }
        }

        holder.imgParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(data);
                mClick.onUploadClick(v);
            }
        });

        holder.closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(i);
                mClick.onDeleteClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}

class SelectPicViewHolder extends RecyclerView.ViewHolder {

    public final ImageView showImg;
    public final ImageView closeImg;
    public final View imgParent;

    public SelectPicViewHolder(@NonNull View itemView) {
        super(itemView);
        showImg = itemView.findViewById(R.id.upload_img);
        closeImg = itemView.findViewById(R.id.upload_close);
        imgParent = itemView.findViewById(R.id.img_parent);
    }
}
