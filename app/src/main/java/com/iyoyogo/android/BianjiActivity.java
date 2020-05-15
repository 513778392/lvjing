package com.iyoyogo.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.HorizontalListView;
import com.example.cj.videoeditor.gpufilter.SlideGpuFilterGroup;
import com.example.cj.videoeditor.gpufilter.filter.GPUImageSaturationFilter;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterFactory;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.record.video.GPUImage;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import adapter.GuoLvAdapter;
import adapter.GuoLvAdapter1;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caijian.CropImageView;
import okhttp.BitmapFileSetting;
import popupwindow.PopupWindowUtils;
import presenter.impl.CameraBiz;

public class BianjiActivity extends BaseActivity implements View.OnClickListener {

    @OnClick({R.id.guolv_linear,R.id.fan,R.id.caijian_linear,R.id.fan1})
    void onClick1(View view){
        switch (view.getId()){
            case R.id.guolv_linear:
                onGuolv();
                break;
            case R.id.caijian_linear:
                onCaijian();
                break;
            case R.id.fan1:
                finish();
                break;
            case  R.id.fan:
                finish();
                break;

        }
    }
    @BindView(R.id.image)
    ImageView image1;
    String path;
    Bitmap bitmap;
    @BindView(R.id.fan)
    ImageView fan;
    @BindView(R.id.fan1)
    TextView fan1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideShijian();
        setContentView(R.layout.activity_bianji);
        ButterKnife.bind(this);
        path = getIntent().getStringExtra("path");
        bitmap = BitmapFactory.decodeFile(path);
        image1.setImageBitmap(bitmap);

    }

    PopupWindowUtils guolvUtils;
    int host =-1;
    private void onGuolv(){
        pro =1;
        guolvUtils = new PopupWindowUtils(this,R.layout.popup_guolv);
     final    ImageView guolv_image = guolvUtils.getView(R.id.guolv_image);
        guolv_image.setImageBitmap(bitmap);
        RecyclerView guolv_list = guolvUtils.getView(R.id.guolv_list);
      final  SeekBar seekbar = guolvUtils.getView(R.id.seekbar);
        guolvUtils.getView(R.id.baocun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(BianjiActivity.this);
                builder1.setTitle("是否替换原有图片？");
                builder1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bitmap b  = getGPUImageFromAssets(pro);
                        String time =    new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                        String savePath = Constants.getPath("record/", pro+time + ".jpg");
                        BitmapFileSetting.saveBitmapFile(b,savePath);
                        guolvUtils.dismiss();
                        image1.setImageBitmap(b);
                    }
                });
                builder1.setNegativeButton("否", null);
                builder1.show();


            }
        });
        guolvUtils.getView(R.id.fanhui1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guolvUtils.dismiss();
            }
        });

      final  GuoLvAdapter1 adapter = new GuoLvAdapter1(this,Arrays.asList(SlideGpuFilterGroup.types));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        guolv_list.setLayoutManager(linearLayoutManager);
        guolv_list.setAdapter(adapter);
        adapter.setOnFilterChangeListener(new GuoLvAdapter1.onFilterChangeListener() {
            @Override
            public void onFilterChanged(int filterType) {
                adapter.setWeizhi(filterType);
                adapter.notifyItemChanged(filterType);
                if(host!=-1){
                    adapter.notifyItemChanged(host);
                }
                host = filterType;
                seekbar.setProgress(0);
                if(MagicFilterType.NONE!= SlideGpuFilterGroup.types[filterType]) {
                    try{
                        Bitmap b = BitmapFactory.decodeFile(path);
                        bitmap = b;
                        GPUImage image1 = new GPUImage(BianjiActivity.this);
                        image1.setImage(bitmap);
                        image1.setFilter(MagicFilterFactory.initFilters(SlideGpuFilterGroup.types[filterType]));
                        guolv_image.setImageBitmap(image1.getBitmapWithFilterApplied());
                        bitmap = image1.getBitmapWithFilterApplied();

                    }catch (Exception E){
                        guolv_image.setImageBitmap(bitmap);
                    }
                }else {
                    Bitmap b = BitmapFactory.decodeFile(path);
                    bitmap = b;
                    guolv_image.setImageBitmap(bitmap);
                }
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //通过进度条的值更改饱和度
                guolv_image.setImageBitmap(getGPUImageFromAssets(progress));
                pro =progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    int pro =1;
    //根据传进来的数值设置素材饱和度
    public Bitmap getGPUImageFromAssets(int progress){
        // 使用GPUImage处理图像
        Bitmap b =Bitmap.createBitmap(bitmap);
        GPUImage gpuImage = new GPUImage(this);
        gpuImage.setImage(b);
        gpuImage.setFilter(new GPUImageSaturationFilter(progress));
        b = gpuImage.getBitmapWithFilterApplied();
        return b;
    }
    PopupWindowUtils utils;
    CropImageView crop;
    private void onCaijian(){
        if(null == utils) {
            utils = new PopupWindowUtils(this, R.layout.popup_bianjicaijian);
        }
        crop = utils.getView(R.id.image);
        crop.setImageBitmap(bitmap);

        utils.getView(R.id.cai_jian).setOnClickListener(this);
        utils.getView(R.id.caijian2).setOnClickListener(this);
        utils.getView(R.id.caijian3).setOnClickListener(this);
        utils.getView(R.id.caijian4).setOnClickListener(this);
        utils.getView(R.id.caijian5).setOnClickListener(this);
        utils.getView(R.id.quxiao).setOnClickListener(this);
        utils.getView(R.id.queren).setOnClickListener(this);
        utils.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(bili!=0||bili!=1) {
                    fan.setImageResource(R.mipmap.back_black);
                    fan1.setTextColor(getResources().getColor(R.color.black));
                }
                utils = null;
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                crop.onYuantu(bili);
            }
        },500);
    }
    int bili = CameraBiz.bili;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.quxiao:
                utils.dismiss();

                break;
            case R.id.queren:
                AlertDialog.Builder builder = new AlertDialog.Builder(BianjiActivity.this);
                builder.setTitle("是否替换原有图片？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bitmap= crop.getCroppedImage();
                        BitmapFileSetting.saveBitmapFile(bitmap,path);
                        utils.dismiss();
                        image1.setImageBitmap(bitmap);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
                break;
            case R.id.cai_jian:
                bili =0;
                crop.onYuantu(bili);
                break;
            case R.id.caijian2:
                bili =1;
                crop.onYuantu(bili);
                break;
            case R.id.caijian3:
                bili =2;
                crop.onYuantu(bili);
                break;
            case R.id.caijian4:
                bili =3;
                crop.onYuantu(bili);
                break;
            case R.id.caijian5:
                bili =4;
                crop.onYuantu(bili);
                break;
        }
    }
}
