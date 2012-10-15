package com.netflix.frigga.cluster;

import com.netflix.frigga.CompoundName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusterGrouper {

    public static <T> Map<String, List<T>> groupByClusterName(List<T> asgs, AsgNameProvider<T> nameProvider) {
        Map<String, List<T>> clusterNamesToAsgs = new HashMap<String, List<T>>();
        for (T asg : asgs) {
            String clusterName = CompoundName.clusterFromGroupName(nameProvider.extractAsgName(asg));
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
