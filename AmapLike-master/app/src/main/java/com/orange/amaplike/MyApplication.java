package com.orange.amaplike;

import android.app.Application;

import com.orange.amaplike.po.User;

public class MyApplication extends Application{
    // 将user写在app类下，使得整个生命周期与应用程序的生命周期相同
    public static User user;
}
