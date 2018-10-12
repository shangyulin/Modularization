package com.example.shangyulin.modularizationtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
@Route(path = "/main/ImageActivity")
public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private int widthPixels;
    private int heightPixels;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(getResources().getIdentifier("image", "id", getPackageName()));
        String path = Environment.getExternalStorageDirectory() + "/";
        String filename = "woniu.jpg";
        File file = new File(path + filename);
        if (!file.exists()){
            Toast.makeText(ImageActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
        }else{
            // 获取屏幕宽高
            getConfig();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            options.inSampleSize = calculateInSampleSize(options, widthPixels, heightPixels);

            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            bitmap = compressByQuality(bitmap, 1*1024*1024, false);

            saveBitmap(bitmap);

            imageView.setImageBitmap(bitmap);
        }
    }


    /**
     * 获取屏幕宽高
     */
    public void getConfig(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        heightPixels = dm.heightPixels;
        widthPixels = dm.widthPixels;
        Log.d("giant", heightPixels + ":" + widthPixels);
    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        // 图片宽高
        int height = options.outHeight;
        int width = options.outWidth;

        int inSampleSize;
        for(inSampleSize = 1; (width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight; inSampleSize <<= 1) {
            ;
        }

        return inSampleSize;
    }

    public static String saveBitmap(Bitmap mBitmap) {
        String savePath = Environment.getExternalStorageDirectory() + "/";
        File filePic;
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (filePic.exists()){
                filePic.delete();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }

    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }


    public static Bitmap compressByQuality(Bitmap src, long maxByteSize, boolean recycle) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if((long)baos.size() <= maxByteSize) {
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if((long)baos.size() >= maxByteSize) {
                bytes = baos.toByteArray();
            } else {
                int st = 0;
                int end = 100;
                int mid = 0;

                while(st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if((long)len == maxByteSize) {
                        break;
                    }

                    if((long)len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }

                if(end == mid - 1) {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }

                bytes = baos.toByteArray();
            }
        }

        if(recycle && !src.isRecycled()) {
            src.recycle();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
