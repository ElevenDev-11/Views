package cn.elevendev.swiperefresh.utils;

import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;

import cn.elevendev.swiperefresh.SwipeRefreshLayout;
import cn.elevendev.swiperefresh.extra.IRefreshView;

/** @author dkzwm */
public class AppBarLayoutUtil
        implements SwipeRefreshLayout.OnHeaderEdgeDetectCallBack,
        SwipeRefreshLayout.OnFooterEdgeDetectCallBack {
    private AppBarLayout mAppBarLayout;
    private boolean mFullyExpanded;
    private boolean mFullyCollapsed;
    private AppBarLayout.OnOffsetChangedListener mListener =
            new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    mFullyExpanded = verticalOffset >= 0;
                    mFullyCollapsed = appBarLayout.getTotalScrollRange() + verticalOffset <= 0;
                }
            };

    public AppBarLayoutUtil(View view) {
        if (view instanceof AppBarLayout) {
            mAppBarLayout = (AppBarLayout) view;
            mAppBarLayout.addOnOffsetChangedListener(mListener);
        }
    }

    public void detach() {
        if (mAppBarLayout != null) {
            mAppBarLayout.removeOnOffsetChangedListener(mListener);
            mAppBarLayout = null;
        }
    }

    @Override
    public boolean isNotYetInEdgeCannotMoveHeader(
            SwipeRefreshLayout parent, @Nullable View child, @Nullable IRefreshView header) {
        if (child == null) {
            if (parent.isVerticalOrientation()) {
                return !mFullyExpanded;
            } else {
                return true;
            }
        } else {
            if (parent.isVerticalOrientation()) {
                return !mFullyExpanded || child.canScrollVertically(-1);
            } else {
                return child.canScrollHorizontally(-1);
            }
        }
    }

    @Override
    public boolean isNotYetInEdgeCannotMoveFooter(
            SwipeRefreshLayout parent, @Nullable View child, @Nullable IRefreshView footer) {
        if (child == null) {
            if (parent.isVerticalOrientation()) {
                return !mFullyCollapsed;
            } else {
                return true;
            }
        } else {
            if (parent.isVerticalOrientation()) {
                return !mFullyCollapsed || child.canScrollVertically(1);
            } else {
                return child.canScrollHorizontally(1);
            }
        }
    }
}
