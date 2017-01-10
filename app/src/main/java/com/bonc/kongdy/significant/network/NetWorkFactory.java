package com.bonc.kongdy.significant.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kongdy on 2016/11/23.
 */
public class NetWorkFactory {

    private static Retrofit retrofit;
    private NetWorkFactory factory;

    public static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static final int girlsSize = 10;
    public static final int gankSize = 5;

    private static ZhihuLatestApi zhihuLatestApi;
    private static GirlsApi girlApi;
    private static WeatherApi weatherApi;
    private static GuoKrApi guoKrApi;
    private static VideoApi videoApi;
//    public NetWorkFactory getInstance(){
//        synchronized (this){
//            if (factory == null){
//                factory = new NetWorkFactory();
//            }
//        }
//        return factory;
//    }


    public static void init() {
        initOkHttp();
        initRetrofit();
    }

    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api")
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    private static void initOkHttp() {
        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.cache();
    }

    public static ZhihuLatestApi getZhihuApi(){
        if (zhihuLatestApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://news-at.zhihu.com/api/4/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            zhihuLatestApi = retrofit.create(ZhihuLatestApi.class);
        }
        return zhihuLatestApi;
    }


    public static GirlsApi getGirlsApi() {
        if (girlApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            girlApi = retrofit.create(GirlsApi.class);
        }
        return girlApi;
    }

    public static WeatherApi getWeatherApi() {
        if (weatherApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.heweather.com/x3/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }


    public static GuoKrApi getGuoKrApi(){
        if (guoKrApi ==null) {
            Retrofit retrofit =new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(GuoKrApi.guokrUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            guoKrApi = retrofit.create(GuoKrApi.class);
        }
        return guoKrApi;
    }

    public static VideoApi getVideoApi(){
        if (videoApi ==null) {
            Retrofit retrofit =new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(VideoApi.videoUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            videoApi = retrofit.create(VideoApi.class);
        }
        return videoApi;
    }


    //    http://api.bilibili.cn/recommend?tid=1&page=1&pagesize=3&order=comment
    //    http://doune.cc/tag/%E8%A7%86%E9%A2%91/feed/
    //    baobab.wdjcdn

}

