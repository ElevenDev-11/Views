package cn.elevendev.refreshlayout.inherit.wrapper;

import android.annotation.SuppressLint;
import android.view.View;

import cn.elevendev.refreshlayout.inherit.api.RefreshFooter;
import cn.elevendev.refreshlayout.inherit.simple.SimpleComponent;

/**
 * 刷新底部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshFooterWrapper extends SimpleComponent implements RefreshFooter {

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

}
