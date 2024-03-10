package cn.elevendev.swiperefresh.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import cn.elevendev.swiperefresh.SwipeRefreshLayout;
import cn.elevendev.swiperefresh.extra.IRefreshView;


public class ViewCatcherUtil {
    private static final String CLASS_NAME_OF_VIEWPAGER = "androidx.viewpager.widget.ViewPager";
    private static final String CLASS_NAME_OF_COORDINATORLAYOUT =
            "androidx.coordinatorlayout.widget.CoordinatorLayout";
    private static final String CLASS_NAME_OF_RECYCLERVIEW =
            "androidx.recyclerview.widget.RecyclerView";
    private static final String CLASS_NAME_OF_APPBARLAYOUT =
            "com.google.android.material.appbar.AppBarLayout";
    private static Class<?> sClassOfCoordinatorLayout;
    private static Class<?> sClassOfAppBarLayout;
    private static Class<?> sClassOfViewPager;
    private static Class<?> sClassOfRecyclerView;
    private static boolean sIsCaughtAppBarLayout = false;
    private static boolean sIsCaughtCoordinatorLayout = false;
    private static boolean sIsCaughtViewPager = false;
    private static boolean sIsCaughtRecyclerView = false;

    public static boolean isViewPager(View view) {
        if (sIsCaughtViewPager && sClassOfViewPager == null) return false;
        sIsCaughtViewPager = true;
        if (sClassOfViewPager == null) {
            try {
                sClassOfViewPager = Class.forName(CLASS_NAME_OF_VIEWPAGER);
            } catch (Exception e) {
                return false;
            }
        }
        return sClassOfViewPager.isAssignableFrom(view.getClass());
    }

    public static boolean isRecyclerView(View view) {
        if (sIsCaughtRecyclerView && sClassOfRecyclerView == null) return false;
        sIsCaughtRecyclerView = true;
        if (sClassOfRecyclerView == null) {
            try {
                sClassOfRecyclerView = Class.forName(CLASS_NAME_OF_RECYCLERVIEW);
            } catch (Exception e) {
                return false;
            }
        }
        return sClassOfRecyclerView.isAssignableFrom(view.getClass());
    }

    public static boolean isCoordinatorLayout(View view) {
        if (view == null) {
            return false;
        }
        if (sIsCaughtCoordinatorLayout && sClassOfCoordinatorLayout == null) {
            return false;
        }
        sIsCaughtCoordinatorLayout = true;
        if (sClassOfCoordinatorLayout == null) {
            try {
                sClassOfCoordinatorLayout = Class.forName(CLASS_NAME_OF_COORDINATORLAYOUT);
            } catch (Exception e) {
                return false;
            }
        }
        return sClassOfCoordinatorLayout.isAssignableFrom(view.getClass());
    }

    public static View catchAppBarLayout(final SwipeRefreshLayout group) {
        if ((sIsCaughtCoordinatorLayout || sIsCaughtAppBarLayout)
                && (sClassOfCoordinatorLayout == null || sClassOfAppBarLayout == null)) {
            return null;
        }
        sIsCaughtCoordinatorLayout = true;
        if (sClassOfCoordinatorLayout == null) {
            try {
                sClassOfCoordinatorLayout = Class.forName(CLASS_NAME_OF_COORDINATORLAYOUT);
            } catch (Exception e) {
                return null;
            }
        }
        sIsCaughtAppBarLayout = true;
        if (sClassOfAppBarLayout == null) {
            try {
                sClassOfAppBarLayout = Class.forName(CLASS_NAME_OF_APPBARLAYOUT);
            } catch (Exception e) {
                return null;
            }
        }
        ViewGroup coordinatorLayout = findChildCoordinatorLayout(group);
        if (coordinatorLayout == null) {
            coordinatorLayout = findParentCoordinatorLayout(group);
        }
        if (coordinatorLayout == null) {
            return null;
        }
        final int count = coordinatorLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = coordinatorLayout.getChildAt(i);
            if (sClassOfAppBarLayout.isAssignableFrom(child.getClass())) {
                return child;
            }
        }
        return null;
    }

    private static ViewGroup findChildCoordinatorLayout(ViewGroup group) {
        if (sClassOfCoordinatorLayout.isAssignableFrom(group.getClass())) {
            return group;
        }
        final int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = group.getChildAt(i);
            if (view instanceof ViewGroup) {
                if (ScrollCompat.isScrollingView(view)) {
                    continue;
                }
                if (view instanceof IRefreshView) {
                    continue;
                }
                ViewGroup layout = findChildCoordinatorLayout((ViewGroup) view);
                if (layout != null) {
                    return layout;
                }
            }
        }
        return null;
    }

    private static ViewGroup findParentCoordinatorLayout(ViewGroup group) {
        ViewParent parent = group.getParent();
        while (parent instanceof ViewGroup) {
            group = (ViewGroup) parent;
            if (group.getId() == android.R.id.content) {
                return null;
            }
            if (ScrollCompat.isScrollingView(group)) {
                return null;
            }
            if (sClassOfCoordinatorLayout.isAssignableFrom(group.getClass())) {
                return group;
            }
            parent = group.getParent();
        }
        return null;
    }
}
