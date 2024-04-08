package cn.elevendev.refreshlayout.footer;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import cn.elevendev.refreshlayout.api.RefreshFooter;
import cn.elevendev.refreshlayout.constant.SpinnerStyle;


/**
 * 球脉冲底部加载组件
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BallPulseFooter extends cn.elevendev.refreshlayout.inherit.footer.BallPulseFooter implements RefreshFooter {

    //<editor-fold desc="构造方法">
    public BallPulseFooter(Context context) {
        this(context, null);
    }

    public BallPulseFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //</editor-fold>


    @Override
    public BallPulseFooter setSpinnerStyle(SpinnerStyle mSpinnerStyle) {
        super.setSpinnerStyle(mSpinnerStyle);
        return this;
    }

    @Override
    public BallPulseFooter setNormalColor(int color) {
        super.setNormalColor(color);
        return this;
    }

    @Override
    public BallPulseFooter setAnimatingColor(int color) {
        super.setAnimatingColor(color);
        return this;
    }
}
