package com.bonc.kongdy.significant.network;

import com.bonc.kongdy.significant.model.GuokrBean;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kongdy on 2016/11/23.
 */
public interface GuoKrApi {

    String guokrUrl="http://www.guokr.com/apis/minisite/";
    //    http://www.guokr.com/apis/minisite/article.json?retrieve_type=by_channel&channel_key=hot
    String articleUrl = "http://www.guokr.com/article/";


    @GET("article.json")
    Observable<GuokrBean> getGuokrData(@Query("retrieve_type") String retrieveType, @Query("channel_key") String channelKey);

    @GET("{id}")
    Observable<ResponseBody> getGuokrNewsData(@Path("id") String id);
}
