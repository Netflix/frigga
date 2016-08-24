package com.netflix.frigga.spi;

import com.netflix.frigga.Names;

/**
 * CustomizedNames.
 *
 * Example that takes the form of
 * (failover_)(original-app-naming)
 */
public class CustomizedNames extends Names {

    private static final String FAILOVER_PREFIX="failover_";

    static String translateToBase(String name) {
        if (isFailoverStack(name)) {
            return name.substring(FAILOVER_PREFIX.length());
        }
        return name;
    }

    static boolean isFailoverStack(String name) {
        return name.startsWith(FAILOVER_PREFIX);
    }

    private final boolean failoverStack;

    public CustomizedNames(String name) {
        super(translateToBase(name));
        this.failoverStack = isFailoverStack(name);
    }

    public boolean isFailoverStack() {
        return failoverStack;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Boolean.hashCode(failoverStack);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((CustomizedNames) obj).isFailoverStack() == failoverStack;
    }
}
