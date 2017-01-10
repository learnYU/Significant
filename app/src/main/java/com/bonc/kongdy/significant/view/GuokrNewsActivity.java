package com.bonc.kongdy.significant.view;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewCompat;
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
import com.bonc.kongdy.significant.model.GuokrBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bonc.kongdy.significant.utlis.MainUtils;
import com.bonc.kongdy.significant.utlis.parser.GuokrXMLParser;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GuokrNewsActivity extends ToolbarActivity {

    public static final String TAG = "GuokrNewsActivity";
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.title_iv)
    ImageView titleIv;
    @Bind(R.id.contentlayout)
    View contentView;
    @Bind(R.id.tv_title)
    TextSwitcher textSwitcher;

    private GuokrBean.ResultBean resultBean;
    private String url = "";
    private String newsTitle="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ButterKnife.bind(this);

        resultBean = (GuokrBean.ResultBean) getIntent().getSerializableExtra("data");
        newsTitle = resultBean.getTitle();
        url = resultBean.getUrl();

        Logger.d(resultBean.toString());
        initView();
        loadData();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guokr_news;
    }

    @Override
    protected void loadData() {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();

        NetWorkFactory.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Logger.d(body);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        GuokrXMLParser xmlParser = new GuokrXMLParser(body);
                        String data = xmlParser.getEndStr();
                        webView.loadDataWithBaseURL("file:///android_asset/",
                                "<link rel=\"stylesheet\" type=\"text/css\" href=\"guokr.css\" />"
                                + data, "text/html", "utf-8", null);
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
        ViewCompat.setTransitionName(contentView,TAG);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public View makeView() {
                final TextView textView = new TextView(context);
                textView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                textView.setTextAppearance(R.style.WebTitle);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
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

        Glide.with(context).load(resultBean.getSmall_image()).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                titleIv.setImageBitmap(resource);
                contentView.setBackgroundColor(MainUtils.getImageColor(resource));
            }
        });

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
                // return true 屏蔽webview页面超链接
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
                            List<GuokrBean.ResultBean> list = liteOrm.query(GuokrBean.ResultBean.class);
                            boolean b = list.contains(resultBean);
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
                                        liteOrm.save(resultBean);
                                        showToast(getString(R.string.follow));
                                    } else {
                                        liteOrm.delete(resultBean);
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
