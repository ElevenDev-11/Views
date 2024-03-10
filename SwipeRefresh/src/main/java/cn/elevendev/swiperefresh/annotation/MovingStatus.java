package cn.elevendev.swiperefresh.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.elevendev.swiperefresh.config.Constants;


@IntDef({Constants.MOVING_CONTENT, Constants.MOVING_FOOTER, Constants.MOVING_HEADER})
@Retention(RetentionPolicy.SOURCE)
public @interface MovingStatus {}
