package cn.elevendev.swiperefresh;

import cn.elevendev.swiperefresh.extra.IRefreshView;
import cn.elevendev.swiperefresh.indicator.IIndicator;

public interface IRefreshViewCreator {
    IRefreshView<IIndicator> createHeader(SwipeRefreshLayout layout);

    IRefreshView<IIndicator> createFooter(SwipeRefreshLayout layout);
}
