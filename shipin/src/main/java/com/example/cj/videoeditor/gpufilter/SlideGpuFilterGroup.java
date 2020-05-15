package com.example.cj.videoeditor.gpufilter;

import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.MyApplication;
import com.example.cj.videoeditor.gpufilter.basefilter.GPUImageFilter;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterFactory;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.utils.EasyGlUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import videoeditor.cj.example.com.shipin.R;


/**
 * Created by cj on 2017/7/20 0020.
 * 滑动切换滤镜的控制类
 */

public class SlideGpuFilterGroup {
    public static int FilterType2Name(MagicFilterType filterType){
        switch (filterType) {
            case NONE:
                return R.string.filter_none;
            case WHITECAT:
                return R.string.filter_whitecat;
            case BLACKCAT:
                return R.string.filter_blackcat;
            case ROMANCE:
                return R.string.filter_romance;
            case SAKURA:
                return R.string.filter_sakura;
            case AMARO:
                return R.string.filter_amaro;
            case BRANNAN:
                return R.string.filter_brannan;
            case BROOKLYN:
                return R.string.filter_brooklyn;
            case EARLYBIRD:
                return R.string.filter_Earlybird;
            case FREUD:
                return R.string.filter_freud;
            case HEFE:
                return R.string.filter_hefe;
            case HUDSON:
                return R.string.filter_hudson;
            case INKWELL:
                return R.string.filter_inkwell;
            case KEVIN:
                return R.string.filter_kevin;
            case LOMO:
                return R.string.filter_lomo;
            case N1977:
                return R.string.filter_n1977;
            case NASHVILLE:
                return R.string.filter_nashville;
            case PIXAR:
                return R.string.filter_pixar;
            case RISE:
                return R.string.filter_rise;
            case SIERRA:
                return R.string.filter_sierra;
            case SUTRO:
                return R.string.filter_sutro;
            case TOASTER2:
                return R.string.filter_toastero;
            case VALENCIA:
                return R.string.filter_valencia;
            case WALDEN:
                return R.string.filter_walden;
            case XPROII:
                return R.string.filter_xproii;
            case ANTIQUE:
                return R.string.filter_antique;
            case CALM:
                return R.string.filter_calm;
            case COOL:
                return R.string.filter_cool;
            case EMERALD:
                return R.string.filter_emerald;
            case EVERGREEN:
                return R.string.filter_evergreen;
            case FAIRYTALE:
                return R.string.filter_fairytale;
            case HEALTHY:
                return R.string.filter_healthy;
            case NOSTALGIA:
                return R.string.filter_nostalgia;
            case TENDER:
                return R.string.filter_tender;
            case SWEETS:
                return R.string.filter_sweets;
            case LATTE:
                return R.string.filter_latte;
            case WARM:
                return R.string.filter_warm;
            case SUNRISE:
                return R.string.filter_sunrise;
            case SUNSET:
                return R.string.filter_sunset;
            case SKINWHITEN:
                return R.string.filter_skinwhiten;
            case CRAYON:
                return R.string.filter_crayon;
            case SKETCH:
                return R.string.filter_sketch;
            default:
                return R.string.filter_none;
        }
    }
    public static final MagicFilterType[] types = new MagicFilterType[]{
            MagicFilterType.NONE,
            MagicFilterType.INKWELL,
            MagicFilterType.SUNSET,
            MagicFilterType.BRANNAN,
            MagicFilterType.FAIRYTALE,
            MagicFilterType.WHITECAT,
            MagicFilterType.ROMANCE,
            MagicFilterType.AMARO,
            MagicFilterType.BROOKLYN,
            MagicFilterType.N1977,
            MagicFilterType.NASHVILLE,
            MagicFilterType.WALDEN,
            MagicFilterType.RISE,
            MagicFilterType.PIXAR,
            MagicFilterType.HUDSON,
            MagicFilterType.FREUD,
            MagicFilterType.WARM,
            MagicFilterType.SKINWHITEN,
            MagicFilterType.EVERGREEN,
            MagicFilterType.TOASTER2,
           /*
            MagicFilterType.SUNRISE,
            MagicFilterType.BLACKCAT,

            MagicFilterType.HEALTHY,
            MagicFilterType.SWEETS,

            MagicFilterType.SAKURA,

            MagicFilterType.ANTIQUE,
            MagicFilterType.NOSTALGIA,
            MagicFilterType.CALM,
            MagicFilterType.LATTE,
            MagicFilterType.TENDER,
            MagicFilterType.COOL,
            MagicFilterType.EMERALD,

            MagicFilterType.CRAYON,
            MagicFilterType.SKETCH,
            MagicFilterType.EARLYBIRD,

            MagicFilterType.HEFE,

            MagicFilterType.KEVIN,
            MagicFilterType.LOMO,


            MagicFilterType.SIERRA,
            MagicFilterType.SUTRO,

            MagicFilterType.VALENCIA,

            MagicFilterType.XPROII*/
    };

