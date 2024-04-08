package cn.elevendev.refreshlayout.inherit.listener;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.inherit.api.RefreshLayout;

/**
 * 加载更多监听器
 */
public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
