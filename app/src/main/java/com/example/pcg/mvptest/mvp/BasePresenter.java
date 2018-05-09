package com.example.pcg.mvptest.mvp;

/**
 * @author Mr_Peng
 * @created at 2017/7/12.
 * @describe: null
 */

public interface BasePresenter <V extends BaseView> {
    void attachView(V view);
    void detachView();
}
