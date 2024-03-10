package cn.elevendev.swiperefresh;

public interface ILifecycleObserver {
    void onAttached(SwipeRefreshLayout layout);

    void onDetached(SwipeRefreshLayout layout);
}
