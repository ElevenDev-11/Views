package cn.elevendev.swiperefresh.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.elevendev.swiperefresh.config.Constants;


@Retention(RetentionPolicy.SOURCE)
@IntDef({Constants.ACTION_NOTIFY, Constants.ACTION_AT_ONCE, Constants.ACTION_NOTHING})
public @interface Action {}
