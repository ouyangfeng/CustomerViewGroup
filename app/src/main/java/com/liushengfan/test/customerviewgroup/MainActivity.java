package com.liushengfan.test.customerviewgroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.liushengfan.test.customerviewgroup.view.PagerSlidingTabStrip;
import com.liushengfan.test.customerviewgroup.view.ViewGroupScrollView;

public class MainActivity extends FragmentActivity implements FragmentAdapterProvider {
    PagerSlidingTabStrip mTab;
    public ViewPager mViewPager;
    String[] mPagetitles;
    public ViewGroupScrollView myVerticalViewPager;

    // 五种分类适配器，
    private CommonFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        myVerticalViewPager = (ViewGroupScrollView) findViewById(R.id.root_vp);
        initViewPager();
        myVerticalViewPager.setViewPagerLayoutId(R.id.vp_userhome);
    }


    private void initViewPager() {
        mPagetitles = getResources().getStringArray(R.array.activity_event);

        mTab = (PagerSlidingTabStrip) findViewById(R.id.pagerslidingtabstrip_navigation);
        mTab.setShouldExpand(false);
        mTab.setUnderlineColor(this.getResources().getColor(R.color.sgk_gray_userhome_separate));
        mTab.setUnderlineHeight(1);
        mTab.setIndicatorColor(this.getResources().getColor(R.color.sgk_red_common_bg));
        mTab.setIndicatorHeight(10);
        mTab.setDividerColor(getResources().getColor(R.color.sgk_gray_userhome_separate));
        mViewPager = (ViewPager) findViewById(R.id.vp_userhome);
        mViewPager.setOffscreenPageLimit(2);
        mAdapter = new PagerAdapterUserHome(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mTab.setViewPager(mViewPager);
    }

    @Override
    public String getTitle(int position) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        Fragment f = new ItemFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int getCount() {
        return mPagetitles.length;
    }

}
