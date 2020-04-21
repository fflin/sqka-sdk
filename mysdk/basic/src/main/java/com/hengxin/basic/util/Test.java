package com.hengxin.basic.util;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.Nullable;

/**
 * author : fflin
 * date   : 2020/4/20 15:28
 * desc   :
 * version: 1.0
 */
public class Test extends ContentResolver {

    public Test(@Nullable Context context) {
        super(context);
        Context applicationContext = context.getApplicationContext();
    }
}
