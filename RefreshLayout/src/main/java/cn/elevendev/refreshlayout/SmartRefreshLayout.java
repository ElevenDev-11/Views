package cn.elevendev.refreshlayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.elevendev.refreshlayout.api.DefaultRefreshFooterCreator;
import cn.elevendev.refreshlayout.api.DefaultRefreshHeaderCreator;
import cn.elevendev.refreshlayout.api.DefaultRefreshInitializer;
import cn.elevendev.refreshlayout.api.RefreshFooter;
import cn.elevendev.refreshlayout.api.RefreshHeader;
import cn.elevendev.refreshlayout.api.RefreshLayout;
import cn.elevendev.refreshlayout.api.ScrollBoundaryDecider;
import cn.elevendev.refreshlayout.footer.BallPulseFooter;
import cn.elevendev.refreshlayout.header.BezierRadarHeader;
import cn.elevendev.refreshlayout.listener.OnLoadMoreListener;
import cn.elevendev.refreshlayout.listener.OnMultiPurposeListener;
import cn.elevendev.refreshlayout.listener.OnRefreshListener;
import cn.elevendev.refreshlayout.listener.OnRefreshLoadMoreListener;
import cn.elevendev.refreshlayout.listener.SimpleMultiPurposeListener;

/**
 * 智能刷新布局
 * Intelligent RefreshLayout
 */
@SuppressLint("RestrictedApi")
@SuppressWarnings({"unused"})
public class SmartRefreshLayout extends cn.elevendev.refreshlayout.inherit.SmartRefreshLayout implements RefreshLayout {

