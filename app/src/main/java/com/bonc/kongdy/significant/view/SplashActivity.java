package com.bonc.kongdy.significant.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.adapter.SplashPagerAdapter;
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.splash_iv)
    ImageView imageView;
    @Bind(R.id.splash_vp)
    ViewPager viewPager;

    //第一次下载展现viewpager
    boolean firstLoad = false;

    private Context context;
    private SplashPagerAdapter adapter;

    private List<View> viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ButterKnife.bind(this);

        if (firstLoad) {
            imageView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);

        } else {
            imageView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        }

        View pagerview1 = LayoutInflater.from(context).inflate(R.layout.pager_item,null);
        viewList = new ArrayList<>();
        viewList.add(pagerview1);

        adapter = new SplashPagerAdapter(context,viewList);

        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);

        imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        initView();
        loadData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void loadData() {

        imageView.setBackgroundResource(R.mipmap.kobe);

        //        Observable.create(new Observable.OnSubscribe<String>() {
        //            @Override
        //            public void call(Subscriber<? super String> subscriber) {
        //                subscriber.onNext("跳入main");
        //            }
        //        }).subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Observer<String>() {
        //                    @Override
        //                    public void onCompleted() {
        //                        Logger.d("onCompleted");
        //                        startActivity(new Intent(context,MainActivity.class));
        //                    }
        //
        //                    @Override
        //                    public void onError(Throwable e) {
        //                        Logger.d("onError");
        //                        startActivity(new Intent(context,MainActivity.class));
        //                    }
        //
        //                    @Override
        //                    public void onNext(String s) {
        //
        //                        Logger.d("onNext");
        //                        try {
        //                            Thread.sleep(10000);
        //                        } catch (InterruptedException e) {
        //                            e.printStackTrace();
        //                        }
        //
        //                        startActivity(new Intent(context,MainActivity.class));
        //                        SplashActivity.this.finish();
        //                    }
        //                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context,MainActivity.class));
                SplashActivity.this.finish();
            }
        },2000);

    }

    @Override
    protected void initView() {

    }

    /**
     * 当activity 完全启动的时候调用
     * 一个正常的Activity生命周期如下：onCreate  onStart onPostCreate onResume  onPostResume onPause  onStop  onDestory
     **/
    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        Logger.d("onPostCreate");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
