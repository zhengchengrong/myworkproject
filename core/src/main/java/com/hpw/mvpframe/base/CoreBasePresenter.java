package com.hpw.mvpframe.base;

import com.hpw.mvpframe.RxManager;

public abstract class CoreBasePresenter< T> {
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void attachVM(T v) {
        this.mView = v;
        this.onStart();
    }

    public void detachVM() {
        mRxManager.clear();
        mView = null;
    }

    public abstract void onStart();
}
