package com.example.pcg.mvptest.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Mr_Peng
 * @created at 2017/7/12.
 * @describe: null
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    public V mView;
    public CompositeDisposable mCompositeDisposable;
    public Context mContext;

    public BasePresenterImpl(V mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void attachView(V view) {
        mView=view;
        mContext = view.getContext();
    }

    @Override
    public void detachView() {
        unSubscribe();
        mView=null;
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }
}
