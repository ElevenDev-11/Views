package cn.elevendev.refreshlayout.impl;

import android.annotation.SuppressLint;
import android.view.View;

import cn.elevendev.refreshlayout.api.RefreshHeader;
import cn.elevendev.refreshlayout.internal.InternalAbstract;

/**
 * 刷新头部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
