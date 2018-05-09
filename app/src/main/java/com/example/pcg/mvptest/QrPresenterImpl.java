package com.example.pcg.mvptest;

import android.content.Context;
import android.widget.Toast;

import com.example.pcg.mvptest.api.ApiUtils;
import com.example.pcg.mvptest.api.BaseResult;
import com.example.pcg.mvptest.mvp.BasePresenterImpl;
import com.example.pcg.mvptest.mvp.BaseSubscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Mr_Peng
 * @created at 2017-08-17.
 * @describe: null
 */

public class QrPresenterImpl extends BasePresenterImpl<QrView> implements QrPresenter {
    public QrPresenterImpl(QrView mView, Context mContext) {
        super(mView, mContext);
    }

    @Override
    public void queryQr(String qrCode) {
        ApiUtils.getInstance().verifyCode(qrCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResult<VerifyBean>>() {
                    @Override
                    protected void onFailure(String err) {
                        Toast.makeText(mContext, "服务器繁忙", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(BaseResult<VerifyBean> verifyBeanBaseResult) {
                        if (verifyBeanBaseResult.code == 0) {
                            mView.QRSucces(verifyBeanBaseResult.data);
                        } else {
                            Toast.makeText(mContext, verifyBeanBaseResult.message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    protected void subscribe(Disposable disposable) {

                    }
                });

    }
}
