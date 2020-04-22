package com.hengxin.basic.base;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.hengxin.basic.util.Log;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by chunyang on 2017/10/30.
 */

public abstract class BasePresenter<T extends IBaseView> implements IPresenter<T> {


    protected T mView;
    protected CompositeDisposable mDisposable;

    public BasePresenter() {
        mDisposable = new CompositeDisposable();
    }


    public boolean isViewAttached() {
        return mView != null;
    }


    public void checkViewAttached() {
        if (!isViewAttached()) throw new RuntimeException("未注册View");
    }

    @Override
    public void onAttach(T t) {
        mView = t;
        if (t instanceof Activity) {
            ((AppCompatActivity) t).getLifecycle().addObserver(this);
        } else if (t instanceof Fragment) {
            ((Fragment) t).getLifecycle().addObserver(this);
        }

    }

    @Override
    public void onDetach() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
            Log.i("fflin","--------------------- dispose 11 ");
        }
        mView = null;
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            Log.i("fflin","--------------------- dispose 22 ");
        }
        mView = null;
    }
}