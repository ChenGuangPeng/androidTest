package com.example.pcg.mvptest.api;

import com.example.pcg.mvptest.VerifyBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @author Mr_Peng
 * @created at 2017/7/6.
 * @describe: 协议类
 */

public class ApiUtils {
    private ApiService apiService;

    public static class ApiHolde {
        private static final ApiUtils API_UTILS = new ApiUtils();
    }

    public ApiUtils() {
        apiService = HttpUtils.getInstance().getService(ApiService.class);
    }

    public static ApiUtils getInstance() {
        return ApiHolde.API_UTILS;
    }

    public Observable<BaseResult<VerifyBean>> verifyCode(@Field("permitCode") String qrCode) {
        return apiService.verifyCode(qrCode);
    }

}
