package com.example.cj.videoeditor.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.cj.videoeditor.camera.CameraController;
import com.example.cj.videoeditor.camera.ICamera;
import com.example.cj.videoeditor.drawer.CameraDrawer;
import com.example.cj.videoeditor.gpufilter.SlideGpuFilterGroup;
import com.example.cj.videoeditor.gpufilter.basefilter.GPUImageFilter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClearColor;

/**
 * Created by cj on 2017/8/1.
 * desc
 */

public class CameraView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private Context mContext;
    public CameraDrawer mCameraDrawer;
    public CameraController mCamera;

    private int dataWidth = 0, dataHeight = 0;

    private boolean isSetParm = false;

    private int cameraId;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        /**初始化OpenGL的相关信息*/
        setEGLContextClientVersion(2);//设置版本
        setRenderer(this);//设置Renderer
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        setRenderMode(RENDERMODE_WHEN_DIRTY);//主动调用渲染
        setPreserveEGLContextOnPause(true);//保存Context当pause时
        setCameraDistance(100);//相机距离



        /**初始化Camera的绘制类*/
        mCameraDrawer = new CameraDrawer(getResources());
        /**初始化相机的管理类*/
        mCamera = new CameraController(context);

    }

    private void open(int cameraId) {
        mCamera.close();
        mCamera.open(cameraId);
        mCameraDrawer.setCameraId(cameraId);
        final Point previewSize = mCamera.getPreviewSize();
        dataWidth = previewSize.x;
        dataHeight = previewSize.y;
        SurfaceTexture texture = mCameraDrawer.getTexture();
        texture.setOnFrameAvailableListener(this);
        mCamera.setPreviewTexture(texture);
        mCamera.preview();

    }
    public void tackPicture(){
        mCamera.setTakePicture(new ICamera.TakePicture() {
            @Override
            public void takePicture(byte[] bytes, Camera camera) {
                picture.takePicture(bytes,camera);
            }
        });
    }
    ICamera.TakePicture picture;
    public void onTackPicture(ICamera.TakePicture picture){
        this.picture = picture;
    }
    /**
     * 切换前后置摄像头
     * */
    public void switchCamera() {
        cameraId = cameraId == 0 ? 1 : 0;
        mCameraDrawer.switchCamera();
        open(cameraId);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCameraDrawer.onSurfaceCreated(gl, config);
        if (!isSetParm) {
            open(cameraId);
            stickerInit();
        }
        glClearColor(0,0,0,0);
        mCameraDrawer.setPreviewSize(dataWidth, dataHeight);
    }
    Camera.AutoFocusCallback callback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片

        }
    };
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mCameraDrawer.onSurfaceChanged(gl, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (isSetParm) {
            mCameraDrawer.onDrawFrame(gl);
        }
    }

    /**
     * 每次Activity onResume时被调用,第一次不会打开相机
     */
    @Override
    public void onResume() {
        super.onResume();
        if (isSetParm) {
            open(cameraId);
        }
    }

    public void onDestroy() {
        if (mCamera != null) {
            mCamera.close();
            mCamera=null;
        }
    }

    /**
     * 摄像头聚焦
     */
    public void onFocus(Point point, Camera.AutoFocusCallback callback) {
        try{
            mCamera.onFocus(point, callback);
        }catch (Exception e){

        }
    }
    public GPUImageFilter getFiler(){
       return mCameraDrawer.getFilter();
    }
    public int getCameraId() {
        return cameraId;
    }

    public int getBeautyLevel() {
        return mCameraDrawer.getBeautyLevel();
    }

    public void changeBeautyLevel(final int level) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.changeBeautyLevel(level);
            }
        });
    }

    public void startRecord() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.startRecord();
            }
        });
    }

    public void stopRecord() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.stopRecord();
            }
        });
    }

    public void setSavePath(String path) {
        mCameraDrawer.setSavePath(path);
    }

    public void resume(final boolean auto) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.onResume(auto);
            }
        });
    }

    public void pause(final boolean auto) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.onPause(auto);
            }
        });
    }

    public void onTouch(final MotionEvent event) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.onTouch(event);
            }
        });
    }

    public void setOnFilterChangeListener(SlideGpuFilterGroup.OnFilterChangeListener listener) {
        mCameraDrawer.setOnFilterChangeListener(listener);
    }

    private void stickerInit() {
        if (!isSetParm && dataWidth > 0 && dataHeight > 0) {
            isSetParm = true;
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.requestRender();
    }

    public void onPos(final int pos){
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mCameraDrawer.mSlideFilterGroup.Change(pos);
            }
        });
    }
}
