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
import com.bonc.kongdy.significant.view.base.BaseActivity;
import com.bonc.kongdy.significant.view.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment {

    public static String [] channel_tag={"hot","frontier","review","interview","visual","brief","fact","techb"};
    public static String [] channel_title={"热点","前沿","评论","专访","视觉","速读","谣言粉碎机","商业科技"};

    private static  BaseActivity activity;

    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.main_vp) ViewPager viewPager;

    public MainFragment() {
    }

    public static MainFragment newInstance(BaseActivity activity, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        MainFragment.activity = activity;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment fragment = new MainGame1Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("channel_tag",channel_tag[position]);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return channel_title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return channel_title[position];
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
