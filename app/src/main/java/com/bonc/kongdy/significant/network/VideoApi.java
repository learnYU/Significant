package com.bonc.kongdy.significant.network;

import com.bonc.kongdy.significant.model.VideoBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by kongdy on 2016/11/23.
 */
public interface VideoApi {

    String videoUrl = "http://baobab.wandoujia.com/api/v2/";
//   feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
    String videoNum = "2";

    @GET("feed")
    Observable<VideoBean> getVideoData();
//    Observable<VideoBean> getVideoData(@Query("num")String num, @Query("udid") String page,@Query("vc") String vc);


}
