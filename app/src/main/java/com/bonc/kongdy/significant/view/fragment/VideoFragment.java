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
import com.bonc.kongdy.significant.adapter.VideoAdapter;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.VideoBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.view.VideoInfoActivity;
import com.bonc.kongdy.significant.view.base.BaseFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class VideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener {

    @Bind(R.id.swiprefresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recyclerview)RecyclerView recyclerView;

    private VideoAdapter videoAdapter;
    private List<VideoBean.IssueListBean.ItemListBean> videolist = new ArrayList<>();
    public VideoFragment() {
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this,view);
        activity = getActivity();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        videoAdapter = new VideoAdapter(activity,videolist);
        recyclerView.setAdapter(videoAdapter);
//        recyclerView.addOnScrollListener(getOnBottomListener(layoutManager));
        videoAdapter.setItemClickListener(this);

        refreshLayout.setProgressViewOffset(false,0,100);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(Color.BLACK,Color.BLACK,Color.RED);
        refreshLayout.setOnRefreshListener(this);

        loadData();
        return view;
    }

    private void loadData() {
        NetWorkFactory.getVideoApi()
                .getVideoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                        if (videoBean != null) {
                            refreshLayout.setRefreshing(false);
                            Logger.d(videoBean.toString());
                            int size = videoBean.getIssueList().size();

                            for (int i = 0;i< size ;i++){
                                List<VideoBean.IssueListBean.ItemListBean> itemListBeen = videoBean.getIssueList().get(i).getItemList();
                                itemListBeen.remove(0);
                                videolist.addAll(itemListBeen);
                            }
//                            List<VideoBean.IssueListBean.ItemListBean> itemListBeen = videoBean.getIssueList().get(0).getItemList();
//                            itemListBeen.remove(0);
//                            videolist.addAll(itemListBeen);

                            videoAdapter.setDataList(videolist);

                        }
                    }
                });

    }

    @Override
    public void onRefresh() {
        videolist.clear();
        loadData();
    }

    @Override
    public void onItemClick(View view, Object data) {
        Intent intent = new Intent();
        intent.setClass(activity, VideoInfoActivity.class);
        intent.putExtra("data", (VideoBean.IssueListBean.ItemListBean)data);

        ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(activity,view,VideoInfoActivity.TAG);
        ActivityCompat.startActivity(activity,intent,options.toBundle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
