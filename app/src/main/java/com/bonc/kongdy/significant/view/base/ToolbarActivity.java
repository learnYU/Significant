package com.bonc.kongdy.significant.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bonc.kongdy.basketball.R;

public abstract class ToolbarActivity extends BaseActivity {

    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAppBar = (AppBarLayout) findViewById(R.id.app_bar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        errorIv = (ImageView) findViewById(R.id.error_iv);
        errorIv.setOnClickListener(this);

        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException(
                    "The subclass of ToolbarActivity must contain a toolbar.");
        }
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToolbarClick();
            }
        });
        mToolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        setSupportActionBar(mToolbar);

        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }

    public void onToolbarClick() {}


    public boolean canBack() {
        return false;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }


    protected void hideOrShowToolbar() {
        mAppBar.animate()
               .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
               .setInterpolator(new DecelerateInterpolator(2))
               .start();
        mIsHidden = !mIsHidden;
    }


}
