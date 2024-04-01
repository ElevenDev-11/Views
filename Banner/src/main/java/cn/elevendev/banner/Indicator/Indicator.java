package cn.elevendev.banner.Indicator;

import android.view.View;

import androidx.annotation.NonNull;

import cn.elevendev.banner.Config.IndicatorConfig;
import cn.elevendev.banner.Listener.OnPageChangeListener;

public interface Indicator extends OnPageChangeListener {
    @NonNull
    View getIndicatorView();

    IndicatorConfig getIndicatorConfig();

    void onPageChanged(int count, int currentPosition);

}
