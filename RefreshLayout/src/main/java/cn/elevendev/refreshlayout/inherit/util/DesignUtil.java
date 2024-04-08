package cn.elevendev.refreshlayout.inherit.util;

import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

import cn.elevendev.refreshlayout.inherit.api.RefreshKernel;
import cn.elevendev.refreshlayout.inherit.listener.CoordinatorLayoutListener;

public class DesignUtil {

    public static void checkCoordinatorLayout(View content, RefreshKernel kernel, final CoordinatorLayoutListener listener) {
        try {//try 不能删除，不然会出现兼容性问题
            if (content instanceof CoordinatorLayout) {
                kernel.getRefreshLayout().setEnableNestedScroll(false);
                ViewGroup layout = (ViewGroup) content;
                for (int i = layout.getChildCount() - 1; i >= 0; i--) {
                    View view = layout.getChildAt(i);
                    if (view instanceof AppBarLayout) {
                        ((AppBarLayout) view).addOnOffsetChangedListener((appBarLayout, verticalOffset) -> listener.onCoordinatorUpdate(
                                verticalOffset >= 0,
                                (appBarLayout.getTotalScrollRange() + verticalOffset) <= 0));
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
