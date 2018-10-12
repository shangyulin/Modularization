package com.example.base;

import android.app.Activity;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.RationaleListener;

/**
 * Created by shangyulin on 2018/8/20.
 */

public class PermissionUtils {

    private PermissionUtils(){

    }

    public static class PermissionHolder{
        public static PermissionUtils utils = new PermissionUtils();
    }

    public static PermissionUtils getInstance() {
        return PermissionHolder.utils;
    }

    public void requestPermission(Activity context, int requestCode, String[] permission, RationaleListener rationaleListener, PermissionListener listener){
        AndPermission.with(context)
                .requestCode(requestCode)
                .permission(permission)
                .rationale(rationaleListener)
                .callback(listener).start();
    }
}
