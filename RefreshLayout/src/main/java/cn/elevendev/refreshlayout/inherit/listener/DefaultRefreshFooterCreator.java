package cn.elevendev.refreshlayout.inherit.listener;

import android.content.Context;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.inherit.api.RefreshFooter;
import cn.elevendev.refreshlayout.inherit.api.RefreshLayout;

/**
 * 默认Footer创建器
 */
public interface DefaultRefreshFooterCreator {
    @NonNull
    RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout);
}