    private GPUImageFilter curFilter;
    private GPUImageFilter leftFilter;
    private GPUImageFilter rightFilter;
    private int width, height;
    private int[] fFrame = new int[1];
    private int[] fTexture = new int[1];
    private int curIndex = 0;
    private Scroller scroller;
    private OnFilterChangeListener mListener;

    public SlideGpuFilterGroup() {
        initFilter();
        scroller = new Scroller(MyApplication.getContext());
    }

    private void initFilter() {
        curFilter = getFilter(getCurIndex());
        leftFilter = getFilter(getLeftIndex());
        rightFilter = getFilter(getRightIndex());
    }

    private GPUImageFilter getFilter(int index) {
        GPUImageFilter filter = MagicFilterFactory.initFilters(types[index]);
        if (filter == null) {
            filter = new GPUImageFilter();
        }
        return filter;
    }
    public GPUImageFilter getCurFilter(){
        return curFilter;
    }
    public void init() {
        curFilter.init();
        leftFilter.init();
        rightFilter.init();
    }

    public void onSizeChanged(int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glGenFramebuffers(1, fFrame, 0);
        EasyGlUtils.genTexturesWithParameter(1, fTexture, 0, GLES20.GL_RGBA, width, height);
        onFilterSizeChanged(width, height);
    }

    private void onFilterSizeChanged(int width, int height) {
        curFilter.onInputSizeChanged(width, height);
        leftFilter.onInputSizeChanged(width, height);
        rightFilter.onInputSizeChanged(width, height);
        curFilter.onDisplaySizeChanged(width, height);
        leftFilter.onDisplaySizeChanged(width, height);
        rightFilter.onDisplaySizeChanged(width, height);
    }

    public int getOutputTexture() {
        return fTexture[0];
    }

    public void onDrawFrame(int textureId) {

        EasyGlUtils.bindFrameTexture(fFrame[0], fTexture[0]);
        if (direction == 0 && offset == 0) {
            curFilter.onDrawFrame(textureId);
        } else if (direction == 1) {
            onDrawSlideLeft(textureId);
        } else if (direction == -1) {
            onDrawSlideRight(textureId);
        }
        EasyGlUtils.unBindFrameBuffer();
    }

    private void onDrawSlideLeft(int textureId) {
        if (locked && scroller.computeScrollOffset()) {
            offset = scroller.getCurrX();
            drawSlideLeft(textureId);
        } else {
            drawSlideLeft(textureId);
            if (locked) {
                if (needSwitch) {
                    reCreateRightFilter();

                    if (mListener != null) {
                        mListener.onFilterChange(types[curIndex]);
                    }
                }
                offset = 0;
                direction = 0;
                locked = false;
            }
        }
    }

    private void onDrawSlideRight(int textureId) {
        if (locked && scroller.computeScrollOffset()) {
            offset = scroller.getCurrX();
            drawSlideRight(textureId);
        } else {
            drawSlideRight(textureId);
            if (locked) {
                if (needSwitch) {
                    reCreateLeftFilter();
                    if (mListener != null) {
                        mListener.onFilterChange(types[curIndex]);
                    }
                }
                offset = 0;
                direction = 0;
                locked = false;
            }
        }
    }

