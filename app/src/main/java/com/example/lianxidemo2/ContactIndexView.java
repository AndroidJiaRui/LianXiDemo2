package com.example.lianxidemo2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @Created by xww.
 * @Creation time 2018/8/18.
 * @Email 767412271@qq.com
 * @Blog https://blog.csdn.net/smile_running
 */

public class ContactIndexView extends View {
    //控件实际的宽高
    private int mRealWidth;
    private int mRealHeight;
    //画布的宽高
    private int mWidth;
    private int mHeight;
    //每一个字母所占高度
    private int mEachHeight;
    //点击区域的下标
    private int mTouchIndex = 0;
    //画笔
    private Paint mPaint;
    private Rect mRect;
    private onShowLetterListener onShowLetterListener = null;

    private int colorBackground;
    private int colorNormal;
    private int colorChecked;

    public interface onShowLetterListener {
        void showLatter(String letter);
    }

    public void setOnShowLetter(onShowLetterListener showLetterListener) {
        this.onShowLetterListener = showLetterListener;
    }

    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @TargetApi(Build.VERSION_CODES.M)
    private void init() {
        EventBus.getDefault().register(this);
        colorBackground = ContextCompat.getColor(MyApp.getContext(),R.color.colorPrimaryLight);
        //colorNormal = getContext().getResources().getColor(R.color.colorWhite, null);
        colorNormal=ContextCompat.getColor(MyApp.getContext(),R.color.colorWhite);
       // colorChecked = getContext().getResources().getColor(R.color.colorPrimaryDark, null);
        colorChecked=ContextCompat.getColor(MyApp.getContext(),R.color.colorPrimaryDark);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(38f);
    }


    public ContactIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mRealWidth = widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mRealHeight = heightSize;
                break;
        }
        setMeasuredDimension(mRealWidth, mRealHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(colorBackground);
        //每一个存放字母的宽，也就是画布的宽
        mWidth = canvas.getWidth();
        // 画布高度 - 状态栏高度。否则字母A将显示到状态栏里
        mHeight = canvas.getHeight() - getStatusbarHeight();
        //每一个存放字母的高
        mEachHeight = mHeight / letters.length;

        //绘制26个字母
        for (int i = 0; i < letters.length; i++) {
            final String _latter = letters[i];
            //获得每一个字母所占的宽、高
            mPaint.getTextBounds(_latter, 0, 1, mRect);
            final int letterWidth = mRect.width();
            final int letterHeight = mRect.height();

            //绘制点击高亮的字母
            if (mTouchIndex == i) {
                mPaint.setColor(colorChecked);
            } else {
                mPaint.setColor(colorNormal);
            }
            canvas.drawText(_latter, mWidth / 2 - letterWidth / 2, (i + 1) * mEachHeight - letterHeight / 2, mPaint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                refreshLetterIndex(y);
                break;
            case MotionEvent.ACTION_MOVE:
                refreshLetterIndex(y);
                break;
        }
        return true;
    }


    @Subscribe
    public void onListScrollEvent(ScrollEvent event) {
        //如果到达ListView底部，letter的变化交给点击事件
        if (event.isLast()) {
            return;
        }
        for (int i = 0; i < letters.length; i++) {
            if (event.getLetter().equals(letters[i])) {
                mTouchIndex = i;
                invalidate();
                return;
            }
        }
    }

    /**
     * 刷新被选中的字母的下标，得到下标交给onDraw()
     */
    private void refreshLetterIndex(int y) {
        //y坐标 / 每个字母高度 = 当前字母下标
        int index = y / mEachHeight;
        if (index != mTouchIndex) {
            mTouchIndex = index;
            //回调选中的字母
            if (onShowLetterListener != null) {
                onShowLetterListener.showLatter(letters[mTouchIndex]);
            }
            invalidate();
        }
    }

    /**
     * 获取状态栏高度
     */
    private int getStatusbarHeight() {
        int resId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resId > 0 ? getContext().getResources().getDimensionPixelSize(resId) : 0;
    }
}
