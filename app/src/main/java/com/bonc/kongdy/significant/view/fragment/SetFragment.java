package com.bonc.kongdy.significant.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.utlis.Setting;
import com.bonc.kongdy.significant.utlis.lrucache.FileSizeUtil;
import com.bonc.kongdy.significant.view.AboutActivity;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SetFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private Activity activity;
    private Setting setting;

    private Preference clearCache;
    private Preference about;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.set);

        activity = getActivity();

        clearCache = findPreference("clear_cache");
        about = findPreference("about");

        File file = Glide.getPhotoCacheDir(getActivity());
        clearCache.setSummary(FileSizeUtil.getAutoFileOrFilesSize(activity ,file));


        clearCache.setOnPreferenceClickListener(this);
        about.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == clearCache) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    Glide.get(activity).clearDiskCache();
                    File file = Glide.getPhotoCacheDir(getActivity());
                    String size = FileSizeUtil.getAutoFileOrFilesSize(activity ,file);
                    subscriber.onNext(size);
                }}).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            Logger.e(e.getMessage());
                            Toast.makeText(activity,R.string.clean_cache,Toast.LENGTH_SHORT).show();
                            clearCache.setSummary("0.00 B");
                        }
                        @Override
                        public void onNext(String size) {
                            Glide.get(activity).clearMemory();
                            clearCache.setSummary(size);
                            Toast.makeText(activity,R.string.clean_cache,Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (preference == about) {
            startActivity(new Intent(activity, AboutActivity.class));
        }

        return false;
    }


}
