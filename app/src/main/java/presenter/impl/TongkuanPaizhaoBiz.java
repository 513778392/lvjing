package presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.gpufilter.filter.GPUImageSaturationFilter;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterFactory;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.record.video.GPUImage;
import com.example.cj.videoeditor.widget.CameraView;
import com.iyoyogo.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp.BitmapFileSetting;
import okhttp.LogUtil;
import popupwindow.CustomPopupWindow;
import popupwindow.PopupWindowUtils;
import view.ICameraView;
import viewlist.DrawableSwitch;

/**
 * Created by Administrator on 2018/7/26.
 */

public class TongkuanPaizhaoBiz implements View.OnClickListener{
    ICameraView view;
    int width =720;
    int height=720;
    public TongkuanPaizhaoBiz (ICameraView view){
        this.view = view;
        WindowManager wm = (WindowManager) view.getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          /*  case R.id.text:

                if(bili!=0) {
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) ((10 / 7.2f) * width));
                    mCameraView.setLayoutParams(lp);
                    mCameraView.mCameraDrawer.setPreviewSize(width, (int) ((10 / 7.2f) * width));
                }
                popupWindow1.dismiss();
                bili=0;
            break;*/
            case   R.id.text1:
                bili = 1;
                FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                mCameraView.setLayoutParams(lp1);
                mCameraView.mCameraDrawer.setPreviewSize(width,height);
                popupWindow1.dismiss();
                break;
            case   R.id.text2:
                if(bili!=2) {
                    FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) ((4 / 3f) * width));
                    mCameraView.setLayoutParams(lp2);
                    mCameraView.mCameraDrawer.setPreviewSize(width, (int) ((4 / 3f) * width));
                }
                bili =2;
                popupWindow1.dismiss();
                break;
            case   R.id.text3:
                bili =3;
                FrameLayout.LayoutParams lp3 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)((4/3.5f)*width));
                mCameraView.setLayoutParams(lp3);
                mCameraView.mCameraDrawer.setPreviewSize(width,width);
                popupWindow1.dismiss();
                break;
            case R.id.fanhui:
                popupWindow.dismiss();
                popupWindow = null;
                break;

            case R.id.chongpai:
                popupWindow.dismiss();
                popupWindow = null;
                break;

        }
    }
    CustomPopupWindow shanguang;
    String shan = "guang";
    public void onShanguandeng(final ImageView view1,final CameraView camera){
        shanguang = new CustomPopupWindow.Builder(view.getActivity())
                //设置PopupWindow布局
                .setView(R.layout.popup_shanguangdeng)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.BottomInAndOutStyle)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                // .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                .setViewOnclickListener(new CustomPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View v, int layoutResId) {
                        RadioGroup rg = v.findViewById(R.id.shanguang);
                        RadioButton guang= v.findViewById(R.id.guang);
                        RadioButton kai = v.findViewById(R.id.kai);
                        switch (shan){
                            case "kai":
                                kai.setChecked(true);
                                break;
                            default:
                                guang.setChecked(true);
                                break;
                        }
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                switch (checkedId){
                                    case R.id.guang:
                                        camera.mCamera.onShanguan("guang");
                                        shan = "guang";
                                        break;
                                    case R.id.kai:
                                        camera.mCamera.onShanguan("kai");
                                        shan = "kai";
                                        break;
                                }
                            }
                        });

                    }
                })
                //开始构建
                .create();
        shanguang.showAsDropDown(view1);

        shanguang.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                view1.setImageResource(R.drawable.more);
            }
        });
        view1.setImageResource(R.drawable.more_hei);
    }
    public static   int bili =2;
    CustomPopupWindow popupWindow1;
    CameraView mCameraView;
    public void onBili(final TextView view1,final CameraView mCameraView,final RelativeLayout relative){
        this.mCameraView = mCameraView;
        popupWindow1 = new CustomPopupWindow.Builder(view.getActivity())
                //设置PopupWindow布局
                .setView(R.layout.popup_bili)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.BottomInAndOutStyle)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                // .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                .setViewOnclickListener(new CustomPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View v, int layoutResId) {
                      /*  v.findViewById(R.id.text).setOnClickListener(TongkuanPaizhaoBiz.this);*/
                        v.findViewById(R.id.text1).setOnClickListener(TongkuanPaizhaoBiz.this);
                        v.findViewById(R.id.text2).setOnClickListener(TongkuanPaizhaoBiz.this);
                        v.findViewById(R.id.text3).setOnClickListener(TongkuanPaizhaoBiz.this);

                    }
                })
                //开始构建
                .create();
        popupWindow1.showAsDropDown(view1);

        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                String s ="默认";
                switch (bili){
                  /*  case 0:
                        s = "默认";
                        relative.setBackground(null);
                        break;*/
                    case 1:
                        s ="16:9";
                        relative.setBackground(null);
                        break;
                    case 2:
                        s ="4:3";
                        relative.setBackgroundResource(R.color.black);
                        break;
                    case 3:
                        s= "1:1";
                        relative.setBackgroundResource(R.color.black);
                        break;
                    default:
                        relative.setBackground(null);
                        s="4:3";
                        break;
                }

                view1.setText(s);
            }
        });

    }
    PopupWindowUtils popupWindow;
    Bitmap bitmap;
    String path;
    LinearLayout chongpai;
    ImageView tongkuan_iv,baocun,duibi_iv1,duibi_iv2,back_icon;
    TextView duibi;
    boolean isDuibi;
    RelativeLayout duibi_lin;
    DrawableSwitch drawableSwitch;
    Bitmap bb=null;
    public void onBianji(final String path,Bitmap bitmap,String filepath){
        this.bitmap = bitmap;
        this.path = path;
        if(null == popupWindow) {
            popupWindow = new PopupWindowUtils(view.getActivity(),R.layout.popup_tongkuanpai);
        }
        duibi_lin= popupWindow.getView(R.id.duibi_lin);
        SeekBar seekbar = popupWindow.getView(R.id.seekbar);


        tongkuan_iv = popupWindow.getView(R.id.tongkuan_iv);
        tongkuan_iv.setImageBitmap(bitmap);
        chongpai = popupWindow.getView(R.id.chongpai);
        chongpai.setOnClickListener(this);
        duibi_iv1 = popupWindow.getView(R.id.duibi_iv1);
        duibi_iv2 = popupWindow.getView(R.id.duibi_iv2);
        duibi_iv1.setImageBitmap(bitmap);
        Bitmap b = BitmapFactory.decodeFile(filepath);
        LogUtil.v(filepath);
        duibi_iv2.setImageBitmap(b);
        baocun = popupWindow.getView(R.id.baocun);
        duibi = popupWindow.getView(R.id.duibi);
        back_icon = popupWindow.getView(R.id.back_icon);
        drawableSwitch = popupWindow.getView(R.id.drawableSwitch);
        drawableSwitch.startGO();
        drawableSwitch.setOnMbClickListener(new DrawableSwitch.OnMClickListener() {
            @Override
            public void onClick(boolean isRight) {
                if(isRight){
                    duibi_lin.setVisibility(View.VISIBLE);
                    tongkuan_iv.setVisibility(View.GONE);
                    back_icon.setImageResource(R.mipmap.back_black);

                }else {
                    duibi_lin.setVisibility(View.GONE);
                    tongkuan_iv.setVisibility(View.VISIBLE);
                    back_icon.setImageResource(R.mipmap.back_icon);

                }
            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
      /*  duibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDuibi){
                    duibi_lin.setVisibility(View.GONE);
                    tongkuan_iv.setVisibility(View.VISIBLE);
                    back_icon.setImageResource(R.mipmap.back_icon);
                }else {
                    duibi_lin.setVisibility(View.VISIBLE);
                    tongkuan_iv.setVisibility(View.GONE);
                    back_icon.setImageResource(R.mipmap.back_black);

                }
                isDuibi = !isDuibi;
            }
        });*/
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bb  = getGPUImageFromAssets(pro);
                BitmapFileSetting.saveBitmapFile(bb,path);
                Toast.makeText(view.getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow=null;
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //通过进度条的值更改饱和度
                tongkuan_iv.setImageBitmap(getGPUImageFromAssets(progress));
                duibi_iv1.setImageBitmap(getGPUImageFromAssets(progress));
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
        GPUImage gpuImage = new GPUImage(view.getActivity());
        gpuImage.setImage(b);
        gpuImage.setFilter(new GPUImageSaturationFilter(progress));
        b = gpuImage.getBitmapWithFilterApplied();
        return b;
    }
    public void dealWithCameraData(byte[] data, MagicFilterType type1, int pos, String file, CameraView mCameraView){
        try {
            String time =    new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());

            //图片临时保存位置
            String savePath = Constants.getPath("record/", pos+time + ".jpg");
            Bitmap bm0 = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix m = new Matrix();
            if(mCameraView.getCameraId() == 1){
                mCameraView.mCamera.onShanguan("guang");
                bm0 = BitmapFileSetting.turnCurrentLayer(bm0, -1, 1);
                m.setRotate(90,(float) bm0.getWidth() / 2, (float) bm0.getHeight() / 2);
            }else {
                m.setRotate(90,(float) bm0.getWidth() / 2, (float) bm0.getHeight() / 2);
            }
            Bitmap bm = Bitmap.createBitmap(bm0, 0, 0, bm0.getWidth(), bm0.getHeight(), m, true);
            if(type1 != MagicFilterType.NONE) {
                GPUImage image = new GPUImage(view.getActivity());
                image.setImage(bm);
                image.setFilter(MagicFilterFactory.initFilters(type1));
                bm = image.getBitmapWithFilterApplied();
            }
         //   BitmapFileSetting.saveBitmapFile(bm,savePath);
            onBianji(savePath,bm,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
