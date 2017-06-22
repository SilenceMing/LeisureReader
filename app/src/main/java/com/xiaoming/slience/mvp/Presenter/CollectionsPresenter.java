package com.xiaoming.slience.mvp.Presenter;

import com.xiaoming.slience.bean.Collections;

import java.io.File;

/**
 * @author slience
 * @des
 * @time 2017/6/2011:13
 */

public interface CollectionsPresenter {
    void CreateCollections(String name, String des, File imgFile);

    void UpdateCollections(Collections collections, String name, String des, File imgFile);
}
