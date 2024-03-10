package cn.elevendev.swiperefresh.indicator;

import cn.elevendev.swiperefresh.annotation.MovingStatus;

public interface IIndicatorSetter {

    void setRatioToKeepFooter(float ratio);

    void setRatioToKeepHeader(float ratio);

    void setFooterHeight(int height);

    void setHeaderHeight(int height);

    void onFingerDown(float x, float y);

    void onFingerMove(float x, float y);

    void onFingerUp();

    void setRatioOfFooterToRefresh(float ratio);

    void setRatioOfHeaderToRefresh(float ratio);

    void setRatioToRefresh(float ratio);

    void setResistanceOfFooter(float resistance);

    void setResistance(float resistance);

    void setResistanceOfHeader(float resistance);

    void setCurrentPos(int current);

    void setMovingStatus(@MovingStatus int direction);

    void setMaxMoveRatio(float ratio);

    void setMaxMoveRatioOfHeader(float ratio);

    void setMaxMoveRatioOfFooter(float ratio);

    void setOffsetCalculator(IIndicator.IOffsetCalculator calculator);
}
