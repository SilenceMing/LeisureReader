package com.xiaoming.slience.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.xiaoming.slience.R;
import com.xiaoming.slience.adapter.CollectionsAdapter;
import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenter;
import com.xiaoming.slience.mvp.Presenter.HotLeftPagerDetailPresenterImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerDetailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.Bmob;
import nsu.edu.com.library.SwipeBackActivity;
import nsu.edu.com.library.SwipeBackLayout;

/**
 * @author slience
 * @des
 * @time 2017/6/1116:16
 */

public class HotLeftDetailActivity extends SwipeBackActivity implements HotLeftPagerDetailView,View.OnClickListener {

    private WebView mWvContent;
    private int mPosition;
    private List<CloumnsPosts.PostsBean> mPosts;
    private SwipeBackLayout mSwipeBackLayout;
    private ProgressBarDeterminate mProgressDeterminate;
    private Toolbar mToolbar;
    private TextView mTvLike;
    private TextView mTvComment;
    private ImageView mIvLike;
    private ImageView mIvCollection;
    private TextView mTvMdCollections;
    private ListView mLvCollections;
    private HotLeftPagerDetailPresenter mPresenter;
    private Dialog mBottomDialog;
    private ProgressBarCircularIndeterminate mPgView;
    private CollectionsAdapter collectionsAdapter;
    private String mAction;
    private Collection mCollection;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotleftdetail);
        EventBus.getDefault().register(this);
        Bmob.initialize(this, "8ece0e1c21f9685ab8ddf4602f14008a");
        mPosition = getIntent().getIntExtra(getString(R.string.Post_Positions),0);
        mPosts = (List<CloumnsPosts.PostsBean>) getIntent().getSerializableExtra(getString(R.string.Posts));
        mAction = getIntent().getAction();
        mCollection = (Collection) getIntent().getSerializableExtra(getString(R.string.CollectionActivity_Collection));
        mPresenter = new HotLeftPagerDetailPresenterImp(this);
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(200);
        init();
    }

    private void init() {
        mIvLike = (ImageView) findViewById(R.id.iv_like);
        mIvCollection = (ImageView) findViewById(R.id.iv_collection);
        mTvComment = (TextView) findViewById(R.id.tv_comment);
        mTvLike = (TextView) findViewById(R.id.tv_like);
        mWvContent = (WebView) findViewById(R.id.wv_content);
        mToolbar = (Toolbar) findViewById(R.id.hot_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(mAction.equals(getString(R.string.CollectionActivity_Action))){
            mWvContent.loadUrl(mCollection.getCollection_Url());
            mIvCollection.setEnabled(false);
            mTvLike.setText(mCollection.getCollection_LikeCount());
            mTvComment.setText(mCollection.getCollection_CommCount());
        }else if(mAction.equals(getString(R.string.HotLeftPager_Action))){
            mWvContent.loadUrl(mPosts.get(mPosition).getUrl());
            mTvLike.setText(String.valueOf(mPosts.get(mPosition).getLike_count()));
            mTvComment.setText(String.valueOf(mPosts.get(mPosition).getComments_count()));
        }
        mIvLike.setOnClickListener(this);
        mIvCollection.setOnClickListener(this);
        mProgressDeterminate = (ProgressBarDeterminate) findViewById(R.id.progressDeterminate);
        mWvContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(newProgress == 100){
                    mProgressDeterminate.setVisibility(View.GONE);
                }else{
                    if(mProgressDeterminate.getVisibility() == View.GONE)
                        mProgressDeterminate.setVisibility(View.VISIBLE);
                    mProgressDeterminate.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

        });
        mWvContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hotleftdetail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_share:
                //TODO 网页分享
                break;
            case R.id.menu_browser:
                startActivity(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse(mPosts.get(mPosition).getUrl())));
                break;
            case R.id.menu_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newUri(getContentResolver(), "uri", Uri.parse(mPosts.get(mPosition).getUrl())));
                Toast.makeText(this, "已复制", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_like:
                //TODO 喜欢
                break;
            case R.id.iv_collection:
                showCustomDialog();
                mPresenter.GetCollections();
                break;
            case R.id.tv_mdCollections:
                startActivity(new Intent(this,CreateCollectionsActivity.class));
                break;
        }
    }

    private void showCustomDialog(){
        mBottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_collections_bottom_dialog,null);
        initView(contentView);
        mBottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = getResources().getDisplayMetrics().heightPixels/3;
        contentView.setLayoutParams(layoutParams);
        mBottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        mBottomDialog.setCanceledOnTouchOutside(true);
        mBottomDialog.show();
    }

    private void initView(View view) {
        mTvMdCollections = (TextView) view.findViewById(R.id.tv_mdCollections);
        mLvCollections = (ListView) view.findViewById(R.id.lv_collections);
        mPgView = (ProgressBarCircularIndeterminate) view.findViewById(R.id.pg_view);
        mTvMdCollections.setOnClickListener(this);

    }


    @Override
    public void CollectionsQuery(final List<Collections> list) {
        mPgView.setVisibility(View.GONE);
        collectionsAdapter = new CollectionsAdapter(this, list);
        mLvCollections.setAdapter(collectionsAdapter);

        mLvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.AddCollection(list.get(position),mPosts,mPosition);
            }
        });
    }

    @Override
    public void addCollectionFinish() {
        mBottomDialog.cancel();
    }

    @Override
    public void CollectionQuery(List<Collection> list) {

    }

    @Override
    public void delCollectionsFinish() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String msg){
        if(msg.equals(getString(R.string.eventBus_createFinish))){
            mPresenter.GetCollections();
            collectionsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
