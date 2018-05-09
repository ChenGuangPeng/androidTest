package com.example.pcg.mvptest.api;

import com.example.pcg.mvptest.VerifyBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * @author Mr_Peng
 * @created at 2017/7/6.
 * @describe: 协议参数工厂
 */

public interface ApiService {

   /*

    @GET("")
    Observable queryQr(@Query("mobile") String mobile, @Query("password") String password,
                     @Query("agentId") int agentId);

    @FormUrlEncoded
    @POST("")
    Observable getAuthCode(@Field("mobile") String mobile, @Field("agentId") int agentId);*/


    @FormUrlEncoded
    @POST("")
    Observable<BaseResult<VerifyBean>> verifyCode(@Field("permitCode") String qrCode);
}
