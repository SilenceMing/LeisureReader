package com.xiaoming.slience.utils;

import android.app.Activity;
import android.os.Environment;

import java.io.File;

/**
 * @author slience
 * @des
 * @time 2017/6/1518:02
 */

public class FileUtils {

    private static final String ParentPath = Environment.getExternalStorageDirectory()+"/slience";
    private  static final String Sdcard_CachePath = ParentPath + "/Cache";
    public static final String PhotoDownloadPath =ParentPath + "/Photos";
    private static Activity mActivity;

    public FileUtils(Activity activity) {
        mActivity = activity;
    }

    /**
     * 判断SdCard是否存在
     * @return
     */
    public boolean isExistsSdcard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public File mkCacheDir(){
        if(isExistsSdcard()){
            File file = new File(Sdcard_CachePath);
            if(!file.exists()){
                file.mkdirs();
            }
            return file;
        }else{
            return mActivity.getCacheDir();
        }
    }
}
