package com.bonc.kongdy.significant.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OtherFragment extends BaseFragment {

    public static String [] follow_title={"日报","新闻","视频","美图"};

    @Bind(R.id.tab_layout) TabLayout tabLayout;

    @Bind(R.id.follow_vp) ViewPager viewPager;
    public OtherFragment() {
    }

    public static OtherFragment newInstance() {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        ButterKnife.bind(this,view);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment fragment = new FollowFragment();
                Bundle bundle = new Bundle();
                bundle.putString(FollowFragment.FollowFragmentTAG,follow_title[position]);
                fragment.setArguments(bundle);

                return fragment;
            }

            @Override
            public int getCount() {
                return follow_title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return follow_title[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
