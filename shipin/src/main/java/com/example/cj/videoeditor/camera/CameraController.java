package com.example.cj.videoeditor.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

import com.example.cj.videoeditor.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cj on 2017/8/2.
 * desc 相机的管理类 主要是Camera的一些设置
 * 包括预览和录制尺寸、闪光灯、曝光、聚焦、摄像头切换等
 */

public class CameraController implements ICamera{
    /**相机的宽高及比例配置*/
    public Config mConfig;
    /**相机实体*/
    public Camera mCamera;
    /**预览的尺寸*/
    private Camera.Size preSize;
    /**实际的尺寸*/
    private Camera.Size picSize;

    private Point mPreSize ;
    private Point mPicSize ;
    Context context;
    public CameraController(Context context){
        this.context = context;
        /**初始化一个默认的格式大小*/
        mConfig = new Config();
        mConfig.minPreviewWidth=720;
        mConfig.minPictureWidth=720;
        mConfig.rate=1.778f;
    }

    public void open(int cameraId) {
        mCamera = Camera.open(cameraId);
        if (mCamera != null){
            /**选择当前设备允许的预览尺寸*/
            Camera.Parameters param = mCamera.getParameters();
            preSize = getPropPreviewSize(param.getSupportedPreviewSizes(), mConfig.rate,
                    mConfig.minPreviewWidth);
            picSize = getPropPictureSize(param.getSupportedPictureSizes(),mConfig.rate,
                    mConfig.minPictureWidth);
            param.setPictureSize(picSize.width, picSize.height);
            param.setPreviewSize(preSize.width,preSize.height);

            mCamera.setParameters(param);
            Camera.Size pre=param.getPreviewSize();
            Camera.Size pic=param.getPictureSize();
            mPicSize=new Point(pic.height,pic.width);
            mPreSize=new Point(pre.height,pre.width);
           Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            int rotation = ((Activity)context).getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0: degrees = 0; break;
                case Surface.ROTATION_90: degrees = 90; break;
                case Surface.ROTATION_180: degrees = 180; break;
                case Surface.ROTATION_270: degrees = 270; break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;  // compensate the mirror
            } else {  // back-facing
                result = (info.orientation - degrees + 360) % 360;
            }
            mCamera.setDisplayOrientation(result);



        }
    }
    public void onShanguan(String s){
        try{
            Camera.Parameters mParameters;
            mParameters = mCamera.getParameters();
            if(s.equals("kai")) {
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            }else {
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            }
            mCamera.setParameters(mParameters);

        } catch(Exception ex){}

    }
    @Override
    public void setPreviewTexture(SurfaceTexture texture) {
        if(mCamera!=null){
            try {
                mCamera.setPreviewTexture(texture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setTakePicture(final TakePicture picture) {
        if(mCamera !=null){
            try {
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        picture.takePicture(bytes,camera);
                    }
                });
            }catch (Exception e){
            }
        }
    }

    @Override
    public void setConfig(Config config) {
        this.mConfig=config;
    }

    @Override
    public void setOnPreviewFrameCallback(final PreviewFrameCallback callback) {
        if(mCamera!=null){
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    callback.onPreviewFrame(data,mPreSize.x,mPreSize.y);
                }
            });
        }
    }

    @Override
    public void preview() {
        if (mCamera != null){
            try {
                mCamera.startPreview();
            }catch (Exception e){

            }
        }
    }

    @Override
    public Point getPreviewSize() {
        return mPreSize;
    }

    @Override
    public Point getPictureSize() {
        return mPicSize;
    }

    @Override
    public boolean close() {
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        return false;
    }

    /**
     * 手动聚焦
     *
     * @param point 触屏坐标 必须传入转换后的坐标
     */
    public void onFocus(Point point, Camera.AutoFocusCallback callback) {
        if(null!=point) {
            Camera.Parameters parameters = mCamera.getParameters();
            boolean supportFocus = true;
            boolean supportMetering = true;
            //不支持设置自定义聚焦，则使用自动聚焦，返回
            if (parameters.getMaxNumFocusAreas() <= 0) {
                supportFocus = false;
            }
            if (parameters.getMaxNumMeteringAreas() <= 0) {
                supportMetering = false;
            }
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            List<Camera.Area> areas1 = new ArrayList<Camera.Area>();
            //再次进行转换
            point.x = (int) (((float) point.x) / Constants.screenWidth * 2000 - 1000);
            point.y = (int) (((float) point.y) / Constants.screenHeight * 2000 - 1000);
            int left = point.x - 300;
            int top = point.y - 300;
            int right = point.x + 300;
            int bottom = point.y + 300;
            left = left < -1000 ? -1000 : left;
            top = top < -1000 ? -1000 : top;
            right = right > 1000 ? 1000 : right;
            bottom = bottom > 1000 ? 1000 : bottom;
            Log.v("",""+left+"========"+ top+"============="+ right+"============"+ bottom);
            areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
            areas1.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
            if (supportFocus) {
                parameters.setFocusAreas(areas);
            }
            if (supportMetering) {
                parameters.setMeteringAreas(areas1);
            }

            try {
                mCamera.setParameters(parameters);// 部分手机 会出Exception（红米）
                mCamera.autoFocus(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try{
                mCamera.autoFocus(callback);
            }catch (Exception e){

            }

        }
    }
    private Camera.Size getPropPictureSize(List<Camera.Size> list, float th, int minWidth){
        Collections.sort(list, sizeComparator);
        int i = 0;
        for(Camera.Size s:list){
            if((s.height >= minWidth) && equalRate(s, th)){
                break;
            }
            i++;
        }
        if(i == list.size()){
            i = 0;
        }
        return list.get(i);
    }
    private Camera.Size getPropPreviewSize(List<Camera.Size> list, float th, int minWidth){
        Collections.sort(list, sizeComparator);

        int i = 0;
        for(Camera.Size s:list){
            if((s.height >= minWidth) && equalRate(s, th)){
                break;
            }
            i++;
        }
        if(i == list.size()){
            i = 0;
        }
        return list.get(i);
    }
    private static boolean equalRate(Camera.Size s, float rate){
        float r = (float)(s.width)/(float)(s.height);
        if(Math.abs(r - rate) <= 0.03) {
            return true;
        }else{
            return false;
        }
    }
    private Comparator<Camera.Size> sizeComparator=new Comparator<Camera.Size>(){
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if(lhs.height == rhs.height){
                return 0;
            }else if(lhs.height > rhs.height){
                return 1;
            }else{
                return -1;
            }
        }
    };
}