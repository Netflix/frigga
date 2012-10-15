package com.netflix.frigga;

public abstract class NameBuilder {

    protected String combineAppStackDetail(String appName, String stack, String detail) {
        NameValidation.notEmpty(appName, "appName");
        // Use empty strings, not null references that output "null"
        stack = stack != null ? stack : "";
        if (detail != null && !detail.isEmpty()) {
            return appName + "-" + stack + "-" + detail;
        }
        if (!stack.isEmpty()) {
            return appName + "-" + stack;
        }
        return appName;
    }

}
