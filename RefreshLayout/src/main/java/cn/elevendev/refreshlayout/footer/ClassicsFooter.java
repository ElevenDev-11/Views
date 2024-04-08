package cn.elevendev.refreshlayout.footer;

import android.content.Context;
import android.util.AttributeSet;

import cn.elevendev.refreshlayout.constant.SpinnerStyle;
import cn.elevendev.refreshlayout.inherit.api.RefreshFooter;


/**
 * 经典上拉底部-兼容【1.x】版本-包名位置
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ClassicsFooter extends cn.elevendev.refreshlayout.inherit.footer.ClassicsFooter implements RefreshFooter {

    public ClassicsFooter(Context context) {
        super(context);
    }

    public ClassicsFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ClassicsFooter setSpinnerStyle(SpinnerStyle style) {
        super.setSpinnerStyle(style);
        return this;
    }

    @Override
    public ClassicsFooter setPrimaryColor(int primaryColor) {
        super.setPrimaryColor(primaryColor);
        return this;
    }

    @Override
    public ClassicsFooter setAccentColor(int accentColor) {
        super.setAccentColor(accentColor);
        return this;
    }

    @Override
    public ClassicsFooter setPrimaryColorId(int colorId) {
        super.setPrimaryColorId(colorId);
        return this;
    }

    @Override
    public ClassicsFooter setAccentColorId(int colorId) {
        super.setAccentColorId(colorId);
        return this;
    }

    @Override
    public ClassicsFooter setFinishDuration(int delay) {
        super.setFinishDuration(delay);
        return this;
    }

    @Override
    public ClassicsFooter setTextSizeTitle(float size) {
        super.setTextSizeTitle(size);
        return this;
    }

    @Override
    public ClassicsFooter setTextSizeTitle(int unit, float size) {
        super.setTextSizeTitle(unit, size);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableMarginRight(float dp) {
        super.setDrawableMarginRight(dp);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableMarginRightPx(int px) {
        super.setDrawableMarginRightPx(px);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableSize(float dp) {
        super.setDrawableSize(dp);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableSizePx(int px) {
        super.setDrawableSizePx(px);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableArrowSize(float dp) {
        super.setDrawableArrowSize(dp);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableArrowSizePx(int px) {
        super.setDrawableArrowSizePx(px);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableProgressSize(float dp) {
        super.setDrawableProgressSize(dp);
        return this;
    }

    @Override
    public ClassicsFooter setDrawableProgressSizePx(int px) {
        super.setDrawableProgressSizePx(px);
        return this;
    }
}
