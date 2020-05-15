package presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterFactory;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.record.video.GPUImage;
import com.example.cj.videoeditor.widget.CameraView;
import com.iyoyogo.android.ActivityUtils;
import com.iyoyogo.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp.BitmapFileSetting;
import okhttp.LogUtil;
import popupwindow.CustomPopupWindow;
import view.ICameraView;

/**
 * Created by Administrator on 2018/7/20.
 */

public class CameraBiz implements View.OnClickListener{
    ICameraView view;
    int width =720;
    int height=720;
    public CameraBiz (ICameraView view){
        this.view = view;
        WindowManager wm = (WindowManager) view.getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
         width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        LogUtil.v(((16/9f)*width)+"======="+ ((3/4f)*width));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

          /*  case R.id.text:
                if(bili !=0) {
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) ((10 / 7.2f) * width));
                    mCameraView.setLayoutParams(lp);
                    mCameraView.mCameraDrawer.setPreviewSize(width, (int) ((10 / 7.2f) * width));
                }
                popupWindow1.dismiss();
                bili =0;
                break;*/
          case   R.id.text1:
              bili = 1;
              recyclerView.setBackground(null);
              FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
              mCameraView.setLayoutParams(lp1);
              mCameraView.mCameraDrawer.setPreviewSize(width,height);
              popupWindow1.dismiss();
                break;
            case   R.id.text2:
                recyclerView.setBackgroundResource(R.color.colorWhite);
                if(bili !=2) {
                    FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) ((4 / 3f) * width));
                    mCameraView.setLayoutParams(lp2);
                    mCameraView.mCameraDrawer.setPreviewSize(width, (int) ((4 / 3f) * width));
                }
                popupWindow1.dismiss();
                bili =2;
                break;
            case   R.id.text3:
                bili =3;
                recyclerView.setBackgroundResource(R.color.colorWhite);
                FrameLayout.LayoutParams lp3 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)((4/3.5f)*width));
                mCameraView.setLayoutParams(lp3);
                mCameraView.mCameraDrawer.setPreviewSize(width,width);
                popupWindow1.dismiss();
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
    RecyclerView recyclerView;
    public void onBili(final TextView view1, CameraView mCameraView, final RelativeLayout relative, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
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
                       /*v.findViewById(R.id.text).setOnClickListener(CameraBiz.this);*/
                       v.findViewById(R.id.text1).setOnClickListener(CameraBiz.this);
                       v.findViewById(R.id.text2).setOnClickListener(CameraBiz.this);
                       v.findViewById(R.id.text3).setOnClickListener(CameraBiz.this);

                    }
                })
                //开始构建
                .create();
        popupWindow1.showAsDropDown(view1);

        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                String s ="原图";
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
                LogUtil.v(bili+"==================");
                view1.setText(s);
            }
        });

    }

    public void dealWithCameraData(byte[] data, MagicFilterType type1, int pos, CameraView mCameraView){
        try {
            String time =    new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());

            //图片临时保存位置
            String savePath = Constants.getPath("record/", pos+time + ".jpg");
            Bitmap bm0 = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix m = new Matrix();
            if(mCameraView.getCameraId() == 1){

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
            BitmapFileSetting.saveBitmapFile(bm,savePath);
            ActivityUtils.goBianjiActivity(view.getActivity(),savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
