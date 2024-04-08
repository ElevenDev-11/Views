package cn.elevendev.refreshlayout.inherit.listener;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.inherit.api.RefreshLayout;

/**
 * 刷新监听器
 */
public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
