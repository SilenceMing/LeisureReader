package com.xiaoming.slience.mvp.mode;

import android.util.Log;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author slience
 * @des
 * @time 2017/6/20 15:38
 */

public class HotLeftPagerDetaiModelImp implements HotLeftPagerDetaiModel {

    @Override
    public void collectionsQuery(final isCreateListener listener) {
        BmobQuery<Collections> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Collections>() {
            @Override
            public void done(List<Collections> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        listener.onSuccess(list);
                    }
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void addCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position, final isCreateListener listener) {
        Collection collection = new Collection();
        collection.setCollection_Title(posts.get(position).getTitle());
        collection.setCollection_LikeCount(posts.get(position).getLike_count() + "");
        collection.setCollection_Time(posts.get(position).getPublished_time());
        collection.setCollection_Des(posts.get(position).getAbstractX());
        collection.setCollection_CommCount(posts.get(position).getComments_count() + "");
        collection.setCollection_Url(posts.get(position).getUrl());
        List<CloumnsPosts.PostsBean.ThumbsBean> thumbs = posts.get(position).getThumbs();
        if (thumbs != null && thumbs.size() > 0) {
            collection.setCollection_ImgUrl(thumbs.get(0).getSmall().getUrl());
        }
        collection.setCollections(collections);
        collection.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    listener.onSuccess(null);
                } else {
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void collectionQuery(Collections collections, final CollectionQueryListener listener) {
        BmobQuery<Collection> query = new BmobQuery<>();
        query.addWhereEqualTo("mCollections",new BmobPointer(collections));
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if(e == null){
                    if(list!=null){
                        Log.d("Collection",list.size()+"");
                        listener.onSuccess(list);
                    }
                }else{
                    listener.onFailure(e);
                }
            }
        });
    }

    @Override
    public void collectionsDel(List<Collection> list, final Collections collections , final CollectionListener listener ) {
        List<BmobObject> collectionList = new ArrayList<>();
        collectionList.addAll(list);
        new BmobBatch().deleteBatch(collectionList).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e == null){
                    collections.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                listener.onSuccess();
                            }else{
                                listener.onFailure(e);
                            }
                        }
                    });
                }else{
                    listener.onFailure(e);
                }
            }
        });


    }

    public interface isCreateListener {
        void onSuccess(List<Collections> list);
        void onFailure(BmobException e);
    }

    public interface CollectionQueryListener{
        void onSuccess(List<Collection> list);
        void onFailure(BmobException e);
    }
    public interface CollectionListener{
        void onSuccess();
        void onFailure(BmobException e);
    }

}
