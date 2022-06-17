package com.orange.amaplike.po;

import com.amap.api.services.route.DrivePath;

public class UserDrivePath {
    private String account;
    private DrivePath drivePath;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public DrivePath getDrivePath() {
        return drivePath;
    }

    public void setDrivePath(DrivePath drivePath) {
        this.drivePath = drivePath;
    }

    public UserDrivePath() {
    }

    public UserDrivePath(String account, DrivePath drivePath) {
        this.account = account;
        this.drivePath = drivePath;
    }

    @Override
    public String toString() {
        return "UserDrivePath{" +
                "account='" + account + '\'' +
                ", drivePath=" + drivePath +
                '}';
    }
}
