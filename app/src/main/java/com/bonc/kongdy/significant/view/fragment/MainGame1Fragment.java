package com.bonc.kongdy.significant.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.adapter.GuokrAdapter;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.GuokrBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.bonc.kongdy.significant.view.GuokrNewsActivity;
import com.bonc.kongdy.significant.view.base.BaseFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainGame1Fragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener {

    private static final String retrieveType = "by_channel";
    private String channelKey;

    @Bind(R.id.swiprefresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    private GuokrAdapter guokrAdapter;
    private List<GuokrBean.ResultBean> dataList =new ArrayList<>();

//    public static MainGame1Fragment newInstance(BaseActivity activity) {
//        MainGame1Fragment fragment = new MainGame1Fragment();
//        MainGame1Fragment.activity =activity;
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        if (getArguments() != null) {
            channelKey = getArguments().getString("channel_tag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_game1, container, false);
        ButterKnife.bind(this,view);

        guokrAdapter = new GuokrAdapter(activity ,dataList);
        guokrAdapter.setItemClickListener(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(guokrAdapter);

        refreshLayout.setProgressViewOffset(false,0,100);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.GREEN);
        refreshLayout.setOnRefreshListener(this);

        Logger.d(channelKey);
        loadData();
        return view;
    }

    private void loadData() {
        NetWorkFactory.getGuoKrApi()
                .getGuokrData(retrieveType,channelKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GuokrBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(final GuokrBean guokrBean) {
                        if (guokrBean != null) {
                            refreshLayout.setRefreshing(false);
                            dataList = guokrBean.getResult();
                            guokrAdapter.setDataList(dataList);
                        }
                    }
                });


    }

    @Override
    public void onItemClick(View view, Object data) {
        GuokrBean.ResultBean resultBean =(GuokrBean.ResultBean)data;

        Intent intent = new Intent(activity,GuokrNewsActivity.class);
        intent.putExtra("data",resultBean);
        ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view, GuokrNewsActivity.TAG);
        ActivityCompat.startActivity(activity,intent,options.toBundle());
    }

    @Override
    public void onRefresh() {
        dataList.clear();
        loadData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
