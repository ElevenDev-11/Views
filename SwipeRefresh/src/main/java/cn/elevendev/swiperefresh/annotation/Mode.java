package cn.elevendev.swiperefresh.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.elevendev.swiperefresh.config.Constants;

@Retention(RetentionPolicy.SOURCE)
@IntDef({Constants.MODE_DEFAULT, Constants.MODE_SCALE})
public @interface Mode {}
