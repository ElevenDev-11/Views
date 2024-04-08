package cn.elevendev.refreshlayout.inherit.listener;

import android.content.Context;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.inherit.api.RefreshHeader;
import cn.elevendev.refreshlayout.inherit.api.RefreshLayout;

/**
 * 默认Header创建器
 */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout);
}
