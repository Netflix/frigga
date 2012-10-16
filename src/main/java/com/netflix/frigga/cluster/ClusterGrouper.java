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

import com.netflix.frigga.Names;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusterGrouper {

    public static <T> Map<String, List<T>> groupByClusterName(List<T> asgs, AsgNameProvider<T> nameProvider) {
        Map<String, List<T>> clusterNamesToAsgs = new HashMap<String, List<T>>();
        for (T asg : asgs) {
            String clusterName = Names.parseName(nameProvider.extractAsgName(asg)).getCluster();
            if(!clusterNamesToAsgs.containsKey(clusterName)) {
                clusterNamesToAsgs.put(clusterName, new ArrayList<T>());
            }
            clusterNamesToAsgs.get(clusterName).add(asg);
        }
        return clusterNamesToAsgs;
    }

    public static Map<String, List<String>> groupAsgNamesByClusterName(List<String> asgNames) {
        return groupByClusterName(asgNames, new AsgNameProvider<String>() {
            public String extractAsgName(String asgName) {
                return asgName;
            }
        });
    }

}
