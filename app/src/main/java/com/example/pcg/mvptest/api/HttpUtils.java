package com.example.pcg.mvptest.api;

import android.util.Log;


import com.example.pcg.mvptest.Constants;
import com.example.pcg.mvptest.MyApplacation;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Mr_Peng
 * @created at 2017/7/6.
 * @describe: http请求配置
 */

public class HttpUtils {
    private final String TAG = getClass().getSimpleName();
    private volatile Object s;

    private static class SingletonHolder {
        public static HttpUtils HTTP_UTILS = new HttpUtils();
    }


    public static HttpUtils getInstance() {
        return SingletonHolder.HTTP_UTILS;
    }

    /**
     * Retrofit 配置
     *
     * @param apiClass
     * @param <S>
     * @return <S>
     */
    public <S> S getService(Class<S> apiClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .client(getOkHttpClient(false))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        s = retrofit.create(apiClass);
        return (S) s;
    }

    /**
     * 请求初始化
     *
     * @return OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS);
        builder.connectTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS);

        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG + "okhttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        return builder.build();
    }

    /**
     * 请求初始化
     *
     * @return OkHttpClient
     */
    private OkHttpClient getOkHttpClient(boolean agent) {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(

                        new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                //输出访问地址
                                Log.e("OkHttp", message + " ====>");
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        //设置超时时间
        httpClientBuilder.connectTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(Constants.DEFAULT_TIME, TimeUnit.SECONDS);
        //设置缓存
        MyApplacation instance = MyApplacation.getInstance();
        File cacheDir = instance.getCacheDir();
        File httpCacheDirectory = new File(cacheDir, "OkHttpCache");
        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
//        //设置拦截器
//        httpClientBuilder.addInterceptor(new TokenInterceptor());

        if (!agent) {
            // 添加证书
            try {
                setCertificates(httpClientBuilder,
                        MyApplacation.getContext().getAssets().open("122.224.164.50.tomcat.cer"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            httpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } else {

            httpClientBuilder.addNetworkInterceptor(new CommonHeaderInterceptor());
        }

        return httpClientBuilder.build();
    }


    /**
     * 配置https证书
     *
     * @param builder
     * @param certificates
     */
    public void setCertificates(OkHttpClient.Builder builder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            builder.sslSocketFactory(sslContext.getSocketFactory());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class CommonHeaderInterceptor implements Interceptor {

        public CommonHeaderInterceptor() {
        }


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request()
                    .newBuilder();
            builder.header("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            return chain.proceed(builder.build());
        }
    }
}
