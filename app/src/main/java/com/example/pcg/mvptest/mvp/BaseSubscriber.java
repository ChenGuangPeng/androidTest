package com.example.pcg.mvptest.mvp;

import android.content.Context;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * @author Mr_Peng
 * @created at 2017/7/6.
 * @describe: null
 */

public abstract class BaseSubscriber<T> implements Observer<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    public BaseSubscriber() {

    }

    protected abstract void onFailure(String err);

    protected abstract void onSuccess(T t);
    protected abstract void subscribe(Disposable disposable);


    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        subscribe(disposable);
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        onFailure(throwable.toString());
    }

    @Override
    public void onComplete() {
        // TODO: 2017/7/6  请求完成后所需操作
    }

}
