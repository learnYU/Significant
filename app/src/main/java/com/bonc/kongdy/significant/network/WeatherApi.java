package com.bonc.kongdy.significant.network;

import com.bonc.kongdy.significant.model.WeatherAPI;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kongdy on 2016/11/25.
 */
public interface WeatherApi {
    @GET("weather")
    Observable<WeatherAPI> queryWeather(@Query("city") String city, @Query("key") String key);
}
