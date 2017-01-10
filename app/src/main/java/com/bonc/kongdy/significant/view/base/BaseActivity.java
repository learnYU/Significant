package com.bonc.kongdy.significant.view.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.db.LiteOrmUtils;
import com.bonc.kongdy.significant.utlis.Setting;
import com.litesuits.orm.LiteOrm;
import com.orhanobut.logger.Logger;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kongdy on 2016/11/21.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context context;
    protected Setting setting;
    protected ImageView errorIv;

    protected CompositeSubscription subscription = new CompositeSubscription();
    protected LiteOrm liteOrm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        setting = Setting.getInstance();
        liteOrm = LiteOrmUtils.getCascadeInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public CompositeSubscription getSubscription() {
        if (subscription == null){
            subscription = new CompositeSubscription();
        }
        return subscription;
    }

    public void addSubscription(Subscription s) {
        if (this.subscription == null) {
            this.subscription = new CompositeSubscription();
        }

        this.subscription.add(s);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null){
//            subscription.clear();
            subscription.unsubscribe();
        }
    }

    protected  void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.error_iv:
                loadData();
                break;
            default:
                break;
        }
    }

    abstract protected int provideContentViewId();
    abstract protected void loadData();
    abstract protected void initView();

}
