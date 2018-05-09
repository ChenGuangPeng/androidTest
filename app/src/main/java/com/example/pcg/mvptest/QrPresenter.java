package com.example.pcg.mvptest;
import com.example.pcg.mvptest.mvp.BasePresenter;

/**
 * @author Mr_Peng
 * @created at 2017-09-14.
 * @describe: null
 */

public interface QrPresenter extends BasePresenter<QrView> {
    void queryQr(String qrCode);
}
