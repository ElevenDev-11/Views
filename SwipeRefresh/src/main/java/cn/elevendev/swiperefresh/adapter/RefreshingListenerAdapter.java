package cn.elevendev.swiperefresh.adapter;

import cn.elevendev.swiperefresh.SwipeRefreshLayout;

public abstract class RefreshingListenerAdapter implements SwipeRefreshLayout.OnRefreshListener {
    @Override
    public void onRefreshing() {}

    @Override
    public void onLoadingMore() {}
}
