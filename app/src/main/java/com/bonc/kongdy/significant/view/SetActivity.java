package com.bonc.kongdy.significant.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bonc.kongdy.significant.view.fragment.SetFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetActivity extends ToolbarActivity {

    @Bind(R.id.exitbtn) Button exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ButterKnife.bind(this);

        initView();
        loadData();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.set));
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SetFragment()).commit();
        exitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()){
            case R.id.exitbtn:

                break;
            default:
                break;

        }

    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
