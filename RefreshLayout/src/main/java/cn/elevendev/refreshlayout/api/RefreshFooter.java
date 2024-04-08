package cn.elevendev.refreshlayout.api;

import cn.elevendev.refreshlayout.constant.SpinnerStyle;
import cn.elevendev.refreshlayout.footer.BallPulseFooter;

/**
 * 刷新底部
 */
public interface RefreshFooter extends cn.elevendev.refreshlayout.inherit.api.RefreshFooter {

    BallPulseFooter setSpinnerStyle(SpinnerStyle mSpinnerStyle);
}