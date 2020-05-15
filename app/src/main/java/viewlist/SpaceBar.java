/**
 * Copyright © 2014 All rights reserved.
 *
 * @Title: SpaceBar.java
 * @Prject: SpaceBar
 * @Package: com.example.spacebar
 * @Description: TODO
 * @author: raot raotao.bj@cabletech.com.cn
 * @date: 2014年10月8日 上午11:52:03
 * @version: V1.0
 */
package viewlist;

import java.math.BigDecimal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iyoyogo.android.R;

import okhttp.LogUtil;


/**
 * @ClassName: SpaceBar
 * @Description: TODO
 * @author: raot raotao.bj@cabletech.com.cn
 * @date: 2014年10月8日 上午11:52:03
 */
public class SpaceBar extends View {

	public interface OnSpaceChangeListener {
		public void onChangeListener(int min, int max);
	}


	OnSpaceChangeListener onSpaceChangeListener;
	/**
	 * @param onSpaceChangeListener
	 *            the onSpaceChangeListener to set
	 */
	public void setOnSpaceChangeListener(
			OnSpaceChangeListener onSpaceChangeListener) {
		this.onSpaceChangeListener = onSpaceChangeListener;
	}

	private int minNum = 0;
	private int maxNum = 100;
	private Paint paint = new Paint();

	private boolean isMinTouch = false;
	private boolean isMaxTouch = false;
	private MotionEvent startEvent;
	private MotionEvent moveEvent;
	private float minTouch = 0;
	private float maxTouch = 0;

	public SpaceBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public SpaceBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.SpaceBar);
		minNum = typedArray.getInteger(R.styleable.SpaceBar_minNum, 0);
		maxNum = typedArray.getInteger(R.styleable.SpaceBar_maxNum, 100);


	}

	public void setNum(int minNum, int maxNum) {
		this.minNum = minNum;
		this.maxNum = maxNum;
		minTouch = 0;
		maxTouch = getWidth() - 40;
		invalidate();
	}
	int top =0;
	int botton =200;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getWidth();
		botton = getHeight();
		int spaceWidth = (width - 40) / 10;
		if (isMinTouch || isMaxTouch) {
			float touchX = 0;
			if (isMinTouch) {
				touchX = minTouch;
			}
			if (isMaxTouch) {
				touchX = maxTouch;
			}
		}
		if (maxTouch == 0) {
			maxTouch = width - 40;
		}
		canvas.drawBitmap(((BitmapDrawable) getResources().getDrawable(
				R.drawable.heng)).getBitmap(), null,
				new Rect((int) minTouch, top, (int) minTouch + 40, botton), paint);
		canvas.drawBitmap(
				((BitmapDrawable) getResources().getDrawable(
						R.drawable.heng)).getBitmap(), null,
				new Rect((int) maxTouch, top, (int) maxTouch + 40, botton), paint);
		paint.reset();
		paint.setColor(Color.parseColor("#F1F1F1"));
		canvas.drawRect(0, top, minTouch , botton, paint);
		canvas.drawRect(maxTouch + 40, top, width +20, botton, paint);
		paint.reset();
		double min = minNum
				+ (double) (minTouch / spaceWidth * (maxNum - minNum) / 10);
		double max = minNum
				+ (double) (maxTouch / spaceWidth * (maxNum - minNum) / 10);
		int min1 = (new BigDecimal(min + "").setScale(0,
				BigDecimal.ROUND_HALF_UP)).intValue();
		int man1 =  (new BigDecimal(
				max + "").setScale(0, BigDecimal.ROUND_HALF_UP))
				.intValue();
		onSpaceChangeListener
				.onChangeListener(min1,man1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		if (action == MotionEvent.ACTION_UP) {
			isMaxTouch = false;
			isMinTouch = false;
			startEvent = null;
			moveEvent = null;
			invalidate();
		} else if (action == MotionEvent.ACTION_MOVE) {
			if (isMinTouch || isMaxTouch) {
				moveEvent = MotionEvent.obtain(event);
				float move = moveEvent.getX() - startEvent.getX();
				if (isMinTouch) {
					if (minTouch + move >= 0 && minTouch + move + 40 < maxTouch) {
						minTouch = minTouch + move;
						invalidate();
						startEvent = MotionEvent.obtain(event);
					} else if (minTouch + move + 40 >= maxTouch
							&& maxTouch + move + 40 <= getWidth()) {
						minTouch = minTouch + move;
						maxTouch = maxTouch + move;
						invalidate();
						startEvent = MotionEvent.obtain(event);
					}
				} else if (isMaxTouch) {
					if (maxTouch + move + 40 <= getWidth()
							&& maxTouch + move > minTouch + 40) {
						maxTouch = maxTouch + move;
						invalidate();
						startEvent = MotionEvent.obtain(event);
					} else if (maxTouch + move <= minTouch + 40
							&& minTouch + move >= 0) {
						minTouch = minTouch + move;
						maxTouch = maxTouch + move;
						invalidate();
						startEvent = MotionEvent.obtain(event);
					}
				}
			}
		} else if (action == MotionEvent.ACTION_DOWN) {
			if (!isMinTouch && !isMaxTouch) {
				startEvent = MotionEvent.obtain(event);
				if (event.getX() - minTouch > 0 && event.getX() - minTouch < 40) {
					isMinTouch = true;
				} else if (event.getX() - maxTouch > 0
						&& event.getX() - maxTouch < 40) {
					isMaxTouch = true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
