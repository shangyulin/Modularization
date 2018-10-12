package com.example.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.tauth.Tencent;

import static android.provider.UserDictionary.Words.APP_ID;

@Route(path = "/test/LoginActivity")
public class LoginActivity extends AppCompatActivity {

    private TextView tv;
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String content = intent.getStringExtra("key");

        tv = findViewById(getResources().getIdentifier("result", "id", getPackageName()));
        tv.setText(content);

//        findViewById(getResources().getIdentifier("topay", "id", getPackageName())).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ARouter.getInstance().build("/pay/PayActivity").navigation();
//            }
//        });

        findViewById(getResources().getIdentifier("qq", "id", getPackageName())).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        ObjectAnimator.ofFloat(tv, "scaleX", 1, 2, 1, 2, 3).setDuration(3000).start();
        ObjectAnimator.ofFloat(tv, "scaleY", 1, 2, 1, 2, 3).setDuration(3000).start();
        ObjectAnimator.ofFloat(tv, "alpha", 0, 0.5f, 1.0f).setDuration(3000).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    public void login()
    {
        mTencent = Tencent.createInstance("101493768", this.getApplicationContext());
        if (!mTencent.isSessionValid())
        {
            mTencent.login(this, "101493768", new BaseUiListener());
        }
    }
}
