package com.bonc.kongdy.significant.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kongdy on 2016/11/23.
 */
public class BaseFragment extends Fragment {
    protected Activity activity;

    private CompositeSubscription subscription;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null){
            subscription.unsubscribe();
        }
    }

    protected  void showToast(String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
