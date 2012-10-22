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

/**
 * Utility methods for grouping ASG related objects by cluster. The cluster name is derived from the name of the ASG.
 */
public class ClusterGrouper {

    /**
     * Group a list of ASG related objects by cluster name.
     *
     * @param inputs list of objects associated with an ASG
     * @param nameProvider strategy object used to extract the ASG name of the object type of the input list
     * @return map of cluster name to list of input object
     */
    public static <T> Map<String, List<T>> groupByClusterName(List<T> inputs, AsgNameProvider<T> nameProvider) {
        Map<String, List<T>> clusterNamesToAsgs = new HashMap<String, List<T>>();
        for (T asg : inputs) {
            String clusterName = Names.parseName(nameProvider.extractAsgName(asg)).getCluster();
            if(!clusterNamesToAsgs.containsKey(clusterName)) {
                clusterNamesToAsgs.put(clusterName, new ArrayList<T>());
            }
            clusterNamesToAsgs.get(clusterName).add(asg);
        }
        return clusterNamesToAsgs;
    }

    /**
     * Groups a list of ASG names by cluster name.
     *
     * @param asgNames list of asg names
     * @return map of cluster name to list of ASG names in that cluster
     */
    public static Map<String, List<String>> groupAsgNamesByClusterName(List<String> asgNames) {
        return groupByClusterName(asgNames, new AsgNameProvider<String>() {
            public String extractAsgName(String asgName) {
                return asgName;
            }
        });
    }

}