    //<editor-fold desc="构造方法 construction methods">
    public SmartRefreshLayout(Context context) {
        this(context, null);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //</editor-fold>

    //<editor-fold desc="开放接口 open interface">

    /**
     * Set the header of RefreshLayout.
     * 设置指定的 Header
     * @param header RefreshHeader 刷新头
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setRefreshHeader(@NonNull RefreshHeader header) {
        return setRefreshHeader(header, MATCH_PARENT, WRAP_CONTENT);
    }

    /**
     * Set the header of RefreshLayout.
     * 设置指定的 Header
     * @param header RefreshHeader 刷新头
     * @param width the width in px, can use MATCH_PARENT and WRAP_CONTENT.
     *              宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height the height in px, can use MATCH_PARENT and WRAP_CONTENT.
     *               高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setRefreshHeader(@NonNull RefreshHeader header, int width, int height) {
        super.setRefreshHeader(header, width, height);
        return this;
    }

    /**
     * Set the footer of RefreshLayout.
     * 设置指定的 Footer
     * @param footer RefreshFooter 刷新尾巴
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setRefreshFooter(@NonNull RefreshFooter footer) {
        return setRefreshFooter(footer, MATCH_PARENT, WRAP_CONTENT);
    }

    /**
     * Set the footer of RefreshLayout.
     * 设置指定的 Footer
     * @param footer RefreshFooter 刷新尾巴
     * @param width the width in px, can use MATCH_PARENT and WRAP_CONTENT.
     *              宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height the height in px, can use MATCH_PARENT and WRAP_CONTENT.
     *               高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setRefreshFooter(@NonNull RefreshFooter footer, int width, int height) {
        super.setRefreshFooter(footer, width, height);
        return this;
    }

    /**
     * Get footer of RefreshLayout
     * 获取当前 Footer
     * @return RefreshLayout
     */
    @Nullable
    @Override
    public RefreshFooter getRefreshFooter() {
        return mRefreshFooter instanceof RefreshFooter ? (RefreshFooter) mRefreshFooter : null;
    }

    /**
     * Get header of RefreshLayout
     * 获取当前 Header
     * @return RefreshLayout
     */
    @Nullable
    @Override
    public RefreshHeader getRefreshHeader() {
        return mRefreshHeader instanceof RefreshHeader ? (RefreshHeader) mRefreshHeader : null;
    }


    /**
     * Set refresh listener separately.
     * 单独设置刷新监听器
     * @param listener OnRefreshListener 刷新监听器
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setOnRefreshListener(final OnRefreshListener listener) {
        super.setOnRefreshListener(refreshLayout -> listener.onRefresh(SmartRefreshLayout.this));
        return this;
    }

    /**
     * Set load more listener separately.
     * 单独设置加载监听器
     * @param listener OnLoadMoreListener 加载监听器
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setOnLoadMoreListener(final OnLoadMoreListener listener) {
        super.setOnLoadMoreListener(refreshLayout -> listener.onLoadMore(SmartRefreshLayout.this));
        return this;
    }

    /**
     * Set refresh and load listeners at the same time.
     * 同时设置刷新和加载监听器
     * @param listener OnRefreshLoadMoreListener 刷新加载监听器
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setOnRefreshLoadMoreListener(final OnRefreshLoadMoreListener listener) {
        super.setOnRefreshLoadMoreListener(new cn.elevendev.refreshlayout.inherit.listener.OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull cn.elevendev.refreshlayout.inherit.api.RefreshLayout refreshLayout) {
                listener.onLoadMore(SmartRefreshLayout.this);
            }

            @Override
            public void onRefresh(@NonNull cn.elevendev.refreshlayout.inherit.api.RefreshLayout refreshLayout) {
                listener.onRefresh(SmartRefreshLayout.this);
            }
        });
        return this;
    }

    /**
     * Set up a multi-function listener.
     * Recommended {@link cn.elevendev.refreshlayout.listener.SimpleMultiPurposeListener}
     * 设置多功能监听器
     * 建议使用 {@link cn.elevendev.refreshlayout.listener.SimpleMultiPurposeListener}
     * @param listener OnMultiPurposeListener 多功能监听器
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setOnMultiPurposeListener(OnMultiPurposeListener listener) {
        super.setOnMultiListener(new SimpleMultiPurposeListener(listener, this));
        return this;
    }

    /**
     * Set the scroll boundary Decider, Can customize when you can refresh.
     * Recommended {@link cn.elevendev.refreshlayout.impl.ScrollBoundaryDeciderAdapter}
     * 设置滚动边界判断器
     * 建议使用 {@link cn.elevendev.refreshlayout.impl.ScrollBoundaryDeciderAdapter}
     * @param boundary ScrollBoundaryDecider 判断器
     * @return RefreshLayout
     */
    @Override
    public RefreshLayout setScrollBoundaryDecider(final ScrollBoundaryDecider boundary) {
        super.setScrollBoundaryDecider(new ScrollBoundaryDecider() {
            @Override
            public boolean canRefresh(View content) {
                return boundary.canRefresh(content);
            }

            @Override
            public boolean canLoadMore(View content) {
                return boundary.canLoadMore(content);
            }
        });
        return this;
    }

    /**
     * 设置默认 Header 构建器
     * @param creator Header构建器
     */
    public static void setDefaultRefreshHeaderCreator(@NonNull final DefaultRefreshHeaderCreator creator) {
        cn.elevendev.refreshlayout.inherit.SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            if (layout instanceof RefreshLayout) {
                return creator.createRefreshHeader(context, (RefreshLayout) layout);
            } else {
                return new BezierRadarHeader(context);
            }
        });
    }

    /**
     * 设置默认 Footer 构建器
     * @param creator Footer构建器
     */
    public static void setDefaultRefreshFooterCreator(@NonNull final DefaultRefreshFooterCreator creator) {
        cn.elevendev.refreshlayout.inherit.SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            if (layout instanceof RefreshLayout) {
                return creator.createRefreshFooter(context, (RefreshLayout) layout);
            } else {
                return new BallPulseFooter(context);
            }
        });
    }

    /**
     * 设置默认 Refresh 初始化器
     * @param initializer 全局初始化器
     */
    public static void setDefaultRefreshInitializer(@NonNull final DefaultRefreshInitializer initializer) {
        cn.elevendev.refreshlayout.inherit.SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            if (layout instanceof RefreshLayout) {
                initializer.initialize(context, (RefreshLayout)layout);
            }
        });
    }

}
