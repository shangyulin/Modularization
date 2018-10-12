package com.example.login;

import android.app.Application;
import android.content.Context;

import com.example.base.BaseAppInit;

/**
 * Created by shangyulin on 2018/9/3.
 */

public class LoginInit implements BaseAppInit {

    public static Application application;

    @Override
    public void InitApplicationSpeed(Application application) {
        this.application = application;
    }

    public static Context getContext(){
        return application.getApplicationContext();
    }
}
