package com.xiaoming.slience.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaoming.slience.R;

/**
 * @author slience
 * @des
 * @time 2017/6/2011:44
 */

public abstract class CustomDialog {
    private Dialog mDialog;

    public CustomDialog(Context context, int resource, int Gravity, int animation) {
        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(resource, null);
        bottomDialog.setContentView(contentView);
        bottomDialog.getWindow().setGravity(Gravity);
        bottomDialog.getWindow().setWindowAnimations(animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        mDialog = bottomDialog;
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public abstract void initView(View contentView);
}
