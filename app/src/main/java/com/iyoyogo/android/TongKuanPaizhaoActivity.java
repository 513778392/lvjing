package com.iyoyogo.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import adapter.GuoLvAdapter1;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp.LogUtil;
import presenter.impl.TongkuanPaizhaoBiz;
import view.ICameraView;

public class TongKuanPaizhaoActivity extends BaseActivity implements ICameraView, View.OnClickListener, View.OnTouchListener, SensorControler.CameraFocusListener, SlideGpuFilterGroup.OnFilterChangeListener

{
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
    LinearLayout fanhui,linear,linear2,linear3,relative1;
    TongkuanPaizhaoBiz biz;
    RecyclerView horilist;
    RelativeLayout relative;
    @BindView(R.id.changan_rela)
    RelativeLayout changan_rela;
    int type2,width;
    String filepath="/storage/emulated/0/Pictures/record/520180727_135031.jpg";
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(null !=mCameraView) {
                mCameraView.onFocus(null, callback);
                handler.postDelayed(runnable, 2000);
            }
        }
    };
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tong_kuan_paizhao);
        ButterKnife.bind(this);
        filepath = getIntent().getStringExtra("type");
        File file = new File(filepath);
        LogUtil.v(filepath);
        type2 =Integer.valueOf(file.getName().substring(0,1));
        pos = type2;
        changan_rela.setAlpha(0.5f);
        biz = new TongkuanPaizhaoBiz(this);
        executorService = Executors.newSingleThreadExecutor();
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onPosition(pos);
                handler.postDelayed(runnable,2000);

            }
        },1000);
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,(int)((4/3f)*width));
        mCameraView.setLayoutParams(lp);
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
                biz.onBili(bili,mCameraView,relative);
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

              /*  PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setPreviewEnabled(false)
                        .start(TongKuanPaizhaoActivity.this);*/

                break;
            case R.id.more:
                biz.onShanguandeng(more,mCameraView);
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
    int pos =0;
    TextView bili;
    ImageView more;
    private void initView () {
        relative = findViewById(R.id.relative);
        linear3 = findViewById(R.id.linear3);
        relative1 = findViewById(R.id.relative1);
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
                biz.dealWithCameraData(bytes,type1,pos,filepath,mCameraView);
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
      final   GuoLvAdapter1 adapter = new GuoLvAdapter1(this,Arrays.asList(SlideGpuFilterGroup.types));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horilist.setLayoutManager(linearLayoutManager);
        horilist.setAdapter(adapter);
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
    int host=-1;
    int in = 0;

    @Override
    public boolean onTouch (View v, MotionEvent event){
       /* mCameraView.onTouch(event);*/
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
        mCameraView.onResume();
        if (recordFlag && autoPausing) {
            mCameraView.resume(true);
            autoPausing = false;
        }
    }
    @Override
    protected void onPause () {
        super.onPause();
        if (recordFlag && !pausing) {
            mCameraView.pause(true);
            autoPausing = true;
        }
        mCameraView.onPause();
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
                        AlbumNotifyHelper.insertVideoToMediaStore(TongKuanPaizhaoActivity.this,path1);
                        ActivityUtils.goShipinjianji(TongKuanPaizhaoActivity.this,path1);
                    }
                },2000);

            }
        });
    }
    boolean sizeChanged;
    int savedWidth;
    private void changeLayout(View view,LinearLayout ll) {
        // TransitionManager.beginDelayedTransition(ll);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (sizeChanged) {
          /*  params.width = savedWidth;*/
            params.gravity = Gravity.CENTER;
        } else {
            savedWidth = params.width;
          /*  params.width = 100;*/
            params.gravity = Gravity.BOTTOM;
        }
        view.setLayoutParams(params);
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
}
