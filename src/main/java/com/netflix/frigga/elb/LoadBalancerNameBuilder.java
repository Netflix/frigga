package com.netflix.frigga.elb;

import com.netflix.frigga.NameBuilder;

public class LoadBalancerNameBuilder extends NameBuilder {

    private String appName;
    private String stack;
    private String detail;

    public String buildLoadBalancerName() {
        return combineAppStackDetail(appName, stack, detail);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
