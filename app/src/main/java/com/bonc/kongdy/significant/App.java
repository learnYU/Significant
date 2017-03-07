package com.bonc.kongdy.significant;

import android.app.Application;
import android.content.Context;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.db.LiteOrmUtils;
import com.bonc.kongdy.significant.utlis.CrashHandler;
import com.bonc.kongdy.significant.utlis.Setting;
import com.litesuits.orm.db.DataBaseConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by kongdy on 2016/11/23.
 */
public class App extends Application{

    public static Context appContext = null;

    private Setting setting = null;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        CrashHandler.getInstance().init(getApplicationContext());

        DataBaseConfig config = new DataBaseConfig(appContext);
        config.debugged = true; // open the log
        config.dbVersion = 1; // set database version
        config.onUpdateListener = null; // set database update listener
        LiteOrmUtils.initDB(config);

        setting = Setting.getInstance();
        initWeatherIcon();

    }

    private void initWeatherIcon() {
        Logger.d("initWeatherIcon");
        setting.putInt("未知", R.mipmap.type_one_sunny);
        setting.putInt("晴", R.mipmap.type_one_sunny);
        setting.putInt("阴", R.mipmap.type_one_cloudy);
        setting.putInt("多云", R.mipmap.type_one_cloudy);
        setting.putInt("少云", R.mipmap.type_one_cloudy);
        setting.putInt("晴间多云", R.mipmap.type_one_cloudytosunny);
        setting.putInt("小雨", R.mipmap.type_one_light_rain);
        setting.putInt("中雨", R.mipmap.type_one_light_rain);
        setting.putInt("大雨", R.mipmap.type_one_heavy_rain);
        setting.putInt("阵雨", R.mipmap.type_one_thunderstorm);
        setting.putInt("雷阵雨", R.mipmap.type_one_thunder_rain);
        setting.putInt("霾", R.mipmap.type_one_fog);
        setting.putInt("雾", R.mipmap.type_one_fog);
        setting.putInt("雨加雪", R.mipmap.type_one_snow);
        setting.putInt("雪", R.mipmap.type_one_snow);
    }

}
