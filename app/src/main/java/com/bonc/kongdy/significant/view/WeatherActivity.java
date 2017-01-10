package com.bonc.kongdy.significant.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.adapter.WeatherAdapter;
import com.bonc.kongdy.significant.model.Weather;
import com.bonc.kongdy.significant.model.WeatherAPI;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.utlis.Setting;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.orhanobut.logger.Logger;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WeatherActivity extends ToolbarActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swiprefresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    private WeatherAdapter adapter;
    private Weather weather;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    @Override
    protected void loadData() {
        NetWorkFactory.getWeatherApi()
                .queryWeather(Setting.getCity(),getString(R.string.weather_API_Key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherAPI>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                        showToast(e.getMessage());
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(WeatherAPI weather) {
                        refreshLayout.setRefreshing(false);
                        adapter.setWeatherData(weather.mHeWeatherDataService30s.get(0));
                    }
                });
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.title_weather));

        weather = new Weather();
        refreshLayout.setProgressViewOffset(false,0,100);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        adapter = new WeatherAdapter(context,weather);
        recyclerView.setAdapter(adapter);


        Serializable serializable = getIntent().getSerializableExtra("weatherData");
        if (serializable != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    weather = (Weather) getIntent().getSerializableExtra("weatherData");
                    adapter.setWeatherData(weather);
                    refreshLayout.setRefreshing(false);
                }
            },getResources().getInteger(R.integer.飞一会));
        } else {
            loadData();
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public void onRefresh() {
        weather = null;
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
