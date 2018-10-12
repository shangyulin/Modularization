package com.example.shangyulin.modularizationtest;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by shangyulin on 2018/8/17.
 */

public class RxBus {

    private RxBus() {}

    private static class RxBusInstance {
        private static final Observer<String> INSTANCE = new Observer<String>() {

            Disposable dis;// 用于解除订阅关系

            @Override
            public void onSubscribe(Disposable d) {
                dis = d;
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread().getName());
            }
        };
    }

    public static Observer getInstance() {
        return RxBusInstance.INSTANCE;
    }
}
