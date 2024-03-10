package cn.elevendev.swiperefresh.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.elevendev.swiperefresh.SwipeRefreshLayout;

@IntDef({SwipeRefreshLayout.LayoutManager.HORIZONTAL, SwipeRefreshLayout.LayoutManager.VERTICAL})
@Retention(RetentionPolicy.SOURCE)
public @interface Orientation {}
