package com.hengxin.mall.ui.transport;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengxin.mall.R;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/29 12:05
 * desc   :
 * version: 1.0
 */
public class TransportViewHolder extends RecyclerView.ViewHolder {

    private final ImageView transIcon;
    private final View transLine;
    private final TextView transStatus;
    private final TextView transTime;
    private final TextView consultRes;

    public TransportViewHolder(@NonNull View itemView) {
        super(itemView);
        transIcon = itemView.findViewById(R.id.trans_item_icon);
        transLine = itemView.findViewById(R.id.trans_line);
        transStatus = itemView.findViewById(R.id.trans_status);
        transTime = itemView.findViewById(R.id.trans_time);
        consultRes = itemView.findViewById(R.id.consult_res);
    }

    public void bindViewData(Context context, List list, int position, boolean fromTransform) {
        if (position == 0) {
            transIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.transport_ok));
        } else {
            transIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.transport_normal));
        }

        if (position == list.size() - 1) {
            transLine.setVisibility(View.GONE);
        } else {
            transLine.setVisibility(View.VISIBLE);
        }

        if (fromTransform) {
            consultRes.setVisibility(View.GONE);
        } else {
            consultRes.setVisibility(View.VISIBLE);
        }
    }
}
