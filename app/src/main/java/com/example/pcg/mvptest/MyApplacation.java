package com.example.pcg.mvptest;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * @author Mr_Peng
 * @created at 2017/7/6 13:46.
 * @describe: null
 */

public class MyApplacation extends Application {
    private static MyApplacation mInstance;
    private static Context mContext;

    @Override
    public void onCreate() {
        if (mInstance == null) {
            mInstance = this;
        }
        mContext = getApplicationContext();
        ZXingLibrary.initDisplayOpinion(this);
        super.onCreate();
    }

    public static MyApplacation getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mContext;
    }
}
