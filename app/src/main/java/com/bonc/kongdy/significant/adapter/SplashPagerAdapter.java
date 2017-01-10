package com.bonc.kongdy.significant.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kongdy on 2016/11/21.
 */
public class SplashPagerAdapter extends PagerAdapter {

    private Context context;
    private List<View> viewList;

    public SplashPagerAdapter(Context context, List<View> viewList) {
        this.context = context;
        this.viewList = viewList;
    }

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
        this.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        super.instantiateItem(container, position);
        View v=viewList.get(position);
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(viewList.get(position));
        return viewList.get(position);
    }


    @Override
    public int getCount() {
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
