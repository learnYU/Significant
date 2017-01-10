package com.bonc.kongdy.significant.view;

import android.os.Bundle;
import android.view.View;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.db.LiteOrmUtils;
import com.bonc.kongdy.significant.model.StoryBean;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.litesuits.orm.LiteOrm;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.ButterKnife;

public class AboutActivity extends ToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        initView();
        loadData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        LiteOrm liteOrm = LiteOrmUtils.getCascadeInstance();

        List<StoryBean> list =  liteOrm.query(StoryBean.class);
        Logger.d(list.size()+"  " +list.toString());

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);



    }
}
