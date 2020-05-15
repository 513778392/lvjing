package com.example.cj.videoeditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.example.cj.videoeditor.utils.DensityUtils;


/**
 * Description:
 * bt_capture
 */
public class CircularProgressView extends android.support.v7.widget.AppCompatImageView {
    /**
     * 记录当前自定义Btn是否按下
     */
    private boolean clickdown = false;

    /**
     * 下拉刷新的回调接口
     */
    private LongTouchListener mListener;
    /**
     * 长按监听接口，使用按钮长按的地方应该注册此监听器来获取回调。
     */
    public interface LongTouchListener {

        /**
         * 处理长按的回调方法
         */
        void onLongTouch();
        void onTaishou();
    }
    /**
     * 按钮长按时 间隔多少毫秒来处理 回调方法
     */
    private int mtime;

    private int mStroke=5;
    private int mProcess=0;
    private int mTotal=100;
/*    private int mNormalColor=0xFFFFFFFF;
    private int mSecondColor=0xFFFEE300;*/
private int mNormalColor=0xFFFEE300;
    private int mSecondColor= Color.parseColor("#ff0000") ;
    private int mStartAngle=-90;
    private RectF mRectF;

    private Paint mPaint;
    private Drawable mDrawable;

    public CircularProgressView(Context context) {
        this(context,null);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mStroke= DensityUtils.dp2px(getContext(),mStroke);
        mPaint=new Paint();
        mPaint.setColor(mNormalColor);
        mPaint.setStrokeWidth(mStroke);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mDrawable=new Progress();
        setImageDrawable(mDrawable);
    }

    public void setTotal(int total){
        this.mTotal=total;
        mDrawable.invalidateSelf();
    }

    public void setProcess(int process){
        this.mProcess=process;
        post(new Runnable() {
            @Override
            public void run() {
                mDrawable.invalidateSelf();
            }
        });
        Log.e("wuwang","process-->"+process);
    }

    public int getProcess(){
        return mProcess;
    }

    public void setStroke(float dp){
        this.mStroke= DensityUtils.dp2px(getContext(),dp);
        mPaint.setStrokeWidth(mStroke);
        mDrawable.invalidateSelf();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams().width== ViewGroup.LayoutParams.WRAP_CONTENT){
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        }else{
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }
    /**
     * 处理touch事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            clickdown = true;
            new LongTouchTask().execute();

            Log.i("huahua", "按下");
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            clickdown = false;
            Log.i("huahua", "弹起");
        }
        return super.onTouchEvent(event);
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param time
     *      指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理长按的任务
     */
    class LongTouchTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while(clickdown)
            {
                sleep(mtime);
                publishProgress(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mListener.onTaishou();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mListener.onLongTouch();
        }

    }

    /**
     * 给长按btn控件注册一个监听器。
     *
     * @param listener
     *      监听器的实现。
     * @param time
     *      多少毫秒时间间隔 来处理一次回调方法
     */
    public void setOnLongTouchListener(LongTouchListener listener, int time) {
        mListener = listener;
        mtime = time;

    }

    private class Progress extends Drawable {
        @Override
        public void draw(Canvas canvas) {
            int width=getWidth();
            int pd=mStroke/2+1;
            if(mRectF==null){
                mRectF=new RectF(pd,pd,width-pd,width-pd);
            }
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mNormalColor);
            canvas.drawCircle(width/2,width/2,width/2-pd,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(mRectF,mStartAngle,mProcess*360/(float)mTotal,false,mPaint);
        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSPARENT;
        }
    }

}
