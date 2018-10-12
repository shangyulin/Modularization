package com.example.base;

import android.app.Application;

/**
 * Created by shangyulin on 2018/9/3.
 */

public class BaseInit implements BaseAppInit {

    public static Application application;

    @Override
    public void InitApplicationSpeed(Application application) {
        this.application = application;
    }

    public static Application getApplication(){
        return application;
    }

}
