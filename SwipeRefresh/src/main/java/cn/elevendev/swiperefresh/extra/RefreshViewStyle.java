package cn.elevendev.swiperefresh.extra;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cn.elevendev.swiperefresh.R;

public class RefreshViewStyle {
    @IRefreshView.RefreshViewStyle public int mStyle = IRefreshView.STYLE_DEFAULT;

    public RefreshViewStyle(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            final TypedArray arr =
                    context.obtainStyledAttributes(
                            attrs, R.styleable.IRefreshView, defStyleAttr, defStyleRes);
            @IRefreshView.RefreshViewStyle
            int style = arr.getInt(R.styleable.IRefreshView_sr_style, mStyle);
            mStyle = style;
            arr.recycle();
        }
    }
}
