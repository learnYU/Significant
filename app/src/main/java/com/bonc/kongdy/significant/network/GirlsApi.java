package com.bonc.kongdy.significant.network;

import com.bonc.kongdy.significant.model.GirlsBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kongdy on 2016/11/23.
 */
public interface GirlsApi {
    @GET("data/福利/" + NetWorkFactory.girlsSize + "/{page}")
    Observable<GirlsBean> getMeizhiData(@Path("page") int page);
//    http://gank.io/api/data/福利/10/1
}
