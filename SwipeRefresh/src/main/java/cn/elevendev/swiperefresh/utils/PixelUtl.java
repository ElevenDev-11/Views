package cn.elevendev.swiperefresh.utils;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class PixelUtl {
    public static int dp2px(@NonNull Context context, float offSet) {
        return Math.round(
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        offSet,
                        context.getResources().getDisplayMetrics()));
    }
}
