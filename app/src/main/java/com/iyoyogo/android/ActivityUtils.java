package com.iyoyogo.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.iyoyogo.android.MainActivity;

import okhttp.LogUtil;

/**

 * 创建时间：2018/5/30
 * 描述：Activity跳转工具类
 */
public class ActivityUtils {

    /**
     * MainActivity
     *
     * @param context
     */
    public static void goMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 编辑图片
     * @param context
     */
    public static void goBianjiActivity(Context context,String path) {
        Intent intent = new Intent(context, BianjiActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

    /**
     * 拍照Activity
     *
     * @param context
     */
    public static void goShowCameraActivity(Context context) {
        Intent intent = new Intent(context, ActivityCamera.class);
        context.startActivity(intent);
    }

    /**
     * 同款itemActivity
     *
     * @param context
     */
    public static void goTongkuanItemActivity(Context context,String path) {
        Intent intent = new Intent(context, TongkuanItemActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }
    /**
     * 同款Activity
     *
     * @param context
     */
    public static void goTongkuanActivity(Context context) {
        Intent intent = new Intent(context, TongKuanActivity.class);
        context.startActivity(intent);
    }
    /**
     * 同款拍照Activity
     *
     * @param context
     */
    public static void goTongKuanCameraActivity(Context context,String type) {
        Intent intent = new Intent(context, TongKuanPaizhaoActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    /**
     * 视频剪辑Activity
     *
     * @param context
     */
    public static void goShipinjianji(Context context,String path){

        Intent intent = new Intent(context, ShipinActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

}
