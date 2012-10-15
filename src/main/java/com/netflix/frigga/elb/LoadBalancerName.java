package com.netflix.frigga.elb;

import com.netflix.frigga.CompoundName;

public class LoadBalancerName {

    private CompoundName compoundName;

    public LoadBalancerName(String loadBalancerName) {
        compoundName = CompoundName.parseName(loadBalancerName);
    }

    public static LoadBalancerName fromName(String loadBalancerName) {
        return new LoadBalancerName(loadBalancerName);
    }

    public String getApp() {
        return compoundName.getApp();
    }

    public String getStack() {
        return compoundName.getStack();
    }

    public String getDetail() {
        return compoundName.getDetail();
    }

}
