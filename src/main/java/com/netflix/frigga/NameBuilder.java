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
package com.netflix.frigga;

/**
 * Abstract class for classes in charge of constructing Asgard names.
 */
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
