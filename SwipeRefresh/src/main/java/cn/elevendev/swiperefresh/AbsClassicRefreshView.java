package cn.elevendev.swiperefresh;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import cn.elevendev.swiperefresh.extra.IRefreshView;
import cn.elevendev.swiperefresh.indicator.IIndicator;
import cn.elevendev.swiperefresh.utils.PixelUtl;

public abstract class AbsClassicRefreshView<T extends IIndicator> extends RelativeLayout
        implements IRefreshView<T>, LastUpdateTimeUpdater.ITimeUpdater {
    private static final Interpolator sLinearInterpolator = new LinearInterpolator();
    protected int mDefaultHeightInDP = 64;
    protected RotateAnimation mFlipAnimation;
    protected RotateAnimation mReverseFlipAnimation;
    protected TextView mTitleTextView;
    protected TextView mLastUpdateTextView;
    protected ImageView mArrowImageView;
    protected View mProgressBar;
    protected String mLastUpdateTimeKey;
    protected boolean mShouldShowLastUpdate;
    protected long mLastUpdateTime = -1;
    protected int mRotateAniTime = 200;
    protected LastUpdateTimeUpdater mLastUpdateTimeUpdater;
    protected cn.elevendev.swiperefresh.extra.RefreshViewStyle mStyle;

    public AbsClassicRefreshView(Context context) {
        this(context, null);
    }

    public AbsClassicRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsClassicRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStyle = new cn.elevendev.swiperefresh.extra.RefreshViewStyle(context, attrs, defStyleAttr, 0);
        mFlipAnimation =
                new RotateAnimation(
                        0,
                        -180,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f);
        mFlipAnimation.setInterpolator(sLinearInterpolator);
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation =
                new RotateAnimation(
                        -180,
                        0,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f);
        mReverseFlipAnimation.setInterpolator(sLinearInterpolator);
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
        createClassicViews();
        mArrowImageView = findViewById(R.id.sr_classic_arrow);
        mTitleTextView = findViewById(R.id.sr_classic_title);
        mLastUpdateTextView = findViewById(R.id.sr_classic_last_update);
        mProgressBar = findViewById(R.id.sr_classic_progress);
        mLastUpdateTimeUpdater = new LastUpdateTimeUpdater(this);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
    }

    protected void createClassicViews() {
        ClassicConfig.createClassicViews(this);
    }

    public void tryUpdateLastUpdateTime() {
        if (canUpdate()) {
            updateTime(this);
        }
    }

    public TextView getLastUpdateTextView() {
        return mLastUpdateTextView;
    }

    @Override
    public void updateTime(AbsClassicRefreshView refreshView) {
        String time =
                ClassicConfig.getLastUpdateTime(getContext(), mLastUpdateTime, mLastUpdateTimeKey);
        if (TextUtils.isEmpty(time)) {
            mLastUpdateTextView.setVisibility(GONE);
        } else {
            mLastUpdateTextView.setVisibility(VISIBLE);
            mLastUpdateTextView.setText(time);
        }
    }

    @Override
    public boolean canUpdate() {
        return !(TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLastUpdateTimeUpdater.stop();
        mFlipAnimation.cancel();
        mReverseFlipAnimation.cancel();
    }

    @Override
    public int getStyle() {
        return mStyle.mStyle;
    }

    public void setStyle(@RefreshViewStyle int style) {
        if (mStyle.mStyle != style) {
            mStyle.mStyle = style;
            requestLayout();
        }
    }

    public void setTimeUpdater(@NonNull LastUpdateTimeUpdater.ITimeUpdater timeUpdater) {
        mLastUpdateTimeUpdater.setTimeUpdater(timeUpdater);
    }

    public void setDefaultHeightInDP(@IntRange(from = 0) int defaultHeightInDP) {
        mDefaultHeightInDP = defaultHeightInDP;
    }

    @Override
    public int getCustomHeight() {
        return PixelUtl.dp2px(getContext(), mDefaultHeightInDP);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onReset(SwipeRefreshLayout frame) {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        mShouldShowLastUpdate = true;
        mLastUpdateTimeUpdater.stop();
        tryUpdateLastUpdateTime();
        requestLayout();
    }

    @Override
    public void onFingerUp(SwipeRefreshLayout layout, T indicator) {}

    @Override
    public void onPureScrollPositionChanged(SwipeRefreshLayout layout, byte status, T indicator) {
        if (indicator.hasJustLeftStartPosition()) {
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(INVISIBLE);
            mProgressBar.setVisibility(INVISIBLE);
            mTitleTextView.setVisibility(GONE);
            mArrowImageView.setVisibility(GONE);
            mLastUpdateTextView.setVisibility(GONE);
            mShouldShowLastUpdate = false;
            mLastUpdateTimeUpdater.stop();
            tryUpdateLastUpdateTime();
        }
    }

    public void setRotateAniTime(int time) {
        if (time == mRotateAniTime || time <= 0) {
            return;
        }
        mRotateAniTime = time;
        mFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setDuration(mRotateAniTime);
    }

    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextView.setTextColor(color);
    }

    public void setLastUpdateTextColor(@ColorInt int color) {
        mLastUpdateTextView.setTextColor(color);
    }
}
