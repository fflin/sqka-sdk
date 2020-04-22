package com.hengxin.mall.ui.home;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayout;
import com.hengxin.mall.model.HomePageType;
import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.FrscoImageLoader;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.mall.ui.home.viewholder.ActivityViewHolder;
import com.hengxin.mall.ui.home.viewholder.AloneCouponListViewHolder;
import com.hengxin.mall.ui.home.viewholder.ClassifyViewHolder;
import com.hengxin.mall.ui.home.viewholder.CouponItemViewHolder;
import com.hengxin.mall.ui.home.viewholder.EmptyHolder;
import com.hengxin.mall.ui.home.viewholder.GridViewHolder;
import com.hengxin.mall.utils.FrcosUtils;
import com.hengxin.mall.utils.ViewUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TodaySelectionTypeRBaseItem extends RBaseItem {
    private final static int noneType = 100;
    private final static int bannerType = 11000;
    private final static int cateType = 12501;//中图标5个/行 --测试分类
    private final static int topicTpye = 14000;
    private final static int labelType = 19000;
    private final static int couponType = 18000;
    private final static int lifecouponType = 10;//直播卷
    private final static int imageType = 16000;
    private final static int imgageItem = 1250;

    private final static int samllType = 12502;//小图标5行列 -- 支持动图
    private final static int twoImageType = 12201;//两张图图片--  支持动图
    private final static int threeImageType = 12301;//两张图图片
    private final static int scrollImageType = 21000;//可滑动的

    private final static int lineType = 24000;//线线线
    private final static int advoneType = 11001;
    private final static int broadcastType = 25000;
    private final static int activityType = 26000;//活动type  不用
    private int screenWidth;
    private FrscoImageLoader frscoImageLoader;
    private static final int recommendType = 18001;//为您推荐,数据解析coupon_list

    private FragmentManager mFragmentManager;
    //以下类型待接口完成
    private final static int classifyType = 99999;//分类
    private final static int verGoodsType = 999998;//竖向排列的商品item

    public TodaySelectionTypeRBaseItem(Context context, FragmentManager fragmentManager) {
        super(context);
        screenWidth = ViewUtil.getScreenWidth(context);
        frscoImageLoader = new FrscoImageLoader();
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {
        int itemViewType = getItemViewType(data, position);
        switch (itemViewType) {
            case advoneType:
            case bannerType: {
                BannerViewHoler bannerViewHoler = (BannerViewHoler) baseHolder;
                bannerViewHoler.setBannerDate(data, itemViewType);
            }
            break;
            case cateType: {
                GridViewHolder gridViewHolder = (GridViewHolder) baseHolder;
                gridViewHolder.setOnGridSelectClick(homePageItem -> {
                    /*if ("充值中心".equals(homePageItem.title) && KawApp.getLastVersion() != null) {
                        if (KawApp.getLastVersion().overSeas != null) {
                            homePageItem.setCurrentPage(KawApp.getLastVersion().overSeas);
                        }
                    }
                    //UMAnalyticUtils.onEvent2Param(getContext(), "Mall_category", homePageItem.title);*/
                    RouteUtils.isNewNative(homePageItem.open_type, mContext, homePageItem, callBackDetail);
                });
                gridViewHolder.bindView(data);
            }
            break;
            case topicTpye: {
                TopicViewHolder imageViewHolder = (TopicViewHolder) baseHolder;
                imageViewHolder.bindView(data);
            }
            break;
            case labelType: {
                LabelViewHolder labelViewHolder = (LabelViewHolder) baseHolder;
                labelViewHolder.bindView(data);
            }
            break;
            case lifecouponType:
            case couponType: {
                AloneCouponListViewHolder viewHolder = (AloneCouponListViewHolder) baseHolder;
                viewHolder.setNewListDate(data);
            }
            break;
            case imageType: {
                ImageTitleViewHolder imageTitleViewHolder = (ImageTitleViewHolder) baseHolder;
                imageTitleViewHolder.bindingDate(data);
            }
            break;
            case threeImageType:
            case twoImageType:
            case samllType: {
                FlexboxLayoutViewHolder flexboxLayoutViewHolder = (FlexboxLayoutViewHolder) baseHolder;
                flexboxLayoutViewHolder.bindingDate(data, itemViewType);
            }
            break;
            case scrollImageType:
                ScrollViewHolder scrollViewHolder = (ScrollViewHolder) baseHolder;
                scrollViewHolder.bindDateView(data);
                break;
//            case broadcastType: {
//                BroadCateViewHolder broadCateViewHolder = (BroadCateViewHolder) baseHolder;
//                broadCateViewHolder.setDateView(data);
//            }
//            break;
            case lineType:
                LineViewHolder lineViewHolder = (LineViewHolder) baseHolder;
                lineViewHolder.bindingDate(data);
                break;
            /*case activityType:
                ActivityViewHolder holder = (ActivityViewHolder) baseHolder;
                holder.bindingData(data);
                break;*/
            case recommendType:
                // 解析数据  CouponNewModel  重新写一个recyclerView, RecyclerView里的adapter设置为AloneHolder
                ActivityViewHolder activityViewHolder = (ActivityViewHolder) baseHolder;
                activityViewHolder.bindingData(data);
                break;

            case classifyType:
                ClassifyViewHolder classifyViewHolder = (ClassifyViewHolder) baseHolder;
                classifyViewHolder.bindView(mContext,mFragmentManager,data);
                break;

            case verGoodsType:
                CouponItemViewHolder couponItemViewHolder = (CouponItemViewHolder) baseHolder;
                couponItemViewHolder.setListDate(data);
                break;

            case noneType:
            default:
                break;

        }
    }

    //
    @Override
    public int getItemViewType(Object data, int position) {
        // com.hengxin.mall.model.CouponNewModel cannot be cast to com.hengxin.mall.model.HomeModel$HomePageItem
        HomePageType homePageItem = (HomePageType) data;
        return homePageItem.view_type;
    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        RecyclerView.ViewHolder holder = null;
        switch (itemType) {
            case advoneType:
            case bannerType: {
                holder = new BannerViewHoler(convertView);
            }
            break;
            case cateType: {
                holder = new GridViewHolder(convertView, mContext);
            }
            break;
            case topicTpye: {
                holder = new TopicViewHolder(convertView);
            }
            break;
            case labelType: {
                holder = new LabelViewHolder(convertView);
            }
            break;
            case lifecouponType:
            case couponType: {
                holder = new AloneCouponListViewHolder(convertView, mContext).setOnCallBackDetail(callBackDetail);
            }
            break;
            case imageType: {
                holder = new ImageTitleViewHolder(convertView);
            }
            break;
            case threeImageType:
            case twoImageType:
            case samllType: {
                holder = new FlexboxLayoutViewHolder(convertView);
            }
            break;
            case scrollImageType: {
                holder = new ScrollViewHolder(convertView);
            }
            break;
//            case broadcastType: {
//                holder = new BroadCateViewHolder(convertView);
//            }
//            break;
            case lineType:
                holder = new LineViewHolder(convertView);
                break;
            case recommendType:
                holder = new ActivityViewHolder(mContext, convertView);
                break;
            case classifyType:
                holder = new ClassifyViewHolder(convertView);
                break;
            case verGoodsType:
                holder = new CouponItemViewHolder(convertView,mContext);
                break;
            case noneType:
            default:
                holder = new EmptyHolder(convertView);
                break;
        }
        return holder;
    }

    @Override
    public int getItemLayout(int itemType) {
        int layoutid;
        switch (itemType) {
            case advoneType:
                layoutid = R.layout.coupon_topic_item_banner;//轮播图
                break;
            case bannerType: {
                layoutid = R.layout.coupon_list_item_banner;
            }
            break;
            case cateType: {
                layoutid = R.layout.page_today_gridview_type;
            }
            break;
            case topicTpye: {
                layoutid = R.layout.coupon_list_rlacteventdiv_item;
            }
            break;
            case labelType: {
                layoutid = R.layout.coupon_list_label_item;
            }
            break;
            case imgageItem: {
                layoutid = R.layout.page_home_imge_type;
            }
            break;
            case imageType:
                layoutid = R.layout.page_home_imge_title_type;
                break;
            case lifecouponType:
            case couponType: {
                layoutid = R.layout.view_alonel_list_item;//券布局
//                layoutid = R.layout.view_coupon_item;
            }
            break;
            case threeImageType:
            case twoImageType:
            case samllType: {
                layoutid = R.layout.page_today_small_icon_type;
            }
            break;
            case scrollImageType: {
                layoutid = R.layout.page_home_scroll_type;
            }
            break;
//            case broadcastType: {
//                layoutid = R.layout.page_home_broscase_type;
//            }
//            break;
            case lineType:
                layoutid = R.layout.page_home_line_type;
                break;
            case recommendType:
                layoutid = R.layout.page_activity;
                break;
            case classifyType:
                layoutid = R.layout.item_mall_classify;
                break;
            case verGoodsType:
                layoutid = R.layout.view_coupon_item;
                break;
            case noneType:
            default:
                layoutid = R.layout.common_none_item;
                break;
        }
        return layoutid;
    }

    //三张劵的 page_home_fixthree_type
    //四张券的低价专区  coupon_list_item_image_table

    private class BannerViewHoler extends RecyclerView.ViewHolder {
        private Banner banner;

        public BannerViewHoler(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.list_item_banner);
        }

        public void setBannerDate(Object bannerDate, int type) {
            if (banner.getTag() instanceof Integer) {
                ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                layoutParams.height = (int) banner.getTag();
                banner.setLayoutParams(layoutParams);
            }
            HomeModel.HomePageItem bannerBean = (HomeModel.HomePageItem) bannerDate;
            List<HomeModel.HomePageItem> dataBeans = bannerBean.list;
            ArrayList<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < dataBeans.size(); i++) {
                HomeModel.HomePageItem bean = dataBeans.get(i);
                if (i == 0) imageUrls.add(bean.pic + "€***€");
                else imageUrls.add(bean.pic);
            }
            if (!bannerBean.has_point) {
                banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
            }
            banner.setDelayTime((int) (bannerBean.interval * 1000));
            if (type == bannerType) {
                banner.setImages(imageUrls).setImageLoader(frscoImageLoader.setFist(true));
                banner.start();
            } else {
                if (imageUrls.size() == 1) {
                    banner.isAutoPlay(false);
                    banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                }
                banner.setImages(imageUrls).setImageLoader(frscoImageLoader.setFist(true));
                banner.start();
            }
            banner.setOnBannerListener(position -> {
                HomeModel.HomePageItem dataBean = dataBeans.get(position);
                RouteUtils.isNewNative(dataBean.open_type, mContext, dataBean, callBackDetail);
            });
        }
    }

    private class ImageTitleViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView page_image_title;
        private TextView pageMore;

        public ImageTitleViewHolder(View convertView) {
            super(convertView);
            page_image_title = convertView.findViewById(R.id.page_image_title);
            pageMore = convertView.findViewById(R.id.page_image_more);
        }

        public void bindingDate(Object data) {
            final HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) data;
            FrcosUtils.setControllerListener(page_image_title, homePageItem.pic, screenWidth, false);
            page_image_title.setOnClickListener(v -> RouteUtils.isNewNative(homePageItem.open_type, mContext, homePageItem, callBackDetail));

            if (!TextUtils.isEmpty(homePageItem.tag_right)) {
                pageMore.setVisibility(View.VISIBLE);
                pageMore.setText(homePageItem.tag_right);
            } else {
                pageMore.setVisibility(View.GONE);
            }
            pageMore.setOnClickListener(v -> {
                RouteUtils.isNewNative(homePageItem.open_type, mContext, homePageItem, callBackDetail);
            });
        }
    }

    /**
     * 创建5个图的布局
     */
    private class TopicViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView aivActevent1;
        private final SimpleDraweeView aivActevent2;
        private final SimpleDraweeView aivActevent3;
        private final SimpleDraweeView aivActevent4;

        public TopicViewHolder(View convertView) {
            super(convertView);
            aivActevent1 = convertView.findViewById(R.id.aivActevent1);
            aivActevent2 = convertView.findViewById(R.id.aivActevent2);
            aivActevent3 = convertView.findViewById(R.id.aivActevent3);
            aivActevent4 = convertView.findViewById(R.id.aivActevent4);

        }

        public void bindView(Object date) {
            HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) date;
            List<HomeModel.HomePageItem> list = homePageItem.list;
            try {
                aivActevent1.setImageURI(list.get(0).pic);
                aivActevent2.setImageURI(list.get(1).pic);
                aivActevent3.setImageURI(list.get(2).pic);
                aivActevent4.setImageURI(list.get(3).pic);
                clickAction(aivActevent1, list.get(0));
                clickAction(aivActevent2, list.get(1));
                clickAction(aivActevent3, list.get(2));
                clickAction(aivActevent4, list.get(3));
            } catch (Exception e) {

            }

        }

        private void clickAction(SimpleDraweeView aivActevent1, final HomeModel.HomePageItem homePageItem) {
            aivActevent1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //UMAnalyticUtils.onEvent2Param(getContext(), "Mall_category", homePageItem.title);
                    RouteUtils.isNewNative(homePageItem.open_type, mContext, homePageItem, callBackDetail);
//                    ChanelActivity.startSubclassInficationActivity(mContext, homePageItem.getTitle(), homePageItem.getCid(), homePageItem.getId());
                }
            });
        }
    }


    private class LabelViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView label_img;
        private final TextView label_tv;

        public LabelViewHolder(View convertView) {
            super(convertView);
            label_img = convertView.findViewById(R.id.label_img);
            label_tv = convertView.findViewById(R.id.label_tv);
        }

        public void bindView(Object object) {
            HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) object;
            label_img.setImageURI(homePageItem.pic);
            if (!TextUtils.isEmpty(homePageItem.title))
                label_tv.setText(homePageItem.title);
        }

    }

    private class ScrollViewHolder extends RecyclerView.ViewHolder {
        TextView pageFindAll, pageTitle;
        RecyclerView recyclerView;
        VLRAdapter adapter;

        public ScrollViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.page_home_scroll_view);
            pageFindAll = itemView.findViewById(R.id.scroll_find_all);
            pageTitle = itemView.findViewById(R.id.scroll_type_title);
            LinearLayoutManager layout = new LinearLayoutManager(mContext);
            layout.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layout);
            ScrollListRBaseItem mItem = new ScrollListRBaseItem(mContext);
            mItem.setCallBackDetail(callBackDetail);
            adapter = new VLRAdapter(mItem);
            recyclerView.setAdapter(adapter);
        }

        public void bindDateView(Object data) {
            HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) data;
            pageTitle.setText(homePageItem.title);
            pageFindAll.setOnClickListener(view -> {
                RouteUtils.isNewNative(homePageItem.open_type, mContext, homePageItem, callBackDetail);
                //UMAnalyticUtils.onEvent2Param(mContext, "Mall_daily_rank", homePageItem.title);
            });
            List<CouponNewModel> coupons = homePageItem.coupon_list;
            adapter.reLoadData(coupons, true);
        }
    }

    private class FlexboxLayoutViewHolder extends RecyclerView.ViewHolder {
        public FlexboxLayoutViewHolder(View convertView) {
            super(convertView);
        }

        public void bindingDate(Object data, int itemViewType) {
            HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) data;
            switch (itemViewType) {
                case samllType:
                    addSmllType(homePageItem, itemView);
                    break;
                case twoImageType:
//                    addTwoBigType(homePageItem, itemView);
                    addThreeBigType(homePageItem, itemView);
                    break;
                case threeImageType:
                    addThreeBigType(homePageItem, itemView);
                    break;
            }
        }

        private void addThreeBigType(HomeModel.HomePageItem homePageItem, View itemView) {
            FlexboxLayout flexboxLayout = (FlexboxLayout) itemView;
            if (flexboxLayout.getChildCount() != 0) {
                flexboxLayout.removeAllViews();
            }
            flexboxLayout.setShowDivider(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            flexboxLayout.setDividerDrawable(ContextCompat.getDrawable(mContext, R.drawable.line_bg));
            for (HomeModel.HomePageItem item : homePageItem.list) {
                SimpleDraweeView rootView = (SimpleDraweeView) LayoutInflater.from(getContext()).inflate(R.layout.page_flexbox_three_image_item, flexboxLayout, false);
                rootView.setOnClickListener(v -> {
                    RouteUtils.isNewNative(item.open_type, mContext, item, callBackDetail);
                    //UMAnalyticUtils.onEvent2Param(getContext(), "Mall_category", item.title);
                });
                rootView.setImageURI(item.pic);
                flexboxLayout.addView(rootView);
            }
        }

        private void addTwoBigType(HomeModel.HomePageItem homePageItem, View itemView) {
            FlexboxLayout flexboxLayout = (FlexboxLayout) itemView;
            if (flexboxLayout.getChildCount() != 0) {
                flexboxLayout.removeAllViews();
            }
            flexboxLayout.setShowDivider(FlexboxLayout.SHOW_DIVIDER_MIDDLE);
            flexboxLayout.setDividerDrawable(ContextCompat.getDrawable(mContext, R.drawable.line_bg));
            for (HomeModel.HomePageItem item : homePageItem.list) {
                SimpleDraweeView rootView = (SimpleDraweeView) LayoutInflater.from(getContext()).inflate(R.layout.page_flexbox_two_image_item, flexboxLayout, false);
                rootView.setOnClickListener(v -> {
                    RouteUtils.isNewNative(item.open_type, mContext, item, callBackDetail);
                    //UMAnalyticUtils.onEvent(getContext(), item.event_key);
                });

                setGifDraweeView(item.pic, rootView);
                flexboxLayout.addView(rootView);
            }
        }

        private void addSmllType(HomeModel.HomePageItem homePageItem, View itemView) {
            FlexboxLayout flexboxLayout = (FlexboxLayout) itemView;
            if (flexboxLayout.getChildCount() != 0) {
                flexboxLayout.removeAllViews();
            }
            for (HomeModel.HomePageItem item : homePageItem.list) {
                View rootView = LayoutInflater.from(getContext()).inflate(R.layout.page_flexbox_small_item, flexboxLayout, false);
                SimpleDraweeView boxIv = rootView.findViewById(R.id.flex_box_itemIv);
                setGifDraweeView(item.pic, boxIv);

                TextView boxTv = rootView.findViewById(R.id.flex_box_itemTv);
                boxTv.setText(item.title);

                rootView.setOnClickListener(v -> {
                    RouteUtils.isNewNative(item.open_type, mContext, item, callBackDetail);
                    //UMAnalyticUtils.onEvent2Param(getContext(), "Mall_category", item.title);
                });
                flexboxLayout.addView(rootView);
            }

        }

        private void setGifDraweeView(String url, SimpleDraweeView view) {
            DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(url))
                    .setAutoPlayAnimations(true).build();
            view.setController(controller);
        }
    }

    private class LineViewHolder extends RecyclerView.ViewHolder {

        public LineViewHolder(View itemView) {
            super(itemView);
        }

        public void bindingDate(Object data) {
            HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) data;
            if (homePageItem.line != null) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                layoutParams.height = ViewUtil.dp2px(homePageItem.line.height);
                layoutParams.leftMargin = ViewUtil.dp2px(homePageItem.line.left);
                layoutParams.rightMargin = ViewUtil.dp2px(homePageItem.line.right);
                itemView.setLayoutParams(layoutParams);
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.INVISIBLE);
            }
        }
    }
}


