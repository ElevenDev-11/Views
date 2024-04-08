package cn.elevendev.refreshlayout.inherit.listener;

import android.content.Context;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.inherit.api.RefreshLayout;

/**
 * 默认全局初始化器
 */
public interface DefaultRefreshInitializer {
    void initialize(@NonNull Context context, @NonNull RefreshLayout layout);
}
