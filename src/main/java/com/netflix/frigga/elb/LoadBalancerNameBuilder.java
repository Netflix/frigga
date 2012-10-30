/**
 * Copyright 2012 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.frigga.elb;

import com.netflix.frigga.NameBuilder;

/**
 * Logic for constructing the name of a new load balancer in Asgard.
 */
public class LoadBalancerNameBuilder extends NameBuilder {

    private String appName;
    private String stack;
    private String detail;

    /**
     * Constructs and returns the name of the load balancer.
     *
     * @return load balancer name
     */
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
