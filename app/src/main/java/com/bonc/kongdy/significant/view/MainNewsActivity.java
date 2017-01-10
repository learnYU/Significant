package com.bonc.kongdy.significant.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.view.base.BaseFragment;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bonc.kongdy.significant.view.fragment.GirlsFragment;
import com.bonc.kongdy.significant.view.fragment.MainFragment;
import com.bonc.kongdy.significant.view.fragment.OtherFragment;
import com.bonc.kongdy.significant.view.fragment.VideoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainNewsActivity extends ToolbarActivity {

    @Bind(R.id.tab1)RelativeLayout tab1;
    @Bind(R.id.tab2)RelativeLayout tab2;
    @Bind(R.id.tab3)RelativeLayout tab3;
    @Bind(R.id.tab4)RelativeLayout tab4;

    @Bind(R.id.iv1)ImageView iv1;
    @Bind(R.id.iv2)ImageView iv2;
    @Bind(R.id.iv3)ImageView iv3;
    @Bind(R.id.iv4)ImageView iv4;

    @Bind(R.id.fragment_container) FrameLayout frameLayout;

    private FragmentManager fragmentManager;
    private BaseFragment mainFragment ,liveFragment, girlsFragment, otherFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main_news;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView() {

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);

        mainFragment = MainFragment.newInstance(this,"");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,mainFragment);
        transaction.commit();
    }
    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);

        iv1.setImageResource(R.mipmap.tab_news);
        iv2.setImageResource(R.mipmap.tab_video);
        iv3.setImageResource(R.mipmap.tab_pic);
        iv4.setImageResource(R.mipmap.tab_pic);

        switch (view.getId()){
            case R.id.tab1:
                setTitle("果壳");
                iv1.setImageResource(R.mipmap.tab_news_fill);
                if(mainFragment == null){
                    transaction.add(R.id.fragment_container,mainFragment);
                }else{
                    transaction.show(mainFragment);
                }
                break;
            case R.id.tab2:
                setTitle("NBA LIVE");
                iv2.setImageResource(R.mipmap.tab_video_fill);
                if(liveFragment == null){
                    liveFragment = VideoFragment.newInstance();
                    transaction.add(R.id.fragment_container,liveFragment);
                }else{
                    transaction.show(liveFragment);
                }

                break;
            case R.id.tab3:
                setTitle("GIRLS");
                iv3.setImageResource(R.mipmap.tab_pic_fill);
                if(girlsFragment == null){
                    girlsFragment = GirlsFragment.newInstance(this,"param");
                    transaction.add(R.id.fragment_container,girlsFragment);
                }else{
                    transaction.show(girlsFragment);
                }

                break;
            case R.id.tab4:
                setTitle("Other");
                iv4.setImageResource(R.mipmap.tab_pic_fill);
                if(otherFragment == null){
                    otherFragment = OtherFragment.newInstance();
                    transaction.add(R.id.fragment_container,otherFragment);
                }else{
                    transaction.show(otherFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(mainFragment!=null){
            transaction.hide(mainFragment);
        }
        if(liveFragment!=null){
            transaction.hide(liveFragment);
        }
        if(girlsFragment!=null){
            transaction.hide(girlsFragment);
        }
        if(otherFragment!=null){
            transaction.hide(otherFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


}
