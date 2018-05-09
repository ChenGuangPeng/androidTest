package com.example.pcg.mvptest;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.example.pcg.androidtest.R;

public class MainActivity extends Activity implements QrView {

    QrPresenterImpl presenter;

    @Override
    public void QRSucces(VerifyBean bean) {
        tvContent.setText("业主：" + bean.getProprietorName() + "\n" + "地址：" + bean.getAddress() + "\n" +
                "业主手机：" + bean.getMobile() + "\n" + "访客姓名：" + bean.getVisitorName() + "\n" +
                "性别：" + (bean.getSex() == 1 ? "男" : "女") + "\n" + "是否驾车：" + (bean.getIsDrive() == 1 ? "是" : "否") + "\n" +
                "车牌号：" + bean.getPlateNumber());
    }

    @Override
    public Context getContext() {
        return null;
    }

    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        presenter = new QrPresenterImpl(this, this);
        presenter.queryQr(getIntent().getStringExtra("result"));
    }


    public void btnClick(View view) {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
