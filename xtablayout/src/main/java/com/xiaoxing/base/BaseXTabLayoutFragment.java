package com.xiaoxing.base;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xiaoxing.adapter.ViewPagerAdapter;
import com.xiaoxing.common.base.BaseFragment;
import com.xiaoxing.xtablayout.R;
import com.xiaoxing.xtablayout.R2;
import com.xiaoxing.xtablayout.XTabLayout;

import java.util.List;

import butterknife.BindView;

/**
 * 描述：
 * 作者：xiaoxing on 17/5/9 15:38
 * 邮箱：2235445233@qq.com
 */
public class BaseXTabLayoutFragment extends BaseFragment {

    @BindView(R2.id.tabs)
    protected XTabLayout mTabLayout;
    @BindView(R2.id.viewPager)
    ViewPager mViewPager;

//    public static final String[] mTabTitle = new String[]{"监狱", "看守所"};

    private int mItem = 0;

    private ViewPagerAdapter mViewPagerAdapter;
    private Bundle mBundle;
    private List<Fragment> mFragments = null;
    private List<String> mTabTitles = null;


    /**
     * revycler上面的界面，动态添加
     * <p>
     * 使用方法：
     * 1、mLinearLayoutParent.addView(addGoodsAttrsView(addHeadView(R.layout.view));
     * 2、重写addHeadView方法
     */
    @BindView(R2.id.ll_parent)
    public LinearLayout mLinearLayoutParent;

    @BindView(R2.id.ll_bottom)
    public LinearLayout mLinearLayoutBottom;

    public List<Fragment> getFragments() {
        return mFragments;
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    public List<String> getTabTitles() {
        return mTabTitles;
    }

    public void setTabTitles(List<String> tabTitles) {
        mTabTitles = tabTitles;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_xtablayout;
    }


    @Override
    public void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        initBaseXtabData();

    }

    /**
     * 动态添加recycler上面的界面
     *
     * @return
     */
    protected View addHeadView(int layout) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(layout, null);

        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 动态添加recycler上面的界面
     *
     * @param layout
     * @return
     */
    protected View addBottomView(int layout) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(layout, null);

        view.setLayoutParams(lp);

        return view;
    }
    /**
     * 初始化数据
     */
    protected void initBaseXtabData() {

        initTabTitles();
        initFragments();
    }

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);
//        setTitleHidden();
        initTabLayout();

    }



    /**
     * 初始化水平滚动菜单
     */
    private void initTabLayout() {
        setupViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount());
        // 设置ViewPager的数据等
        mTabLayout.setupWithViewPager(mViewPager);

        //适合很多tab
        //mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab均分,适合少的tab
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tab均分,适合少的tab,TabLayout.GRAVITY_CENTER
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }


    private void setupViewPager(ViewPager viewPager) {

        Bundle bundle = getActivity().getIntent().getExtras();


        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        mBundle = new Bundle();

        for (int i = 0; i < mFragments.size(); i++) {
            mBundle = new Bundle();
            mBundle.putString("status", (i + 1) + "");
            mBundle.putString("title", mTabTitles.get(i));
            mFragments.get(i).setArguments(mBundle);
            mViewPagerAdapter.addFrag(mFragments.get(i), mTabTitles.get(i));
        }

        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(mItem);

    }

    private void initFragments() {
        mFragments = getFragments();
    }

    protected void initTabTitles() {
        mTabTitles = getTabTitles();
    }

}
