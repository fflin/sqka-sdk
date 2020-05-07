package com.hengxin.basic;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 */
public class RxBus {

    //    // 有背压处理的 RxBus
    //    private final FlowableProcessor<Object> bus;

    //非背压处理
    private final Subject<Object> bus;

    private RxBus() {
        //非背压处理
        bus = PublishSubject.create().toSerialized();
        //        // 有背压处理的 RxBus
        //        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getInstance() {
        return Holder.IN;
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public boolean hasObservable() {
        return bus.hasObservers();
    }

    public Observable<Object> register() {
        return bus;
    }
    /*
     * 转换为特定类型的Obserbale
     */
    public <T> Observable<T> register(Class<T> type) {
        return bus.ofType(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    private static class Holder {
        private static RxBus IN = new RxBus();
    }
}
