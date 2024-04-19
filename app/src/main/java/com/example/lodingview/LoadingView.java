package com.example.lodingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * <pre>
 *     author : Lmojito
 *     e-mail : mrl@lmojito.com
 *     time   : 2023/03/30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoadingView extends View {

    private Paint mPaint;
    private int mColor;
    private int mRadius;
    private int mStrokeWidth;
    private RectF mRectF;
    private int mStartAngle;
    private int mSweepAngle;
    private boolean mIsLoading;
    private boolean mIsGrowing;
    private int mMinSweepAngle;
    private int mMaxSweepAngle;
    private int mIncrement;
    private int mCornerRadius;
    private  Context mContext;

    private ValueAnimator mRotateAnimator;
    private int mCurrentRotation;

    public LoadingView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mColor = Color.BLACK;
        mRadius = 30;
        mStrokeWidth = 6;
        mRectF = new RectF();
        mStartAngle = 0;
        mMinSweepAngle = 30;
        mMaxSweepAngle = 270;
        mIncrement = 5;
        mSweepAngle = mMinSweepAngle;
        mIsLoading = true;
        mIsGrowing = true;
        mCornerRadius = 20;

        mRotateAnimator = ValueAnimator.ofInt(0, 359);
        mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRotateAnimator.setInterpolator(new LinearInterpolator());
        mRotateAnimator.setDuration(1200);
        mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRotation = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mRotateAnimator.start();
        startHandler();
    }

    private void startHandler() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mIsLoading) {
                    updateAnimation();
                    invalidate();
                    handler.postDelayed(this, 10);
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAntiAlias(true);

        mRectF.set(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius, getWidth() / 2 + mRadius, getHeight() / 2 + mRadius);

        canvas.save();
        canvas.rotate(mCurrentRotation, getWidth() / 2, getHeight() / 2);
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);
        canvas.restore();
    }

    private void updateAnimation() {
        if (mIsGrowing) {
            mSweepAngle += mIncrement;
            if (mSweepAngle >= mMaxSweepAngle) {
                mIsGrowing = false;
            }
        } else {
            mSweepAngle -= mIncrement;
            if (mSweepAngle <= mMinSweepAngle) {
                mIsGrowing = true;
            }
        }
    }

    public void startLoading() {
        if (!mIsLoading) {
            reStartLoading();
        }
        mIsLoading = true;
        invalidate();
    }

    public void stopLoading() {
        mIsLoading = false;
        mRotateAnimator.pause();
        invalidate();
    }

    private void reStartLoading() {
        mIsLoading = true;
        startHandler();
        mRotateAnimator.start();
    }

    public void setColor(int id){
        mColor = mContext.getColor(id);
//        invalidate();
    }

    public boolean getIsLoading() {
        return mIsLoading;
    }
}
