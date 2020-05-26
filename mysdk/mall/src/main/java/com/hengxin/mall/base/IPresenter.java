package com.hengxin.mall.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by chunyang on 2017/10/30.
 */


public interface IPresenter<T> extends LifecycleObserver {

    void onAttach(T t);

    void onDetach();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner);


}
