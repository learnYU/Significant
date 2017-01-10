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
import com.bonc.kongdy.significant.adapter.GirlListAdapter;
import com.bonc.kongdy.significant.adapter.GuokrAdapter;
import com.bonc.kongdy.significant.adapter.RecycleAdapter;
import com.bonc.kongdy.significant.adapter.VideoAdapter;
import com.bonc.kongdy.significant.db.LiteOrmUtils;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.GirlBean;
import com.bonc.kongdy.significant.model.GuokrBean;
import com.bonc.kongdy.significant.model.StoryBean;
import com.bonc.kongdy.significant.view.GuokrNewsActivity;
import com.bonc.kongdy.significant.view.PictureActivity;
import com.bonc.kongdy.significant.view.VideoInfoActivity;
import com.bonc.kongdy.significant.view.ZHNewsActivity;
import com.bonc.kongdy.significant.view.base.BaseFragment;
import com.bonc.kongdy.significant.model.VideoBean;
import com.litesuits.orm.LiteOrm;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String FollowFragmentTAG = "FollowFragment";
    private String title = "日报";

    @Bind(R.id.swiprefresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    private List<StoryBean> storyBeanList = new ArrayList<>();
    private List<GuokrBean.ResultBean> resultBeanList = new ArrayList<>();
    private List<GirlBean> girlBeanList = new ArrayList<>();
    private List<VideoBean.IssueListBean.ItemListBean> videolist = new ArrayList<>();

    private LiteOrm liteOrm = LiteOrmUtils.getCascadeInstance();

    public FollowFragment() {
    }

    public static FollowFragment newInstance() {
        FollowFragment fragment = new FollowFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(FollowFragmentTAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        ButterKnife.bind(this,view);
        activity = getActivity();

        refreshLayout.setProgressViewOffset(false,0,100);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(Color.BLACK,Color.WHITE,Color.GRAY);
        refreshLayout.setOnRefreshListener(this);

        if (title.equals("日报")) {
            initDaily();
        } else if (title.equals("新闻")) {
            initNews();
        } else if (title.equals("视频")) {
            initVideo();
        } else if (title.equals("美图")) {
            initPic();
        }


        return view;
    }

    private void initVideo() {
        final VideoAdapter adapter = new VideoAdapter(activity,videolist);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Intent intent = new Intent();
                intent.setClass(activity, VideoInfoActivity.class);
                intent.putExtra("data", (VideoBean.IssueListBean.ItemListBean)data);

                ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,VideoInfoActivity.TAG);
                ActivityCompat.startActivity(activity,intent,options.toBundle());
            }
        });

        getSubscription().add(Observable.create(new Observable.OnSubscribe<List<VideoBean.IssueListBean.ItemListBean>>() {
            @Override
            public void call(Subscriber<? super List<VideoBean.IssueListBean.ItemListBean>> subscriber) {
                List<VideoBean.IssueListBean.ItemListBean> list =  liteOrm.query(VideoBean.IssueListBean.ItemListBean.class);
                Logger.d(list.size()+"  " +list.toString());

                if (list == null){
                    subscriber.onError(new Throwable(getString(R.string.error_msg)));
                }

                subscriber.onNext(list);
            }}).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoBean.IssueListBean.ItemListBean>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        refreshLayout.setRefreshing(false);
                        Logger.d("error");
                    }

                    @Override
                    public void onNext(List<VideoBean.IssueListBean.ItemListBean> resultBeen) {
                        if (resultBeen != null) {
                            videolist.clear();
                            videolist.addAll(resultBeen);
                            adapter.setDataList(videolist);
                            refreshLayout.setRefreshing(false);
                        }
                    }
                })
        );

    }

    private void initPic() {
        final GirlListAdapter adapter = new GirlListAdapter(activity,girlBeanList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Intent intent = new Intent();
                intent.setClass(activity, PictureActivity.class);
                intent.putExtra("data", (GirlBean) data);
                ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,PictureActivity.TRANSIT_PIC);
                ActivityCompat.startActivity(activity,intent,options.toBundle());
            }
        });

        getSubscription().add(Observable.create(new Observable.OnSubscribe<List<GirlBean>>() {
            @Override
            public void call(Subscriber<? super List<GirlBean>> subscriber) {
                List<GirlBean> list =  liteOrm.query(GirlBean.class);
                Logger.d(list.size()+"  " +list.toString());

                subscriber.onNext(list);
            }}).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GirlBean>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        refreshLayout.setRefreshing(false);
                        Logger.d("error");
                    }

                    @Override
                    public void onNext(List<GirlBean> resultBeen) {
                        if (resultBeen != null) {
                            girlBeanList.clear();
                            girlBeanList.addAll(resultBeen);
                            adapter.setList(resultBeen);
                            refreshLayout.setRefreshing(false);
                        }
                    }
                })
        );
    }

    /**
     * 加载新闻页收藏
     */
    private void initNews() {

        final GuokrAdapter adapter = new GuokrAdapter(activity,resultBeanList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Intent intent = new Intent(activity,GuokrNewsActivity.class);
                intent.putExtra("data",(GuokrBean.ResultBean)data);
                ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view, GuokrNewsActivity.TAG);
                ActivityCompat.startActivity(activity,intent,options.toBundle());
            }
        });

        getSubscription().add(Observable.create(new Observable.OnSubscribe<List<GuokrBean.ResultBean>>() {
            @Override
            public void call(Subscriber<? super List<GuokrBean.ResultBean>> subscriber) {
                List<GuokrBean.ResultBean> list =  liteOrm.query(GuokrBean.ResultBean.class);
                Logger.d(list.size()+"  " +list.toString());

                subscriber.onNext(list);
            }}).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GuokrBean.ResultBean>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        refreshLayout.setRefreshing(false);
                        Logger.d("error");
                    }

                    @Override
                    public void onNext(List<GuokrBean.ResultBean> resultBeen) {
                        if (resultBeen != null) {
                            resultBeanList.clear();
                            resultBeanList.addAll(resultBeen);
                            adapter.setDataList(resultBeanList);
                            refreshLayout.setRefreshing(false);
                        }
                    }
                })
        );

    }

    /**
     * 加载日报收藏
     */
    private void initDaily() {
        final RecycleAdapter adapter = new RecycleAdapter(activity,storyBeanList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                Intent intent = new Intent(activity,ZHNewsActivity.class);
                intent.putExtra("data",(StoryBean)data);
                ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,ZHNewsActivity.TAG);
                ActivityCompat.startActivity(activity,intent,options.toBundle());
            }
        });

        getSubscription().add(Observable.create(new Observable.OnSubscribe<List<StoryBean>>() {
            @Override
            public void call(Subscriber<? super List<StoryBean>> subscriber) {
                List<StoryBean> list =  liteOrm.query(StoryBean.class);
                Logger.d(list.size()+"  " +list.toString());

                subscriber.onNext(list);
//                subscriber.onError(new Throwable("收藏数据获取失败 TAT"));
            }}).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<StoryBean>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<StoryBean> storyBean) {
                        if (storyBean != null) {
                            storyBeanList.clear();
                            storyBeanList.addAll(storyBean);
                            adapter.setDataList(storyBeanList);
//                            adapter.notifyDataSetChanged();

                            refreshLayout.setRefreshing(false);
                        }
                    }
                })
        );

    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (title.equals("日报")) {
            initDaily();
        } else if (title.equals("新闻")) {
            initNews();
        } else if (title.equals("视频")) {
            initVideo();
        } else if (title.equals("美图")) {
            initPic();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
