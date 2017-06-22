package com.xiaoming.slience.mvp.mode;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collection;
import com.xiaoming.slience.bean.Collections;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:37
 */

public interface HotLeftPagerDetaiModel {
    void collectionsQuery(HotLeftPagerDetaiModelImp.isCreateListener listener);

    void addCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position, HotLeftPagerDetaiModelImp.isCreateListener listener);

    void collectionQuery(Collections collections, HotLeftPagerDetaiModelImp.CollectionQueryListener listener);

    void collectionsDel(List<Collection> list,Collections collections, HotLeftPagerDetaiModelImp.CollectionListener listener);
}
