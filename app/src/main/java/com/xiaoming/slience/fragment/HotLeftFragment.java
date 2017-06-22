package com.xiaoming.slience.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoming.slience.activity.CollectionsActivity;
import com.xiaoming.slience.pager.HotLeftPager;
import com.xiaoming.slience.mvp.Presenter.HotLeftPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPresenterImp;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.HotLeftAdapter;
import com.xiaoming.slience.base.BaseFragment;
import com.xiaoming.slience.global.Global_Title;
import com.xiaoming.slience.bean.Columns;
import com.xiaoming.slience.mvp.view.HotLeftView;

import java.util.ArrayList;
import java.util.List;

public class HotLeftFragment extends BaseFragment implements HotLeftView{

    private TabLayout mTabView;
    private ViewPager mVpContent;
    private HotLeftAdapter mAdapter;
    private List<HotLeftPager> pagerList ;
    private HotLeftPresenter mPresenter;
    private List<Columns.ColumnsBean> mColumnsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        pagerList = new ArrayList<>();
        mColumnsList = new ArrayList<>();
        mPresenter = new HotLeftPresenterImp(this,mACache);
        mAdapter = new HotLeftAdapter();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_hotleft,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        mTabView = (TabLayout) view.findViewById(R.id.tab_view);
        mTabView.setTabMode(TabLayout.MODE_SCROLLABLE);
        mVpContent = (ViewPager) view.findViewById(R.id.vp_content);
    }

    @Override
    public void initData() {
        mPresenter.getDataFromServer(false,false);
    }

    @Override
    public void onRequestComplete(List<Columns.ColumnsBean> columnsList) {
        mColumnsList.addAll(columnsList);
        for(int i = 0; i< Global_Title.HOTLEFT_TABS.length; i++){
            mTabView.addTab(mTabView.newTab().setText(Global_Title.HOTLEFT_TABS[i]));
            HotLeftPager pager = new HotLeftPager(getActivity(),columnsList.get(i),mACache);
            pagerList.add(pager);
        }
        mAdapter.setData(pagerList);
        mVpContent.setAdapter(mAdapter);
        mTabView.setupWithViewPager(mVpContent);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mActivity.getMenuInflater().inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.action_collections:
                startActivity(new Intent(mActivity, CollectionsActivity.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
