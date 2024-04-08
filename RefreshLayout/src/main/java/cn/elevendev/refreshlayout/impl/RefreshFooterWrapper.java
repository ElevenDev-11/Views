package cn.elevendev.refreshlayout.impl;

import android.annotation.SuppressLint;
import android.view.View;

import cn.elevendev.refreshlayout.inherit.api.RefreshFooter;
import cn.elevendev.refreshlayout.internal.InternalAbstract;

/**
 * 刷新底部包装
 * Created by scwang on 2017/5/26.
 */
@SuppressLint("ViewConstructor")
public class RefreshFooterWrapper extends InternalAbstract implements RefreshFooter {

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

}
