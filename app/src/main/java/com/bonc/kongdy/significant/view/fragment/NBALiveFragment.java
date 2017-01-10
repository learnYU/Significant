package com.bonc.kongdy.significant.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.bonc.kongdy.significant.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NBALiveFragment extends BaseFragment {

    private static BaseActivity activity;

    @Bind(R.id.webview) WebView webview;

    public static NBALiveFragment newInstance(BaseActivity activity) {

        Bundle args = new Bundle();
        NBALiveFragment.activity = activity;

        NBALiveFragment fragment = new NBALiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_nbalive, container, false);
        ButterKnife.bind(this,view);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webview.setWebChromeClient(new ChromeClient());
        webview.setWebViewClient(new WebClient());

        webview.loadUrl(getResources().getString(R.string.nbalive_url));

        return view;
    }

    private class ChromeClient extends WebChromeClient {

        @Override public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
//            mProgressbar.setProgress(newProgress);
//            if (newProgress == 100) {
//                mProgressbar.setVisibility(View.GONE);
//            } else {
//                mProgressbar.setVisibility(View.VISIBLE);
//            }
        }

        @Override public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            activity.setTitle(title);
        }

    }

    private class WebClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (webview != null)
            webview.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webview != null)
            webview.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webview != null)
            webview.destroy();
        ButterKnife.unbind(this);
    }


}
