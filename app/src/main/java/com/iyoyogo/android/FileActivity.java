package com.iyoyogo.android;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapter.FileAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import entity.PhotoDirectory;
import okhttp.LogUtil;
import viewlist.SimpleActionBar;

public class FileActivity extends BaseActivity {

    @BindView(R.id.linear_view)
     LinearLayout linear_view;
    @BindView(R.id.action_bar)
    SimpleActionBar action_bar;

     TextView cancelTv;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    //所有photos的路径
    private List<PhotoDirectory> directories;
    FileAdapter adapter;
    private ArrayList<String> originalPhotos = null;
    @BindView(R.id.tupian)
    TextView tupian;
    @BindView(R.id.shipin)
    TextView shipin;
    @OnClick({R.id.tupian,R.id.shipin,R.id.next_step_tv,R.id.quxiao})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tupian:
                type1=0;
                onAdapter("图片");
                getAllPhotoInfo();
                tupian.setTextColor(getResources().getColor(R.color.title_bar_bg_color));
                shipin.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.shipin:
                type1=1;
             /*   showProgressDialog("正在加载视频");*/
                onAdapter("视频");
                getAllVideoInfos();
                shipin.setTextColor(getResources().getColor(R.color.title_bar_bg_color));
                tupian.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.next_step_tv:
                List<PhotoDirectory> list= new ArrayList<>();
                for (int i = 0; i <directories.size() ; i++) {
                    if(directories.get(i).isXuan()){
                        list.add(directories.get(i));
                    }
                }
                if(list.size()==1){
                 if(type1 ==1) {
                     LogUtil.v(list.get(0).getCoverPath()+"=======================");
                     ActivityUtils.goShipinjianji(this,list.get(0).getCoverPath());
                 }else {
                     ActivityUtils.goBianjiActivity(this,list.get(0).getCoverPath());

                 }
                }
                break;
            case R.id.quxiao:
                for (int i = 0; i <directories.size() ; i++) {
                    if(directories.get(i).isXuan()){
                        directories.get(i).setXuan(false);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
    int type1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.bind(this);
        directories = new ArrayList<>();

        init();
        onAdapter("图片");
      tupian.setTextColor(getResources().getColor(R.color.title_bar_bg_color));
        getAllPhotoInfo();

    }
    private void onAdapter(String type){
        directories.clear();
        adapter = new FileAdapter(this,directories,type);
        GridLayoutManager gm = new GridLayoutManager(this,4);
        recycler_view.setLayoutManager(gm);
        recycler_view.setAdapter(adapter);
        adapter.setOnClickListener(new FileAdapter.OnClick() {
            @Override
            public void onClick(int pos, View v) {
                directories.get(pos).setXuan(!directories.get(pos).isXuan());
                adapter.notifyItemChanged(pos);
            }
        });

    }

    private void init(){


    }

    /**
     * 读取手机中所有图片信息
     */
    private void getAllPhotoInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String[] projImage = { MediaStore.Images.Media._ID
                        , MediaStore.Images.Media.DATA
                        ,MediaStore.Images.Media.SIZE
                        ,MediaStore.Images.Media.DISPLAY_NAME};
                Cursor mCursor = getContentResolver().query(mImageUri,
                        projImage,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED+" desc");

                if(mCursor!=null){
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //用于展示相册初始化界面

                        // 获取该图片的父路径名
                        String dirPath = new File(path).getParentFile().getAbsolutePath();
                        //存储对应关系
                        PhotoDirectory photo = new PhotoDirectory();
                        photo.setCoverPath(path);
                        directories.add(photo);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adapter.notifyDataSetChanged();
                        }
                    });
                    mCursor.close();
                }

            }
        }).start();
    }


    /**
     * 获取手机中所有视频的信息
     */
    private void getAllVideoInfos(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ALPHA_8;
                ContentResolver contentResolver = getContentResolver();
                Cursor mCursor = contentResolver.query(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                        null, null);
               final List<PhotoDirectory> list = new ArrayList<>();
                if(mCursor!=null){
                    while (mCursor.moveToNext()) {
                        // 获取视频的路径
                        int videoId = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media._ID));
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                      /*  int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.SIZE))/1024; //单位kb
                        if(size<0){
                            //某些设备获取size<0，直接计算
                            Log.e("dml","this video size < 0 " + path);
                            size = new File(path).length()/1024;

                        }
                        String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long modifyTime = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));//暂未用到

                        //提前生成缩略图，再获取：http://stackoverflow.com/questions/27903264/how-to-get-the-video-thumbnail-path-and-not-the-bitmap

                        String[] projection = { MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA};
                        Cursor cursor = getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI
                                , projection
                                , MediaStore.Video.Thumbnails.VIDEO_ID + "=?"
                                , new String[]{videoId+""}
                                , null);
                        String thumbPath = "";
                        while (cursor.moveToNext()){
                            thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                        }
                        cursor.close();

                        // 获取该视频的父路径名
                        String dirPath = new File(path).getParentFile().getAbsolutePath();*/
                        //存储对应关系
                        PhotoDirectory photo = new PhotoDirectory();
                        photo.setCoverPath(path);
                        list.add(photo);


                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = list.size()-1; i >-1 ; i--) {
                                LogUtil.v(i+"================");
                                directories.add(list.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                    mCursor.close();
                }

            }
        }).start();
    }

}
