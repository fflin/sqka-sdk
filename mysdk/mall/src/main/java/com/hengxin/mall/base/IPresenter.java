package com.hengxin.mall.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * Created by chunyang on 2017/10/30.
 */


public interface IPresenter<T> extends LifecycleObserver {

    void onAttach(T t);

    void onDetach();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner);


}
