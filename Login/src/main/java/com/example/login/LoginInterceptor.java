package com.example.login;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.example.base.BaseApplication;
import com.example.base.PermissionUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;


/**
 * Created by shangyulin on 2018/9/3.
 */

/**
 * 加载登录页面之前进行拦截
 */
@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {

    private Context mContext;

    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        if (postcard.getPath().equals("/test/LoginActivity")){
            BaseApplication.getTopActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PermissionUtils.getInstance().requestPermission(BaseApplication.getTopActivity(), 200, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS}, new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(BaseApplication.getTopActivity(), rationale).show();
                        }
                    }, new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                            Toast.makeText(BaseApplication.getTopActivity(), "成功获取权限", Toast.LENGTH_LONG).show();
                            // 成功获取到所有权限，放行
                            callback.onContinue(postcard);
                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

                            Toast.makeText(BaseApplication.getTopActivity(), "有必要权限被拒绝，无法进入游戏", Toast.LENGTH_SHORT).show();

                            // 是否有不再提示并拒绝的权限。
                            if (AndPermission.hasAlwaysDeniedPermission(BaseApplication.getTopActivity(), deniedPermissions)) {
                                // 第一种：用AndPermission默认的提示语。
                                AndPermission.defaultSettingDialog(BaseApplication.getTopActivity(), 300).show();
                                // 第二种：用自定义的提示语。
//                                AndPermission.defaultSettingDialog(BaseApplication.getTopActivity(), 400)
//                                        .setTitle("权限申请失败")
//                                        .setMessage("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！")
//                                        .setPositiveButton("好，去设置")
//                                        .show();
                            }
                        }
                    });
                }
            });
        }else{
            // 放行
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
