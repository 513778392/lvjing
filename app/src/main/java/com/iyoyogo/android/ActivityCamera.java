package com.iyoyogo.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cj.videoeditor.AlbumNotifyHelper;
import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.camera.ICamera;
import com.example.cj.videoeditor.camera.SensorControler;
import com.example.cj.videoeditor.gpufilter.SlideGpuFilterGroup;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.widget.CameraView;
import com.example.cj.videoeditor.widget.CircularProgressView;
import com.example.cj.videoeditor.widget.FocusImageView;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import adapter.GuoLvAdapter1;
import butterknife.BindView;
import butterknife.ButterKnife;
import presenter.impl.CameraBiz;
import view.ICameraView;

public class ActivityCamera extends BaseActivity implements ICameraView, View.OnClickListener, View.OnTouchListener, SensorControler.CameraFocusListener, SlideGpuFilterGroup.OnFilterChangeListener

    {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(null !=mCameraView) {
                    try{
                        mCameraView.onFocus(null, callback);
                        handler.postDelayed(runnable, 2000);
                    }catch (Exception e){

                    }
                }
            }
        };
        private CameraView mCameraView;
        private CircularProgressView mCapture;
        private FocusImageView mFocus;
        private ImageView mBeautyBtn;
        private ImageView mFilterBtn;
        private ImageView mCameraChange;
        private static final int maxTime = 15000;//最长录制20s
        private boolean pausing = false;
        private boolean recordFlag = false;//是否正在录制

        private long timeStep = 50;//进度条刷新的时间
        long timeCount = 0;//用于记录录制时间
        private boolean autoPausing = false;
        ExecutorService executorService;
        private SensorControler mSensorControler;
        LinearLayout fanhui,linear,linear2;
        CameraBiz biz;
        RecyclerView horilist;
        RelativeLayout relative;
        @BindView(R.id.changan_rela)
        RelativeLayout changan_rela;
    @BindView(R.id.relative1)
    LinearLayout relative1;
    @BindView(R.id.linear3)
            LinearLayout linear3;
        int width = 720;

        @Override
        protected void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
          hideShijian();
            setContentView(R.layout.activity_camera);
            ButterKnife.bind(this);
            WindowManager wm = (WindowManager)
                    getSystemService(Context.WINDOW_SERVICE);
            width = wm.getDefaultDisplay().getWidth();
            changan_rela.setAlpha(0.5f);
            biz = new CameraBiz(this);
            showProgressDialog("正在加载摄像头");
            executorService = Executors.newSingleThreadExecutor();
            mSensorControler = SensorControler.getInstance();
            mSensorControler.setCameraFocusListener(ActivityCamera.this);

            initView();
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,(int)((4/3f)*width));
            mCameraView.setLayoutParams(lp);
       /*     mCameraView.mCameraDrawer.setPreviewSize(width,(int)((4/3f)*width));*/
            onPosition(pos);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgressDialog();
                    handler.postDelayed(runnable,2000);

                }
            },2000);


    }
    boolean sizeChanged;
        int savedWidth;

        private void changeLayout(View view,LinearLayout ll) {
           // TransitionManager.beginDelayedTransition(ll);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (sizeChanged) {
            /*    params.width = savedWidth;*/
                params.gravity = Gravity.CENTER;
            } else {
                savedWidth = params.width;
              /*  params.width = 100;*/
                params.gravity = Gravity.BOTTOM;
            }
            view.setLayoutParams(params);
        }

        @Override
        public View getView() {
            return linear2;
        }

        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.btn_camera_filter:
                    if(!sizeChanged){
                        horilist.setVisibility(View.VISIBLE);
                    }else {
                        horilist.setVisibility(View.GONE);
                    }

                    changeLayout(mBeautyBtn,relative1);
                    changeLayout(mCapture,linear3);
                    changeLayout(mFilterBtn,linear);
                  /*  linear.setVisibility(View.VISIBLE);
                    linear2.setVisibility(View.GONE);*/
                    sizeChanged = !sizeChanged;
                    break;

                case R.id.bili:
                    biz.onBili(bili,mCameraView,relative,horilist);
                    break;
                case R.id.fanhui:
                    finish();
                    break;
                case R.id.btn_camera_switch:
                    mCameraView.switchCamera();
                    if (mCameraView.getCameraId() == 1) {
                        //前置摄像头 使用美颜
                        mCameraView.changeBeautyLevel(3);
                    } else {
                        //后置摄像头不使用美颜
                        mCameraView.changeBeautyLevel(0);
                    }

                    break;
                case R.id.mCapture:
                    mCameraView.tackPicture();
                    break;

                case R.id.btn_camera_beauty:

                   startActivity(new Intent(this,FileActivity.class));

                    break;
                case R.id.more:
                    biz.onShanguandeng(more,mCameraView);
                    break;
                case R.id.tongkuan:
                    ActivityUtils.goTongkuanActivity(ActivityCamera.this);
                    break;


            }
        }

        @Override
        public Activity getActivity() {
            return this;
        }

        @Override
        public void onPosition(int position) {
            pos = position;
            mCameraView.onPos(position);
        }

        TextView bili;
        ImageView more,tongkuan;
        private void initView () {
            relative = findViewById(R.id.relative);
            horilist = findViewById(R.id.horilist);
            linear = findViewById(R.id.linear);
            linear2 = findViewById(R.id.linear2);
            fanhui = findViewById(R.id.fanhui);
            fanhui.setOnClickListener(this);
        mCameraView = (CameraView) findViewById(R.id.camera_view);
        mCapture = (CircularProgressView) findViewById(R.id.mCapture);
        mFocus = (FocusImageView) findViewById(R.id.focusImageView);
        mBeautyBtn = (ImageView) findViewById(R.id.btn_camera_beauty);
        mFilterBtn = (ImageView) findViewById(R.id.btn_camera_filter);
        mCameraChange = (ImageView) findViewById(R.id.btn_camera_switch);
            tongkuan = findViewById(R.id.tongkuan);
            tongkuan.setOnClickListener(this);
            mFilterBtn.setOnClickListener(this);
        mBeautyBtn.setOnClickListener(this);
        mCameraView.setOnTouchListener(this);
        mCameraView.setOnFilterChangeListener(this);
        mCameraChange.setOnClickListener(this);
        mCapture.setTotal(maxTime);
        mCapture.setOnClickListener(this);
            bili = findViewById(R.id.bili);
            bili.setOnClickListener(this);
            more = findViewById(R.id.more);
            more.setOnClickListener(this);


        mCameraView.onTackPicture(new ICamera.TakePicture() {
            @Override
            public void takePicture(byte[] bytes, Camera camera) {
                biz.dealWithCameraData(bytes,type1,pos,mCameraView);
                mCameraView.onResume();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        show("拍照成功");
                    }
                });
                if (recordFlag && autoPausing) {
                    mCameraView.resume(true);
                    autoPausing = false;
                }
            }
        });

            mCapture.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(ContextCompat.checkSelfPermission(ActivityCamera.this, android.Manifest.permission.RECORD_AUDIO)
                            != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(ActivityCamera.this,new String[]{
                                android.Manifest.permission.RECORD_AUDIO},1);
                    }else {
                        if (!recordFlag) {
                            executorService.execute(recordRunnable);
                        } else if (!pausing) {
                            mCameraView.pause(false);
                            pausing = true;
                        } else {
                            mCameraView.resume(false);
                            pausing = false;
                        }
                        in = 1;
                    }

                    return true;
                }
            });
            mCapture.setOnLongTouchListener(new CircularProgressView.LongTouchListener() {
                @Override
                public void onLongTouch() {

                }

                @Override
                public void onTaishou() {
                    if(in ==1&&null!=savePath){
                        recordFlag = false;
                        mCameraView.stopRecord();
                    }
                    in=0;
                }
            },1000);


            mCameraView.mCamera.setOnPreviewFrameCallback(new ICamera.PreviewFrameCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, int width, int height) {
                    Bitmap bm0 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }
            });
          final  GuoLvAdapter1 adapter = new GuoLvAdapter1(this,Arrays.asList(SlideGpuFilterGroup.types));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horilist.setLayoutManager(linearLayoutManager);
            horilist.setAdapter(adapter);
            horilist.setBackgroundResource(R.color.colorWhite);
            adapter.setOnFilterChangeListener(new GuoLvAdapter1.onFilterChangeListener() {
                @Override
                public void onFilterChanged(int filterType) {
                    adapter.setWeizhi(filterType);
                    onPosition(filterType);
                    adapter.notifyItemChanged(filterType);
                    if(host!=-1){
                        adapter.notifyItemChanged(host);
                    }
                    host = filterType;

                }
            });
    }
    int host =-1;
    int in = 0;
    int pos = 0;
        @Override
        public boolean onTouch (View v, MotionEvent event){
          /*  mCameraView.onTouch(event);*/
        if (mCameraView.getCameraId() == 1) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float sRawX = event.getRawX();
                float sRawY = event.getRawY();
                float rawY = sRawY * Constants.screenWidth / Constants.screenHeight;
                float temp = sRawX;
                float rawX = rawY;
                rawY = (Constants.screenWidth - temp) * Constants.screenHeight / Constants.screenWidth;

                Point point = new Point((int) rawX, (int) rawY);
                mCameraView.onFocus(point, callback);
                mFocus.startFocus(new Point((int) sRawX, (int) sRawY));
        }
        return true;
    }
        Camera.AutoFocusCallback callback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                //聚焦之后根据结果修改图片
                if (success) {
                    mFocus.onFocusSuccess();
                } else {
                    //聚焦失败显示的图片
                    mFocus.onFocusFailed();

                }
            }
        };
        @Override
        public void onFocus () {
        if (mCameraView.getCameraId() == 1) {
            return;
        }
        Point point = new Point(Constants.screenWidth / 2, Constants.screenHeight / 2);
        mCameraView.onFocus(point, callback);
    }
        @Override
        public void onBackPressed () {
        if (recordFlag) {
            recordFlag = false;
        } else {
            super.onBackPressed();
        }
    }

        @Override
        protected void onResume () {
        super.onResume();
        try{
            if(null!= mCameraView) {
                mCameraView.onResume();
                if (recordFlag && autoPausing) {
                    mCameraView.resume(true);
                    autoPausing = false;
                }
            }
        }catch (Exception e){

        }

    }
        @Override
        protected void onPause () {
        super.onPause();
        if(null != mCameraView) {
            if (recordFlag && !pausing) {
                mCameraView.pause(true);
                autoPausing = true;
            }
            mCameraView.onPause();
        }
    }
        MagicFilterType type1 = MagicFilterType.NONE;
        @Override
        public void onFilterChange ( final MagicFilterType type){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                type1 = type;
            }
        });

    }


    String savePath;
        Runnable recordRunnable = new Runnable() {
            @Override
            public void run() {
                recordFlag = true;
                pausing = false;
                autoPausing = false;
                timeCount = 0;
                long time = System.currentTimeMillis();
                savePath = Constants.getPath("record/", pos+time + ".mp4");

                try {
                    mCameraView.setSavePath(savePath);
                    mCameraView.startRecord();
                    while (timeCount <= maxTime && recordFlag) {
                        if (pausing || autoPausing) {
                            continue;
                        }
                        mCapture.setProcess((int) timeCount);
                        Thread.sleep(timeStep);
                        timeCount += timeStep;
                    }
                    recordFlag = false;
                    mCameraView.stopRecord();
                    if (timeCount < 2000) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCapture.setProcess(0);
                              show("录像时间太短");
                            }

                        });
                    } else {
                        recordComplete(savePath);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        private void recordComplete ( final String path1){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCapture.setProcess(0);
                show("文件保存路径：" + path1);
                showProgressDialog("正在保存视频");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        AlbumNotifyHelper.insertVideoToMediaStore(ActivityCamera.this,path1);
                        ActivityUtils.goShipinjianji(ActivityCamera.this,path1);
                    }
                },2000);

            }
        });
    }
        ProgressDialog progressDialog;
        public void showProgressDialog( String message) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(this, "请稍等", message, true, false);
            } else if (progressDialog.isShowing()) {
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage(message);
            }
            progressDialog.show();
        }
        public void hideProgressDialog() {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if(null != mCameraView){
                mCameraView.onDestroy();
            }
        }
    }
