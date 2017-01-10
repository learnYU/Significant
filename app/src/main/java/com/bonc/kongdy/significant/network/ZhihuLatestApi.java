package com.bonc.kongdy.significant.network;

import com.bonc.kongdy.significant.model.StoryExtraBean;
import com.bonc.kongdy.significant.model.StoryNewsBean;
import com.bonc.kongdy.significant.model.ZhihuBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kongdy on 2016/11/23.
 */
public interface ZhihuLatestApi {

    @GET("news/{parm}")
    Observable<ZhihuBean> getZhihuData(@Path("parm") String parm);

    @GET("news/{parm}/{date}")
    Observable<ZhihuBean> getZhihuMoreData(@Path("parm") String parm,@Path("date") String date);

    @GET("story-extra/{id}")
    Observable<StoryExtraBean> getZhihuExtraData(@Path("id") String id);

    @GET("news/{id}")
    Observable<StoryNewsBean> getZhihuNewsData(@Path("id") String id);

    //    http://news-at.zhihu.com/api/4/news/latest/  //最新新闻
    //    http://news-at.zhihu.com/api/4/news/before/20131119  过往新闻
    //    http://news-at.zhihu.com/api/4/story-extra/#{id} 新闻额外信息
    //    http://news-at.zhihu.com/api/4/news/3892357

}
