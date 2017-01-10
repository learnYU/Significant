package com.bonc.kongdy.significant.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.adapter.RecycleAdapter;
import com.bonc.kongdy.significant.adapter.SplashPagerAdapter;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.StoryBean;
import com.bonc.kongdy.significant.model.TopStoryBean;
import com.bonc.kongdy.significant.model.Weather;
import com.bonc.kongdy.significant.model.WeatherAPI;
import com.bonc.kongdy.significant.model.ZhihuBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.service.LocalService;
import com.bonc.kongdy.significant.utlis.MainUtils;
import com.bonc.kongdy.significant.utlis.Setting;
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 1;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.banner)ViewPager bannerpager;
    @Bind(R.id.app_bar)AppBarLayout barLayout;
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.toolbar_layout)CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.swiprefresh)SwipeRefreshLayout refreshLayout;
    @Bind(R.id.drawer_layout)DrawerLayout drawerLayout;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.recyclerview)RecyclerView recyclerView;

    private RelativeLayout headerView;
    private ImageView weatherIcon;
    private TextView tempTV;

    private SplashPagerAdapter pagerAdapter;
    private RecycleAdapter recycleAdapter;
    private List<StoryBean> newsList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private ImageView view1,view2,view3;

    private Weather mWeather;

    private String zhihuUrl;
    private String zhihuDate = MainUtils.getCurrentDay();

    private boolean mIsFirstTimeTouchBottom = true;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;

        setPermission();

        initView();

        loadData();

        loadWeatherData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    private void setPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }

        startService(new Intent(this, LocalService.class));
    }

    protected void initView() {
        setSupportActionBar(toolbar);
        barLayout.setExpanded(true,true);

        // 侧拉框
        navigationView.setNavigationItemSelectedListener(this);
        headerView = (RelativeLayout) navigationView.inflateHeaderView(R.layout.nav_header_main);
        tempTV = (TextView) headerView.findViewById(R.id.temp);
        weatherIcon = (ImageView) headerView.findViewById(R.id.weather_icon);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout ,toolbar ,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

//        toolbar.setLogo(R.mipmap.type_one_sunny);
//        toolbar.setSubtitle("Afternoon");
        toolbarLayout.setTitle(getString(R.string.maintitle));
        toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);

        toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        view1 = (ImageView) LayoutInflater.from(context).inflate(R.layout.pager_item,null);
        view2 = (ImageView) LayoutInflater.from(context).inflate(R.layout.pager_item,null);
        view3 = (ImageView) LayoutInflater.from(context).inflate(R.layout.pager_item,null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        pagerAdapter = new SplashPagerAdapter(context, viewList);
        pagerAdapter.setViewList(viewList);
        bannerpager.setAdapter(pagerAdapter);

        recycleAdapter = new RecycleAdapter(context, newsList);
        recycleAdapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Intent intent = new Intent(MainActivity.this,ZHNewsActivity.class);
                intent.putExtra("data",(StoryBean)data);
                ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,view,ZHNewsActivity.TAG);
                ActivityCompat.startActivity(MainActivity.this,intent,options.toBundle());
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.addOnScrollListener(getOnBottomListener(layoutManager));

        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.GREEN);
        refreshLayout.setOnRefreshListener(this);

        fab.setOnClickListener(this);
        headerView.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        if (zhihuDate.equals(MainUtils.getCurrentDay())) {
            subscription.add(
                    NetWorkFactory.getZhihuApi()
                    .getZhihuData("latest")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ZhihuBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            showToast(e.getMessage());
                        }

                        @Override
                        public void onNext(ZhihuBean zhihuBean) {
                            if (zhihuBean != null) {
                                List<StoryBean> storyBeans = zhihuBean.getStories();
                                if (zhihuBean.getTop_stories() != null) {
                                    List<TopStoryBean> topstoryBeans = zhihuBean.getTop_stories();
                                    Glide.with(context).load(topstoryBeans.get(0).getImage()).centerCrop().into(view1);
                                    Glide.with(context).load(topstoryBeans.get(1).getImage()).centerCrop().into(view2);
                                    Glide.with(context).load(topstoryBeans.get(2).getImage()).centerCrop().into(view3);
                                    pagerAdapter.notifyDataSetChanged();
                                }

                                Logger.d(storyBeans.size() + zhihuBean.toString());

                                refreshLayout.setRefreshing(false);
                                newsList.addAll(storyBeans);
                                recycleAdapter.setDataList(newsList);
                            }

                        }
                    })
            );
        }else {
            subscription.add(
                    NetWorkFactory.getZhihuApi()
                    .getZhihuMoreData("before", zhihuDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ZhihuBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.e(e.getMessage());
                        }

                        @Override
                        public void onNext(ZhihuBean zhihuBean) {
                            if (zhihuBean != null) {
                                List<StoryBean> storyBeans = zhihuBean.getStories();
                                Logger.d(storyBeans.size() + zhihuBean.toString());
                                newsList.addAll(storyBeans);
                                refreshLayout.setRefreshing(false);
                                recycleAdapter.setDataList(newsList);
                            }

                        }
                    })
            );
        }

    }

    private void loadWeatherData() {
        subscription.add(
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
                        Logger.e(e.getMessage());
                        showToast(getString(R.string.weather_error_msg));

                        refreshLayout.setRefreshing(false);
                        Glide.with(context).load(R.mipmap.type_none).fitCenter().into(weatherIcon);
                        tempTV.setText(getString(R.string.weather_error_msg));

                    }
                    @Override
                    public void onNext(WeatherAPI weather) {
                        mWeather = weather.mHeWeatherDataService30s.get(0);
                        refreshLayout.setRefreshing(false);

                        Glide.with(context).load(Setting.getInstance().getInt(mWeather.now.cond.txt,R.mipmap.type_one_sunny)).centerCrop().into(weatherIcon);
                        tempTV.setText(mWeather.now.cond.txt +
                                mWeather.dailyForecast.get(0).tmp.min + " ~ " +
                                mWeather.dailyForecast.get(0).tmp.max);
                        Logger.d(mWeather.toString());
                    }
                })
        );
    }

    @Override
    public void onRefresh() {
        newsList.clear();
        zhihuDate = MainUtils.getCurrentDay();

        loadData();
        loadWeatherData();
    }

    RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[1])[0] >= recycleAdapter.getItemCount()-1;

                if (!refreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        refreshLayout.setRefreshing(true);

                        zhihuDate = MainUtils.getLastDay(zhihuDate);
                        Snackbar.make(fab,"Loading "+zhihuDate,Snackbar.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.fab:
               Snackbar.make(fab," 去看更多 。 ",Snackbar.LENGTH_LONG).setAction("action >", new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent();
                       toMoreNews(intent);
                   }
               }).show();
               break;
           case R.id.header_background:
               Intent intent = new Intent(context,WeatherActivity.class);
               intent.putExtra("weatherData",mWeather);
               startActivity(intent);
               drawerLayout.closeDrawer(GravityCompat.START);
           default:
               break;
       }
    }

    /**
     * 侧栏框 点击事件
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case R.id.more_news:
                toMoreNews(intent);
                break;
            case R.id.nav_set:
                startActivity(new Intent(MainActivity.this,SetActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void toMoreNews(Intent intent){
        intent.setClass(context,MainNewsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(fab,getString(R.string.exit),Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
