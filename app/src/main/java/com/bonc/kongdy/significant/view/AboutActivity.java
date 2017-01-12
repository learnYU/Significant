package com.bonc.kongdy.significant.view;

import android.os.Bundle;
import android.view.View;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bonc.kongdy.significant.view.fragment.AboutFragment;

import butterknife.ButterKnife;

public class AboutActivity extends ToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;

        setTitle(getString(R.string.about));
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();

        initView();
        loadData();
    }

    @Override
    public boolean canBack() {
        return true;
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
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);



    }
}