    private void drawSlideLeft(int textureId) {
        GLES20.glViewport(0, 0, width, height);
        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        GLES20.glScissor(0, 0, offset, height);
        leftFilter.onDrawFrame(textureId);
        GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
        GLES20.glViewport(0, 0, width, height);
        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        GLES20.glScissor(offset, 0, width - offset, height);
        curFilter.onDrawFrame(textureId);
        GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
    }

    private void drawSlideRight(int textureId) {
        GLES20.glViewport(0, 0, width, height);
        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        GLES20.glScissor(0, 0, width - offset, height);
        curFilter.onDrawFrame(textureId);
        GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
        GLES20.glViewport(0, 0, width, height);
        GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        GLES20.glScissor(width - offset, 0, offset, height);
        rightFilter.onDrawFrame(textureId);
        GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
    }

    private void reCreateRightFilter() {
        decreaseCurIndex();
        rightFilter.destroy();
        rightFilter = curFilter;
        curFilter = leftFilter;
        leftFilter = getFilter(getLeftIndex());
        leftFilter.init();
        leftFilter.onDisplaySizeChanged(width, height);
        leftFilter.onInputSizeChanged(width, height);
        needSwitch = false;
    }

    private void reCreateLeftFilter() {
        increaseCurIndex();
        leftFilter.destroy();
        leftFilter = curFilter;
        curFilter = rightFilter;
        rightFilter = getFilter(getRightIndex());
        rightFilter.init();
        rightFilter.onDisplaySizeChanged(width, height);
        rightFilter.onInputSizeChanged(width, height);
        needSwitch = false;
    }

    public void destroy() {
        curFilter.destroy();
        leftFilter.destroy();
        rightFilter.destroy();
    }

    private int getLeftIndex() {
        int leftIndex = curIndex - 1;
        if (leftIndex < 0) {
            leftIndex = types.length - 1;
        }
        return leftIndex;
    }

    private int getRightIndex() {
        int rightIndex = curIndex + 1;
        if (rightIndex >= types.length) {
            rightIndex = 0;
        }
        return rightIndex;
    }

    private int getCurIndex() {
        return curIndex;
    }

    private void increaseCurIndex() {
        curIndex++;
        if (curIndex >= types.length) {
            curIndex = 0;
        }
    }

    private void decreaseCurIndex() {
        curIndex--;
        if (curIndex < 0) {
            curIndex = types.length - 1;
        }
    }

    int downX;
    int direction;//0为静止,-1为向左滑,1为向右滑
    int offset;
    boolean locked;
    boolean needSwitch;

    public void onTouchEvent(MotionEvent event) {
        if (locked) {
            return;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (downX == -1) {
                    return;
                }
                int curX = (int) event.getX();
                if (curX > downX) {
                    direction = 1;
                } else {
                    direction = -1;
                }
                offset = Math.abs(curX - downX);
                break;
            case MotionEvent.ACTION_UP:
                if (downX == -1) {
                    return;
                }
                if (offset == 0) {
                    return;
                }
                locked = true;
                downX = -1;
                if (offset > Constants.screenWidth / 3) {
                    scroller.startScroll(offset, 0, Constants.screenWidth - offset, 0, 100 * (1 - offset / Constants.screenWidth));
                    needSwitch = true;
                } else {
                    scroller.startScroll(offset, 0, -offset, 0, 100 * (offset / Constants.screenWidth));
                    needSwitch = false;
                }
                break;
        }
    }

    public void setOnFilterChangeListener(OnFilterChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnFilterChangeListener {
        void onFilterChange(MagicFilterType type);
    }
    public void Change(int pos){

        offset = 0;
        direction = 1;
        locked = true;
        curIndex = pos;
        needSwitch = true;
        zhongjian(pos);
    }
    private void zhongjian(int postion){
        curFilter.destroy();
        curFilter = getFilter(postion);
        curFilter.init();
        mListener.onFilterChange(types[curIndex]);
        curFilter.onDisplaySizeChanged(width, height);
        curFilter.onInputSizeChanged(width, height);
        needSwitch = false;
    }
}
