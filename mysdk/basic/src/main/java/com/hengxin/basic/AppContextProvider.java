package com.hengxin.basic;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.hengxin.basic.util.Log;

/**
 * author : fflin
 * date   : 2020/4/20 16:31
 * desc   : https://www.jianshu.com/p/a263e46a912f
 * version: 1.0
 */
public class AppContextProvider extends ContentProvider {

    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    @Override
    public boolean onCreate() {
        mContext = getContext();
        Log.i("fflin","mContext provider = "+mContext.hashCode());
        return true;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
