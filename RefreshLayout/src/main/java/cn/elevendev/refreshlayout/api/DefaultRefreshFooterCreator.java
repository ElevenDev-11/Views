package cn.elevendev.refreshlayout.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 默认Footer创建器
 */
public interface DefaultRefreshFooterCreator {
    @NonNull
    RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout);
}
