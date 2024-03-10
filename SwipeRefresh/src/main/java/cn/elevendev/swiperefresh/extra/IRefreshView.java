package cn.elevendev.swiperefresh.extra;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.elevendev.swiperefresh.SwipeRefreshLayout;
import cn.elevendev.swiperefresh.indicator.IIndicator;

/** @author dkzwm */
public interface IRefreshView<T extends IIndicator> {
    byte TYPE_HEADER = 0;
    byte TYPE_FOOTER = 1;

    byte STYLE_DEFAULT = 0;
    byte STYLE_SCALE = 1;
    byte STYLE_PIN = 2;
    byte STYLE_FOLLOW_SCALE = 3;
    byte STYLE_FOLLOW_PIN = 4;
    byte STYLE_FOLLOW_CENTER = 5;

    @RefreshViewType
    int getType();

    @RefreshViewStyle
    int getStyle();

    int getCustomHeight();

    @NonNull
    View getView();

    void onFingerUp(SwipeRefreshLayout layout, T indicator);

    void onReset(SwipeRefreshLayout layout);

    void onRefreshPrepare(SwipeRefreshLayout layout);

    void onRefreshBegin(SwipeRefreshLayout layout, T indicator);

    void onRefreshComplete(SwipeRefreshLayout layout, boolean isSuccessful);

    void onRefreshPositionChanged(SwipeRefreshLayout layout, byte status, T indicator);


    void onPureScrollPositionChanged(SwipeRefreshLayout layout, byte status, T indicator);

    @IntDef({TYPE_HEADER, TYPE_FOOTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshViewType {}

    @IntDef({
            STYLE_DEFAULT,
            STYLE_SCALE,
            STYLE_PIN,
            STYLE_FOLLOW_SCALE,
            STYLE_FOLLOW_PIN,
            STYLE_FOLLOW_CENTER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshViewStyle {}
}
