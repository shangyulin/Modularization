package com.example.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/pay/PayActivity")
public class PayActivity extends Activity {

    private GridView grid;
    private List<Integer> sourceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pay);
//        findViewById(getResources().getIdentifier("pay", "id", getPackageName())).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 跳转到MainActivity
//                ARouter.getInstance().build("/main/MainActivity").navigation();
//                finish();
//            }
//        });
        grid = findViewById(getResources().getIdentifier("grid", "id", getPackageName()));

        sourceList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(R.drawable.one);
        }
        grid.setAdapter(new GridAdapter());
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        BitmapFactory.decodeResource(getResources(), R.drawable.one, options);
//        int width = options.outWidth;
//        int height = options.outHeight;
//        int result = dp2px(this, 500);
//        Toast.makeText(this, result + ":", Toast.LENGTH_SHORT).show();
    }


    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return sourceList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View mView;
            if (view == null){
                mView = View.inflate(PayActivity.this, R.layout.item_image, null);
            }else{
                mView = view;
            }
            ImageView item = mView.findViewById(R.id.pay_image);
            return mView;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
