package com.bonc.kongdy.significant.utlis;

import android.content.Context;
import android.content.SharedPreferences;

import com.bonc.kongdy.significant.App;

/**
 * Created by kongdy on 2016/11/25.
 */
public class Setting {

    private static Setting instance = null;

    private static SharedPreferences sp = null;
    private static String city;

    private Setting(Context context) {
        sp = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public static Setting getInstance() {
        if (instance == null){
            instance = new Setting(App.appContext);
        }
        return instance;
    }

    public static String getCity() {
        return sp.getString("city","北京");
    }

    public static void setCity(String city) {
        sp.edit().putString("city",city).apply();
    }

    public Setting putInt(String key, int value){
        sp.edit().putInt(key,value).apply();
        return this;
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }


}
