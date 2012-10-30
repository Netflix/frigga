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
package com.netflix.frigga.cluster;

/**
 * Command object for extracting the ASG name from the provided type. Used with grouping ASGs by cluster.
 *
 * @param <T> Type to extract ASG name from
 */
public interface AsgNameProvider<T> {

    /**
     * Extracts the ASG name from an input object.
     *
     * @param object the object to inspect
     * @return asg name of the provided object
     */
    String extractAsgName(T object);

}
