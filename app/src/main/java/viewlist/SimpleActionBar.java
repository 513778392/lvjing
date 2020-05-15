package viewlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;


public class SimpleActionBar extends RelativeLayout {

    private ImageView mBackIcon;
    private TextView mTitleTextView;        //标题
    private OnClickListener mBackListener;
    private OnClickListener mMenuItemListener;

    private ViewGroup mMenuContainer;      //MenuItem
    private View mContentMenuView;
    private int mMenuItemPadding;

    private LinearLayout mLeftMenuContainer;

    private int mMenuItemResId = R.drawable.actionbar_menuitem_selector;


    public SimpleActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMenuItemPadding = getResources().getDimensionPixelSize(R.dimen.actionbar_menuitem_padding);
    }

    public void setMenuItemPadding(int padding) {
        mMenuItemPadding = padding;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mBackIcon = (ImageView) findViewById(R.id.back_icon);
        mBackIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackListener != null) {
                    mBackListener.onClick(v);
                } else if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });

        mTitleTextView = (TextView) findViewById(R.id.title);
        mMenuContainer = (ViewGroup) findViewById(R.id.menu_container);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBackIcon() {
        mBackIcon.setVisibility(GONE);
    }

    /**
     * 显示返回按钮
     */
    public void showBackIcon() {
        mBackIcon.setVisibility(View.VISIBLE);
    }

    public void setOnBackClickListener(OnClickListener listener) {
        mBackListener = listener;
        if (listener == null) {
            mBackIcon.setEnabled(false);
        } else {
            mBackIcon.setEnabled(true);
        }
    }

    public void setOnMenuItemClickListener(OnClickListener listener) {
        mMenuItemListener = listener;
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(resId);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void hideTitle() {
        if (mTitleTextView != null) {
            mTitleTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int color) {
        mTitleTextView.setTextColor(color);
    }

    /**
     * 设置返回图标样式
     *
     * @param resId
     */
    public void setBackIcon(int resId) {
        mBackIcon.setImageResource(resId);
    }

    /**
     * 设置返回图标样式
     *
     * @param drawable
     */
    public void setBackIcon(Drawable drawable) {
        if (drawable == null) {
            mBackIcon.setVisibility(View.GONE);
            mBackIcon.setImageDrawable(null);
        } else {
            mBackIcon.setVisibility(View.VISIBLE);
            mBackIcon.setImageDrawable(drawable);
        }
    }

    /**
     * 设置自定义的Menu区域
     *
     * @param view
     */
    public void setContentMenuView(View view) {
        mContentMenuView = view;
        if (mMenuContainer != null) {
            mMenuContainer.removeAllViews();
            mMenuContainer.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 添加图标样式的MenuItem项
     *
     * @param imageResId
     */
    public void addImageMenuItem(int imageResId, int viewId) {
        if (mMenuContainer == null) {
            return;
        }
        mMenuContainer.addView(genImageMenuItem(imageResId, viewId),
                0, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    /**
     * 添加图标样式的MenuItem项
     *
     * @param imageResId
     */
    public void addImageMenuItem(int imageResId, int viewId, int w, int h) {
        if (mMenuContainer == null) {
            return;
        }
        mMenuContainer.addView(genImageMenuItem(imageResId, viewId, w, h),0);
    }

    /**
     * 添加自定义的View
     *
     * @param view
     * @param viewId
     */
    public void addCustomViewMenuItem(View view, int viewId) {
        if (mMenuContainer == null || view == null) {
            return;
        }
        view.setId(viewId);
        view.setBackgroundResource(mMenuItemResId);
        view.setPadding(mMenuItemPadding, 0, mMenuItemPadding, 0);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemListener != null) {
                    mMenuItemListener.onClick(v);
                }
            }
        });
        mMenuContainer.addView(view, 0, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 添加文字样式的MenuItem项
     *
     * @param text
     */
    public void addTextMenuItem(String text, int viewId) {
        if (mMenuContainer == null) {
            return;
        }
        mMenuContainer.addView(genTextMenuItem(text, viewId),
                0, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 添加文字样式的MenuItem项
     *
     * @param text
     */
    public void addTextMenuItem(String text, int viewId, int colorId) {
        if (mMenuContainer == null) {
            return;
        }
        mMenuContainer.addView(genTextMenuItem(text, viewId, colorId),
                0, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    public ViewGroup getMenuContainer() {
        return mMenuContainer;
    }

    public void addLeftImageMenuItem(int imageResId, int viewId) {
        initLeftMenuContainer();
        mLeftMenuContainer.addView(genImageMenuItem(imageResId, viewId),
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    public void addLeftTextMenuItem(String text, int viewId) {
        initLeftMenuContainer();
        mLeftMenuContainer.addView(genTextMenuItem(text, viewId),
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 添加自定义的View
     *
     * @param view
     * @param viewId
     */
    public void addLeftCustomViewMenuItem(View view, int viewId) {
        if (view == null) {
            return;
        }
        initLeftMenuContainer();
        view.setId(viewId);
        view.setBackgroundResource(mMenuItemResId);
        view.setPadding(mMenuItemPadding, 0, mMenuItemPadding, 0);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemListener != null) {
                    mMenuItemListener.onClick(v);
                }
            }
        });
        mLeftMenuContainer.addView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    private TextView genTextMenuItem(String text, int viewId) {
        TextView textView = new TextView(getContext());
        textView.setId(viewId);
        textView.setText(text);
        textView.setBackgroundResource(mMenuItemResId);
        textView.setPadding(mMenuItemPadding, 0, mMenuItemPadding, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemListener != null) {
                    mMenuItemListener.onClick(v);
                }
            }
        });
        return textView;
    }

    private TextView genTextMenuItem(String text, int viewId, int colorId) {
        TextView textView = new TextView(getContext());
        textView.setId(viewId);
        textView.setText(text);
        textView.setBackgroundResource(mMenuItemResId);
        textView.setPadding(mMenuItemPadding, 0, mMenuItemPadding, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(colorId));
        textView.setTextSize(15);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemListener != null) {
                    mMenuItemListener.onClick(v);
                }
            }
        });
        return textView;
    }

    private ImageView genImageMenuItem(int imageResId, int viewId) {
        ImageView imageView = new ImageView(getContext());
        imageView.setId(viewId);
        imageView.setImageResource(imageResId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setBackgroundResource(mMenuItemResId);
        imageView.setPadding(mMenuItemPadding, 0, mMenuItemPadding, 0);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemListener != null) {
                    mMenuItemListener.onClick(v);
                }
            }
        });
        return imageView;
    }

    private ImageView genImageMenuItem(int imageResId, int viewId, int w, int h) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(layoutParams);
        imageView.setId(viewId);
        imageView.setImageResource(imageResId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mMenuItemResId);
        imageView.setPadding(mMenuItemPadding, mMenuItemPadding, mMenuItemPadding, mMenuItemPadding);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuItemListener != null) {
                    mMenuItemListener.onClick(v);
                }
            }
        });
        return imageView;
    }

    private void initLeftMenuContainer() {
        if (mLeftMenuContainer == null) {
            mLeftMenuContainer = new LinearLayout(getContext());
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.addRule(RIGHT_OF, R.id.back_icon);
            lp.addRule(LEFT_OF, R.id.title);
            mLeftMenuContainer.setGravity(Gravity.CENTER_VERTICAL);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int leftPadding = (int) (metrics.density * 3 + 0.5);
            mLeftMenuContainer.setPadding(leftPadding, 0, 0, 0);
            addView(mLeftMenuContainer, lp);
        }
    }

    public void removeMenuItem(int index) {
        mMenuContainer.removeViewAt(index);
    }
}
