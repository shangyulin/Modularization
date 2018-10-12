package com.example.pay;

import android.app.Application;

import com.example.base.BaseAppInit;

/**
 * Created by shangyulin on 2018/9/3.
 */

public class PayInit implements BaseAppInit {

    public static Application application;

    @Override
    public void InitApplicationSpeed(Application application) {
        this.application = application;
    }

    public static Application getApplication(){
        return application;
    }

}
