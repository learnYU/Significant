package com.bonc.kongdy.significant.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.adapter.GirlListAdapter;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.GirlBean;
import com.bonc.kongdy.significant.model.GirlsBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.bonc.kongdy.significant.view.PictureActivity;
import com.bonc.kongdy.significant.view.base.BaseFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GirlsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener {
    private static final String ARG_PARAM2 = "param2";

    private static final int PRELOAD_SIZE = 8;

    private static BaseActivity activity;

    @Bind(R.id.swiprefresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.girls_gv)RecyclerView girlsGv;

    private GirlListAdapter girlListAdapter;
    private List<GirlBean> girlBeanList;

    private int page=1;
    private boolean mIsFirstTimeTouchBottom = true;

    public GirlsFragment() {
    }

    public static  GirlsFragment newInstance(BaseActivity activity, String param2) {
        GirlsFragment fragment = new GirlsFragment();
        GirlsFragment.activity = activity;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girls, container, false);
        ButterKnife.bind(this,view);

        girlBeanList = new ArrayList<>();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        girlsGv.setLayoutManager(layoutManager);
        girlListAdapter = new GirlListAdapter(activity,girlBeanList);
        girlsGv.setAdapter(girlListAdapter);
        girlsGv.addOnScrollListener(getOnBottomListener(layoutManager));
        girlListAdapter.setItemClickListener(this);

        refreshLayout.setProgressViewOffset(false,0,100);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(Color.BLACK,Color.BLACK,Color.RED);
        refreshLayout.setOnRefreshListener(this);

        loadData();

        return view;
    }

    private void loadData() {
        getSubscription().add(
                NetWorkFactory.getGirlsApi()
                .getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(activity,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(final GirlsBean girlsBean) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                                if (girlsBean.getError().equals("false")){
                                    girlBeanList.addAll(girlsBean.getResults());
                                    girlListAdapter.setList(girlBeanList);
                                }
                            }
                        },1000);

                    }
                })
        );

    }

    RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                                girlListAdapter.getItemCount() - PRELOAD_SIZE;

                Logger.d("onScrolled"+isBottom);

                if (!refreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        refreshLayout.setRefreshing(true);
                        page += 1;

                        loadData();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        girlBeanList.clear();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, Object data) {
        Intent intent = new Intent();
        intent.setClass(activity, PictureActivity.class);
        intent.putExtra("data", (GirlBean) data);

        ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,PictureActivity.TRANSIT_PIC);
        ActivityCompat.startActivity(activity,intent,options.toBundle());
    }


}
