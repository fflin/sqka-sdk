package com.hengxin.mall.ui.search;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hengxin.basic.sp.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/6 16:43
 * desc   :
 * version: 1.0
 */
public class SearchSpHelper {

    public SearchSpHelper() {

    }

    public void addSearchWord(String inputText) {
        String oldText = SharedPrefUtils.getString(SharedPrefUtils.SEARCH_HISTORY_KEY, "");
        if (!TextUtils.isEmpty(inputText)) {
            List<String> historySave = new ArrayList<>();
            if (!"".equals(oldText)) {
                historySave = new Gson().fromJson(oldText, ArrayList.class);
                if (historySave.size() > 12) historySave.remove(0);
                historySave.remove(inputText);
            }
            historySave.add(inputText);
            String newText = new Gson().toJson(historySave);
            SharedPrefUtils.set(SharedPrefUtils.SEARCH_HISTORY_KEY,newText);
        }
    }

    public  ArrayList<String> getSearchWords() {
        ArrayList<String> list = new ArrayList<>();
        String hisListStr = SharedPrefUtils.getString(SharedPrefUtils.SEARCH_HISTORY_KEY, "");
        if (!TextUtils.isEmpty(hisListStr)) {
            list = new Gson().fromJson(hisListStr, ArrayList.class);
        }
        return list;
    }
}
