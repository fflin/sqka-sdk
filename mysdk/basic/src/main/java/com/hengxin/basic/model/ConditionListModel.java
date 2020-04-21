package com.hengxin.basic.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */
public class ConditionListModel {
    public Condition condition;
    public PaginatorBean paginator;
    public List<CouponNewModel> coupon_list;
    public long timestamp;
    public List<HomeModel.HomePageItem> leaf_cats;

    public static class PaginatorBean  {
        public int page_no;
        public int items;
        public int pages;
        public int previousPage;
        public int nextPage;
    }

    public static class Condition {
        public transient SortList mCurrent;
        public int count_per_row ;
        public List<SortList> sortList;
        public transient boolean isAlone;
        public SearchFilterBean searchFilter;

        public void setCuuretnSortBean(SortList cuuretnSortBean) {
            this.mCurrent = cuuretnSortBean;
        }
    }

    public static class SearchFilterBean {
        public PriceRangeBean priceRange;
        public List<BoolFiltersBean> boolFilters;

    }

    public static class PriceRangeBean {
        public int startDef;
        public int endDef;
        public transient String oldMinStr;
        public transient String oldMaxStr;
        public transient int oldPostion = -1;
        public transient int selectPostion = -1;
        public List<RangeListBean> rangeList;
    }

    public static class RangeListBean extends FilterBean {
        public int start;
        public int end;
    }

    public static class BoolFiltersBean extends FilterBean {
        /**
         * key : is_taobao
         * name : 淘宝
         * def : false
         * radioKeys : ["is_tmall","is_taobao"]
         */
        public String key;
        public String name;
        public boolean def;
        public transient boolean oldSelect;
        public List<String> radioKeys;
    }

    public static class FilterBean {
        public transient View tvBean;
    }

    public static class SortList implements Parcelable {
        public String name;
        public String def_order;
        public String asc;
        public String desc;
        public boolean has_switch = false;
        //自己的
        public int old_select;//上一次的postion
        public int postion;

        protected SortList(Parcel in) {
            name = in.readString();
            def_order = in.readString();
            asc = in.readString();
            desc = in.readString();
            has_switch = in.readByte() != 0;
            old_select = in.readInt();
            postion = in.readInt();
        }

        public static final Creator<SortList> CREATOR = new Creator<SortList>() {
            @Override
            public SortList createFromParcel(Parcel in) {
                return new SortList(in);
            }

            @Override
            public SortList[] newArray(int size) {
                return new SortList[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(def_order);
            dest.writeString(asc);
            dest.writeString(desc);
            dest.writeByte((byte) (has_switch ? 1 : 0));
            dest.writeInt(old_select);
            dest.writeInt(postion);
        }
    }

    @Override
    public String toString() {
        return "ConditionListModel{" +
                "condition=" + condition +
                ", paginator=" + paginator +
                ", coupon_list=" + coupon_list +
                ", timestamp=" + timestamp +
                ", leaf_cats=" + leaf_cats +
                '}';
    }

}
