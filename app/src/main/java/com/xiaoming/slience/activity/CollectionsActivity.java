package com.xiaoming.slience.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.CollectionsAdapter;
import com.xiaoming.slience.base.BaseActivity;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenterImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerDetailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author slience
 * @des 收藏集列表
 * @time 2017/6/2019:20
 */

public class CollectionsActivity extends BaseActivity implements HotLeftPagerDetailView{

    private ListView mLvContent;
    private ProgressBarCircularIndeterminate mPgView;
    private CollectionsAdapter mAdapter;
    private HotLeftPagerDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("收藏集");
        EventBus.getDefault().register(this);
        mPresenter = new HotLeftPagerDetailPresenterImp(this);
        initView();
        mPresenter.GetCollections();
    }

    private void initView() {
        mLvContent = (ListView) findViewById(R.id.lv_content);
        mPgView = (ProgressBarCircularIndeterminate) findViewById(R.id.pg_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collections;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collections,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_buildColl){
            startActivity(new Intent(this,CreateCollectionsActivity.class));
        }else if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void CollectionsQuery(final List<Collections> list) {
        mPgView.setVisibility(View.GONE);
        mAdapter = new CollectionsAdapter(this,list);
        mLvContent.setAdapter(mAdapter);

        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(CollectionsActivity.this,CollectionActivity.class);
                intent.putExtra(getString(R.string.Collections),list.get(position));
                intent.putExtra(getString(R.string.CollectionsPosition),position);
               startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String msg){
        if(msg.equals(getString(R.string.eventBus_createFinish))){
            mPresenter.GetCollections();
            mAdapter.notifyDataSetChanged();
        }else if(msg.equals(getString(R.string.updateFinish))){
            mPresenter.GetCollections();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addCollectionFinish() {
    }

    @Override
    public void CollectionQuery(List<Collection> list) {

    }

    @Override
    public void delCollectionsFinish() {

    }
}
