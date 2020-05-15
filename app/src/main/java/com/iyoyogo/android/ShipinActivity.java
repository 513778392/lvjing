package com.iyoyogo.android;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cj.videoeditor.AlbumNotifyHelper;
import com.example.cj.videoeditor.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapter.ShipinAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp.LogUtil;
import shipinjianqie.Mp4ParseUtil;
import viewlist.SpaceBar;

public class ShipinActivity extends BaseActivity {

    VideoView videoView;
    String path ="/storage/emulated/0/Pictures/record/1534122848714.mp4";
    SpaceBar space;
    long min1=0,max1 =0;
    double start,end;
    @OnClick({R.id.que,R.id.qu,R.id.fanhui})
    void onClick(View view){
        switch (view.getId()){
            case R.id.que:
                AlertDialog.Builder builder = new AlertDialog.Builder(ShipinActivity.this);
                builder.setTitle("是否保存好裁剪的视频？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(min1!=0||shichang!=max1) {
                            if(max1-min1>3000&&shichang>6000) {
                                try {
                                    String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                                    //Mp4ParseUtil.cropMp4(path,min1,max1, Constants.getPath("record/", time + ".mp4"));
                                    String path1 = Constants.getPath("record/", time + ".mp4");
                                    start = (double) min1 / 1000;
                                    end =(double)max1 / 1000;
                                    // Mp4ParseUtil.clipVideo(path, b, (double)max1 / 1000,path1);
                                    //
                                    onJianqie(path1);
                                    show("视频剪辑完成！");
                                    Uri uri = Uri.parse(path1);
                                    videoView.setVideoURI(uri);
                                    shichang = Long.valueOf(getPlayTime(path1));
                                    File f = new File(path);
                                    path = path1;
                                    AlbumNotifyHelper.insertVideoToMediaStore(ShipinActivity.this,path);
                                    space.setNum(0, 100);
                                    bianji =0;
                                } catch (Exception e) {
                                    show("视频剪辑失败！");
                                    LogUtil.v(e.toString());
                                }
                            }else {
                                show("视频剪辑不能少于5秒");
                            }
                        }else {
                            show("视频已保存");
                            finish();
                        }
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
                break;
            case R.id.qu:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShipinActivity.this);
                builder1.setTitle("是否退出裁剪视频？");
                builder1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder1.setNegativeButton("否", null);
                builder1.show();
                break;
            case R.id.fanhui:
                finish();
                break;
        }


    }
    private void onJianqie(String outpath){
        try{
            Mp4ParseUtil.clipVideo(path, outpath,start,end );
        }catch (Exception e){
            start++;
            LogUtil.v("============"+start);
            if(end<start){
                show("视频剪辑失败！");
            }else {
                onJianqie(outpath);
            }
        }
    }

    long shichang;
    @BindView(R.id.shichang)
    TextView shichang1;
    long min2=0,mai2=0;
    int bianji=0;
    /*  @BindView(R.id.doubleslide_withoutrule)
      DoubleSlideSeekBar doubleslide_withoutrule;*/
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    ShipinAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);

        videoView = findViewById(R.id.videoView);
        path = getIntent().getStringExtra("path");
        //    path = "/storage/emulated/0/DCIM/ScreenRecorder/Screenrecorder-2018-08-10-09-40-16-7.mp4";
        LogUtil.v(path+"==============");

        getVideo();

        getBitmap();

    }
    private void getBitmap(){
        LinearLayoutManager manager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager.setOrientation(LinearLayout.HORIZONTAL);

        recycler_view.setLayoutManager(manager);
        new ExtractTask(path).execute();
    }


    /**
     * 抽取关键帧的异步任务
     * @author howie
     *
     */
    class ExtractTask extends AsyncTask<Void, Void, List<Bitmap>> {
        private String path;
        private ProgressDialog progressDialog1;

        public ExtractTask(String path) {
            super();
            this.path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog1==null){
                progressDialog1 = ProgressDialog.show(ShipinActivity.this,
                        "加载中...", "请稍等...", true);
            }else{
                progressDialog1.show();
            }

        }
        @Override
        protected List<Bitmap> doInBackground(Void... arg0) {
            return addFrames(path);
        }
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            lists = bitmaps;
            progressDialog1.dismiss();
            progressDialog1.cancel();
            progressDialog1=null;
            adapter = new ShipinAdapter(ShipinActivity.this,bitmaps);
            recycler_view.setAdapter(adapter);
            videoView.setBackground(new BitmapDrawable(getResources(),decodeFrame(1)));
        }

    }
    List<Bitmap> lists ;

    private ArrayList<Integer> keyFrameList;
    private List<Bitmap> addFrames(String path) {
        Log.v("",path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        // 取得视频的长度(单位为毫秒)
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int duration = Integer.parseInt(time);
        int toatalFrames = duration / 1000;
        keyFrameList = new ArrayList<Integer>();
        int interval = 0;

        for (int i = 0; i < 10; i++) {//
            int frameTime = Integer.valueOf(interval) / 1000;
            keyFrameList.add(frameTime);
            interval += duration / 10;
        }
        List<Bitmap> bits=new ArrayList<Bitmap>();
        for (int i = 0; i < keyFrameList.size(); i++) {
            Bitmap bitmap = retriever.getFrameAtTime(keyFrameList.get(i) * 1000 * 1000,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            if(bitmap!=null){
                int bmpWidth=bitmap.getWidth();
                int bmpHeight=bitmap.getHeight();
                float scale=(float) 0.7;
		        /* 产生reSize后的Bitmap对象 */
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bmpWidth,
                        bmpHeight,matrix,true);
                bits.add(resizeBmp);
            }
        }
        return bits;
    }

    private void getVideo(){
        videoView = (VideoView)this.findViewById(R.id.videoView );
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        //设置视频路径

        space = findViewById(R.id.space);

        space.setOnSpaceChangeListener(new SpaceBar.OnSpaceChangeListener() {
            @Override
            public void onChangeListener(int min, int max) {

               bianji ++;
                min1 = min*shichang/100;
                max1 = max*shichang/100;
                if(min1 !=min2){
                    decodeFrame(min);
                    shichang1.setText("剪辑时间"+(min1/1000)+"s到"+(max1/1000)+"s");
                }
                if(max1 !=mai2){
                    decodeFrame(max);
                    shichang1.setText("剪辑时间"+(min1/1000)+"s到"+(max1/1000)+"s");
                }

                min2 = min1;
                mai2 = max1;


            }
        });
        try {
            Uri uri = Uri.parse(path);
            shichang = Long.valueOf(getPlayTime(path));
            videoView.setVideoURI(uri);
            shichang1.setText("视频时长" + (shichang / 1000) + "S");

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                                videoView.setBackgroundColor(Color.TRANSPARENT);
                            return true;
                        }
                    });

                }
            });
        }catch (Exception e){
            show("视频有问题不能读取");
        }
    }

    /**
     * 获取视频某一帧
     * @param timeMs 毫秒
     * @param listener
     */
    MediaMetadataRetriever  retriever;
    String  fileLength;
    public  Bitmap b = null;
    public Bitmap decodeFrame(int timeMs){
        Bitmap bitmap = null;
        if(null != lists){
            int pos = (int) timeMs/10;
            if(pos>=lists.size()){
                pos= lists.size()-1;
            }
            bitmap =lists.get(pos);
        }
        if(null != bitmap) {
            b = bitmap;
            videoView.setBackground(new BitmapDrawable(b));
        }
        return b;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retriever.release();

    }
    public  String  getPlayTime(String mUri){
        retriever = new MediaMetadataRetriever();
        String  duration ="0";
        retriever.setDataSource(mUri);
        duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
        return duration;
    }

}
