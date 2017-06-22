package com.xiaoming.slience.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoming.slience.pager.HotLeftPager;
import com.xiaoming.slience.global.Global_Title;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/1017:16
 */

public class HotLeftAdapter extends PagerAdapter {

    private List<HotLeftPager> mHotLeftPagers;

    public void setData(List<HotLeftPager> pagers){
        mHotLeftPagers = pagers;
        this.notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Global_Title.HOTLEFT_TABS[position];
    }

    @Override
    public int getCount() {
        return Global_Title.HOTLEFT_TABS.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HotLeftPager pager = mHotLeftPagers.get(position);
        View view = pager.view;
        container.addView(view);
        pager.initData();
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
