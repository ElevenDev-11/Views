package cn.elevendev.refreshlayout.listener;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.api.RefreshLayout;

/**
 * 刷新监听器
 */
public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
