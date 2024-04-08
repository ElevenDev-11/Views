package cn.elevendev.refreshlayout.header;

import android.content.Context;
import android.util.AttributeSet;

import cn.elevendev.refreshlayout.api.RefreshHeader;

/**
 * 贝塞尔曲线类雷达风格刷新组件
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class BezierRadarHeader extends cn.elevendev.refreshlayout.inherit.header.BezierRadarHeader implements RefreshHeader {

    //<editor-fold desc="FrameLayout">
    public BezierRadarHeader(Context context) {
        this(context,null);
    }

    public BezierRadarHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //</editor-fold>

    @Override
    public BezierRadarHeader setPrimaryColor(int color) {
        super.setPrimaryColor(color);
        return this;
    }

    @Override
    public BezierRadarHeader setAccentColor(int color) {
        super.setAccentColor(color);
        return this;
    }

    @Override
    public BezierRadarHeader setPrimaryColorId(int colorId) {
        super.setPrimaryColorId(colorId);
        return this;
    }

    @Override
    public BezierRadarHeader setAccentColorId(int colorId) {
        super.setAccentColorId(colorId);
        return this;
    }

    @Override
    public BezierRadarHeader setEnableHorizontalDrag(boolean enable) {
        super.setEnableHorizontalDrag(enable);
        return this;
    }
}
