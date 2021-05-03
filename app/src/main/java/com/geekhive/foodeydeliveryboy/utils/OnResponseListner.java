package com.geekhive.foodeydeliveryboy.utils;

public interface OnResponseListner<T> {
    void onResponse(T t, WebServices.ApiType apiType, boolean z);
}