package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;
import com.xiaoming.slience.mvp.mode.HotLeftPagerDetaiModel;
import com.xiaoming.slience.mvp.mode.HotLeftPagerDetaiModelImp;
import com.xiaoming.slience.mvp.view.HotLeftPagerDetailView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:35
 */

public class HotLeftPagerDetailPresenterImp implements HotLeftPagerDetailPresenter{

    private HotLeftPagerDetaiModel mModel;
    private HotLeftPagerDetailView mView;

    public HotLeftPagerDetailPresenterImp(HotLeftPagerDetailView view) {
        mView = view;
        mModel = new HotLeftPagerDetaiModelImp();
    }

    @Override
    public void GetCollections() {
        mModel.collectionsQuery(new HotLeftPagerDetaiModelImp.isCreateListener() {
            @Override
            public void onSuccess(List<Collections> list) {
                mView.CollectionsQuery(list);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void AddCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position) {
        mModel.addCollection(collections,posts,position, new HotLeftPagerDetaiModelImp.isCreateListener() {
            @Override
            public void onSuccess(List<Collections> list) {
                mView.addCollectionFinish();
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void GetCollection(Collections collections) {
        mModel.collectionQuery(collections, new HotLeftPagerDetaiModelImp.CollectionQueryListener() {
            @Override
            public void onSuccess(List<Collection> list) {
                mView.CollectionQuery(list);
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void DelCollections(final Collections collections) {
        mModel.collectionQuery(collections, new HotLeftPagerDetaiModelImp.CollectionQueryListener() {
            @Override
            public void onSuccess(List<Collection> list) {
                mModel.collectionsDel(list,collections, new HotLeftPagerDetaiModelImp.CollectionListener() {
                    @Override
                    public void onSuccess() {
                        mView.delCollectionsFinish();
                    }

                    @Override
                    public void onFailure(BmobException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(BmobException e) {
                e.printStackTrace();
            }
        });


    }
}
