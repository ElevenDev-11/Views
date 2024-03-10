package cn.elevendev.swiperefresh.extra.footer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.StringRes;

import cn.elevendev.swiperefresh.AbsClassicRefreshView;
import cn.elevendev.swiperefresh.ClassicConfig;
import cn.elevendev.swiperefresh.R;
import cn.elevendev.swiperefresh.SwipeRefreshLayout;
import cn.elevendev.swiperefresh.indicator.IIndicator;

/** @author dkzwm */
public class ClassicFooter<T extends IIndicator> extends AbsClassicRefreshView<T> {
    private boolean mNoMoreDataChangedView = false;
    @StringRes private int mPullUpToLoadRes = R.string.sr_pull_up_to_load;
    @StringRes private int mPullUpRes = R.string.sr_pull_up;
    @StringRes private int mLoadingRes = R.string.sr_loading;
    @StringRes private int mLoadSuccessfulRes = R.string.sr_load_complete;
    @StringRes private int mLoadFailRes = R.string.sr_load_failed;
    @StringRes private int mReleaseToLoadRes = R.string.sr_release_to_load;
    @StringRes private int mNoMoreDataRes = R.string.sr_no_more_data;
    private OnClickListener mNoMoreDataClickListener;

    public ClassicFooter(Context context) {
        this(context, null);
    }

    public ClassicFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Bitmap bitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.classic_arrow_icon);
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        Bitmap dstBitmap =
                Bitmap.createBitmap(
                        bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (!bitmap.isRecycled()) bitmap.recycle();
        mArrowImageView.setImageBitmap(dstBitmap);
    }

    public void setPullUpToLoadRes(@StringRes int pullUpToLoadRes) {
        mPullUpToLoadRes = pullUpToLoadRes;
    }

    public void setPullUpRes(@StringRes int pullUpRes) {
        mPullUpRes = pullUpRes;
    }

    public void setLoadingRes(@StringRes int loadingRes) {
        mLoadingRes = loadingRes;
    }

    public void setLoadSuccessfulRes(@StringRes int loadSuccessfulRes) {
        mLoadSuccessfulRes = loadSuccessfulRes;
    }

    public void setLoadFailRes(@StringRes int loadFailRes) {
        mLoadFailRes = loadFailRes;
    }

    public void setReleaseToLoadRes(@StringRes int releaseToLoadRes) {
        mReleaseToLoadRes = releaseToLoadRes;
    }

    public void setNoMoreDataRes(int noMoreDataRes) {
        mNoMoreDataRes = noMoreDataRes;
    }

    public void setNoMoreDataClickListener(OnClickListener onClickListener) {
        mNoMoreDataClickListener = onClickListener;
    }

    @Override
    public int getType() {
        return TYPE_FOOTER;
    }

    @Override
    public void onReset(SwipeRefreshLayout frame) {
        super.onReset(frame);
        mNoMoreDataChangedView = false;
        mTitleTextView.setOnClickListener(null);
    }

    @Override
    public void onRefreshPrepare(SwipeRefreshLayout frame) {
        mArrowImageView.clearAnimation();
        mShouldShowLastUpdate = true;
        mNoMoreDataChangedView = false;
        tryUpdateLastUpdateTime();
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTimeUpdater.start();
        }
        mProgressBar.setVisibility(INVISIBLE);
        mArrowImageView.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setOnClickListener(null);
        if (frame.isEnabledPullToRefresh() && !frame.isDisabledPerformLoadMore()) {
            mTitleTextView.setText(mPullUpToLoadRes);
        } else {
            mTitleTextView.setText(mPullUpRes);
        }
        requestLayout();
    }

    @Override
    public void onRefreshBegin(SwipeRefreshLayout frame, T indicator) {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(INVISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(mLoadingRes);
        tryUpdateLastUpdateTime();
    }

    @Override
    public void onRefreshComplete(SwipeRefreshLayout frame, boolean isSuccessful) {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(INVISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        final boolean noMoreData = frame.isEnabledNoMoreData();
        if (frame.isRefreshSuccessful()) {
            mTitleTextView.setText(noMoreData ? mNoMoreDataRes : mLoadSuccessfulRes);
            mLastUpdateTime = System.currentTimeMillis();
            ClassicConfig.updateTime(getContext(), mLastUpdateTimeKey, mLastUpdateTime);
        } else {
            mTitleTextView.setText(noMoreData ? mNoMoreDataRes : mLoadFailRes);
        }
        mLastUpdateTimeUpdater.stop();
        mLastUpdateTextView.setVisibility(GONE);
        if (noMoreData) {
            mTitleTextView.setOnClickListener(mNoMoreDataClickListener);
        }
    }

    @Override
    public void onRefreshPositionChanged(SwipeRefreshLayout frame, byte status, T indicator) {
        final int offsetToLoadMore = indicator.getOffsetToLoadMore();
        final int currentPos = indicator.getCurrentPos();
        final int lastPos = indicator.getLastPos();
        if (frame.isEnabledNoMoreData()) {
            if (currentPos > lastPos && !mNoMoreDataChangedView) {
                mTitleTextView.setVisibility(VISIBLE);
                mLastUpdateTextView.setVisibility(GONE);
                mProgressBar.setVisibility(INVISIBLE);
                mLastUpdateTimeUpdater.stop();
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(GONE);
                mTitleTextView.setText(mNoMoreDataRes);
                mTitleTextView.setOnClickListener(mNoMoreDataClickListener);
                mNoMoreDataChangedView = true;
            }
            return;
        }
        mNoMoreDataChangedView = false;
        if (currentPos < offsetToLoadMore && lastPos >= offsetToLoadMore) {
            if (indicator.hasTouched() && status == SwipeRefreshLayout.SR_STATUS_PREPARE) {
                mTitleTextView.setVisibility(VISIBLE);
                if (frame.isEnabledPullToRefresh() && !frame.isDisabledPerformLoadMore()) {
                    mTitleTextView.setText(mPullUpToLoadRes);
                } else {
                    mTitleTextView.setText(mPullUpRes);
                }
                mArrowImageView.setVisibility(VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(mReverseFlipAnimation);
            }
        } else if (currentPos > offsetToLoadMore && lastPos <= offsetToLoadMore) {
            if (indicator.hasTouched() && status == SwipeRefreshLayout.SR_STATUS_PREPARE) {
                mTitleTextView.setVisibility(VISIBLE);
                if (!frame.isEnabledPullToRefresh() && !frame.isDisabledPerformLoadMore()) {
                    mTitleTextView.setText(mReleaseToLoadRes);
                }
                mArrowImageView.setVisibility(VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(mFlipAnimation);
            }
        }
    }
}
