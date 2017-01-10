package com.bonc.kongdy.significant.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.model.StoryBean;
import com.bonc.kongdy.significant.model.StoryNewsBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.utlis.MainUtils;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ZHNewsActivity extends ToolbarActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "ZHNewsActivity";
    @Bind(R.id.webview) WebView webView;
    @Bind(R.id.title_iv) ImageView titleIv;
    @Bind(R.id.contentlayout) View contentView;
    @Bind(R.id.tv_title) TextSwitcher textSwitcher;
//    @Bind(R.id.swiprefresh)SwipeRefreshLayout refreshLayout;

    private String id = "",newsTitle="";
    private StoryBean storyBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ButterKnife.bind(this);

        storyBean = (StoryBean) getIntent().getSerializableExtra("data");
        id = storyBean.getId();
        newsTitle = storyBean.getTitle();

        initView();
        loadData();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_zhnews;
    }

    @Override
    protected void loadData() {
        subscription.add(
                NetWorkFactory.getZhihuApi()
                .getZhihuNewsData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryNewsBean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
//                        refreshLayout.setRefreshing(false);
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(StoryNewsBean extraBean) {
                        if (extraBean != null){
                            Logger.d(extraBean.toString());
                            setTitle(extraBean.getTitle());

                            Glide.with(context).load(extraBean.getImage()).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    titleIv.setImageBitmap(resource);
                                    contentView.setBackgroundColor(MainUtils.getImageColor(resource));
                                }
                            });

                            webView.loadDataWithBaseURL("file:///android_asset/",
                                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"
                                            + extraBean.getBody(), "text/html", "utf-8", null);
                        }
                    }
                })
        );
    }

    @Override
    public void onRefresh() {
    }

    @Override
    protected void initView() {
        ViewCompat.setTransitionName(contentView,TAG);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressLint("NewApi")
            @Override
            public View makeView() {
                final TextView textView = new TextView(context);
                textView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                textView.setTextAppearance(R.style.WebTitle);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//                textView.setGravity();
                textView.setSingleLine(true);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                } ,1800);
                return textView;
            }
        });
        textSwitcher.setInAnimation(this, android.R.anim.fade_in);
        textSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        textSwitcher.setText(newsTitle);

//        refreshLayout.setProgressViewOffset(false,0,100);
//        refreshLayout.setRefreshing(true);
//        refreshLayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.GREEN);
//        refreshLayout.setOnRefreshListener(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        // 开启缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                displayNetworkError();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
        });

    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.follow_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.follow){
            getSubscription().add(Observable.create(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    List<StoryBean> list = liteOrm.query(StoryBean.class);
                    Logger.d(list.toString());
                    Logger.d(storyBean);
                    boolean b = list.contains(storyBean);
                    Logger.d(b);
                    subscriber.onNext(b);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            Logger.d("error");
                        }

                        @Override
                        public void onNext(Boolean b) {
                            if (!b) {
                                liteOrm.save(storyBean);
                                showToast(getString(R.string.follow));
                            } else {
                                liteOrm.delete(storyBean);
                                showToast(getString(R.string.no_follow));
                            }
                        }
                    })
            );
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null)
            webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null)
            webView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null)
            webView.destroy();
        ButterKnife.unbind(this);
    }

}
