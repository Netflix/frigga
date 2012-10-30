package com.netflix.frigga.cluster

import spock.lang.Specification

class ClusterGrouperSpec extends Specification {

    def 'should group list of asg names'() {
        when:
        def groupedAsgs = ClusterGrouper.groupAsgNamesByClusterName(['actiondrainer','actiondrainer-v003','chukwa.collector_1-v889'])
        then:
        groupedAsgs.size() == 2
        groupedAsgs['actiondrainer'] == ['actiondrainer', 'actiondrainer-v003']
        groupedAsgs['chukwa.collector_1'] == ['chukwa.collector_1-v889']
    }

}
