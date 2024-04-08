package cn.elevendev.refreshlayout.api;

import androidx.annotation.NonNull;

import cn.elevendev.refreshlayout.inherit.api.RefreshComponent;
import cn.elevendev.refreshlayout.inherit.constant.SpinnerStyle;

/**
 * 刷新内部组件
 */
public interface RefreshInternal extends RefreshComponent {

    /**
     * 获取变换方式 {@link SpinnerStyle} 必须返回 非空
     * @return 变换方式
     */
    @NonNull
    SpinnerStyle getSpinnerStyle();
}
