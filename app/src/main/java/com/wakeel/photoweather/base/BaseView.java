package com.wakeel.photoweather.base;


public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);

    void setLoadingIndicator(boolean b);
}
