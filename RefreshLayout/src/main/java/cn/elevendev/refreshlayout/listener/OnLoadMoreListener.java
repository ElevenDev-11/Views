package cn.elevendev.refreshlayout.listener;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.api.RefreshLayout;

/**
 * 加载更多监听器
 */
public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
