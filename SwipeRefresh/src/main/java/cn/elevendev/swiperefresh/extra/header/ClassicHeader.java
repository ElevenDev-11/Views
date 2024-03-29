package cn.elevendev.swiperefresh.extra.header;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.StringRes;

import cn.elevendev.swiperefresh.AbsClassicRefreshView;
import cn.elevendev.swiperefresh.ClassicConfig;
import cn.elevendev.swiperefresh.R;
import cn.elevendev.swiperefresh.SwipeRefreshLayout;
import cn.elevendev.swiperefresh.indicator.IIndicator;

/** @author dkzwm */
public class ClassicHeader<T extends IIndicator> extends AbsClassicRefreshView<T> {
    @StringRes private int mPullDownToRefreshRes = R.string.sr_pull_down_to_refresh;
    @StringRes private int mPullDownRes = R.string.sr_pull_down;
    @StringRes private int mRefreshingRes = R.string.sr_refreshing;
    @StringRes private int mRefreshSuccessfulRes = R.string.sr_refresh_complete;
    @StringRes private int mRefreshFailRes = R.string.sr_refresh_failed;
    @StringRes private int mReleaseToRefreshRes = R.string.sr_release_to_refresh;

    public ClassicHeader(Context context) {
        this(context, null);
    }

    public ClassicHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mArrowImageView.setImageResource(R.drawable.classic_arrow_icon);
    }

    public void setPullDownToRefreshRes(@StringRes int pullDownToRefreshRes) {
        mPullDownToRefreshRes = pullDownToRefreshRes;
    }

    public void setPullDownRes(@StringRes int pullDownRes) {
        mPullDownRes = pullDownRes;
    }

    public void setRefreshingRes(@StringRes int refreshingRes) {
        mRefreshingRes = refreshingRes;
    }

    public void setRefreshSuccessfulRes(@StringRes int refreshSuccessfulRes) {
        mRefreshSuccessfulRes = refreshSuccessfulRes;
    }

    public void setRefreshFailRes(@StringRes int refreshFailRes) {
        mRefreshFailRes = refreshFailRes;
    }

    public void setReleaseToRefreshRes(@StringRes int releaseToRefreshRes) {
        mReleaseToRefreshRes = releaseToRefreshRes;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    @Override
    public void onRefreshPrepare(SwipeRefreshLayout frame) {
        mArrowImageView.clearAnimation();
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTimeUpdater.start();
        }
        mProgressBar.setVisibility(INVISIBLE);
        mArrowImageView.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isEnabledPullToRefresh()) {
            mTitleTextView.setText(mPullDownToRefreshRes);
        } else {
            mTitleTextView.setText(mPullDownRes);
        }
        requestLayout();
    }

    @Override
    public void onRefreshBegin(SwipeRefreshLayout frame, T indicator) {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(INVISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(mRefreshingRes);
        tryUpdateLastUpdateTime();
    }

    @Override
    public void onRefreshComplete(SwipeRefreshLayout frame, boolean isSuccessful) {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(INVISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isRefreshSuccessful()) {
            mTitleTextView.setText(mRefreshSuccessfulRes);
            mLastUpdateTime = System.currentTimeMillis();
            ClassicConfig.updateTime(getContext(), mLastUpdateTimeKey, mLastUpdateTime);
        } else {
            mTitleTextView.setText(mRefreshFailRes);
        }
        mLastUpdateTimeUpdater.stop();
        mLastUpdateTextView.setVisibility(GONE);
    }

    @Override
    public void onRefreshPositionChanged(SwipeRefreshLayout frame, byte status, T indicator) {
        final int offsetToRefresh = indicator.getOffsetToRefresh();
        final int currentPos = indicator.getCurrentPos();
        final int lastPos = indicator.getLastPos();

        if (currentPos < offsetToRefresh && lastPos >= offsetToRefresh) {
            if (indicator.hasTouched() && status == SwipeRefreshLayout.SR_STATUS_PREPARE) {
                mTitleTextView.setVisibility(VISIBLE);
                if (frame.isEnabledPullToRefresh()) {
                    mTitleTextView.setText(mPullDownToRefreshRes);
                } else {
                    mTitleTextView.setText(mPullDownRes);
                }
                mArrowImageView.setVisibility(VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(mReverseFlipAnimation);
            }
        } else if (currentPos > offsetToRefresh && lastPos <= offsetToRefresh) {
            if (indicator.hasTouched() && status == SwipeRefreshLayout.SR_STATUS_PREPARE) {
                mTitleTextView.setVisibility(VISIBLE);
                if (!frame.isEnabledPullToRefresh()) {
                    mTitleTextView.setText(mReleaseToRefreshRes);
                }
                mArrowImageView.setVisibility(VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(mFlipAnimation);
            }
        }
    }
}
