package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.CloumnsPosts;
import com.xiaoming.slience.bean.Collections;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/6/2015:35
 */

public interface HotLeftPagerDetailPresenter {
    /**
     * 查询收藏集
     */
    void GetCollections();

    /**
     * 添加收藏
     *
     * @param collections
     * @param posts
     * @param position
     */
    void AddCollection(Collections collections, List<CloumnsPosts.PostsBean> posts, int position);

    /**
     * 查询收藏
     *
     * @param collections
     */
    void GetCollection(Collections collections);

    /**
     * 删除收藏集
     * @param collections
     */
    void DelCollections(Collections collections);
}
