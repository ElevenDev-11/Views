package cn.elevendev.refreshlayout.inherit.wrapper;

import android.annotation.SuppressLint;
import android.view.View;

import cn.elevendev.refreshlayout.inherit.api.RefreshHeader;
import cn.elevendev.refreshlayout.inherit.simple.SimpleComponent;

/**
 * 刷新头部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends SimpleComponent implements RefreshHeader {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
