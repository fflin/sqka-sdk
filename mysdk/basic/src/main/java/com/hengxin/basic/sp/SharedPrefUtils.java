package com.hengxin.basic.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.hengxin.basic.ContextProvider;

import java.util.Set;

public class SharedPrefUtils {

    private static Context mContext;
    private static final String name = "hengxin_sdk";

    public static final String SEARCH_HISTORY_KEY = "search_history";

    public static void set(String key, Object value) {
        synchronized (SharedPrefUtils.class) {
            SharedPreferences sp = SharedPrefUtils.getInstance();
            if (sp != null) {
                SharedPreferences.Editor editor = sp.edit();
                if ((value instanceof Boolean)) {
                    editor.putBoolean(key, (Boolean) value);
                }

                if ((value instanceof String)) {
                    editor.putString(key, (String) value);
                }

                if ((value instanceof Integer)) {
                    editor.putInt(key, (Integer) value);
                }

                if ((value instanceof Long)) {
                    editor.putLong(key, (Long) value);
                }

                if ((value instanceof Float)) {
                    editor.putFloat(key, (Float) value);
                }

                if ((value instanceof Set)) {
                    editor.putStringSet(key, ((Set) value));
                }

                editor.apply();
            }
        }
    }

    public static boolean getBoolean(String key, boolean value) {
        SharedPreferences sp = SharedPrefUtils.getInstance();
        if (sp != null) {
            value = sp.getBoolean(key, value);
        }
        return value;
    }

    public static String getString(String key, String value) {
        SharedPreferences sp = SharedPrefUtils.getInstance();
        if (sp != null) {
            value = sp.getString(key, value);
        }
        return value;
    }

    public static Set<String> getArray(String key, Set<String> value) {
        SharedPreferences sp = SharedPrefUtils.getInstance();
        if (sp != null) {
            value = sp.getStringSet(key, value);
        }
        return value;
    }

    public static float getFloat(String name, float defaultValue) {
        SharedPreferences sp = SharedPrefUtils.getInstance();
        if (sp == null) return defaultValue;
        return sp.getFloat(name, defaultValue);
    }

    //b(String key)
    public static void remove(String key) {
        SharedPreferences sp = SharedPrefUtils.getInstance();
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    private static SharedPreferences getInstance() {
        SharedPreferences sp;
        if (SharedPrefUtils.mContext == null) {
            SharedPrefUtils.mContext = ContextProvider.get().getContext();
        }
        if (SharedPrefUtils.mContext == null) {
            sp = null;
        } else {
            sp = SharedPrefUtils.mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        return sp;
    }
}
