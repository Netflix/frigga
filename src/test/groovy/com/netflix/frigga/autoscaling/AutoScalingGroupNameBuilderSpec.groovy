/**
 * Copyright 2016 Netflix, Inc.
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
package com.netflix.frigga.autoscaling

import spock.lang.Specification

class AutoScalingGroupNameBuilderSpec extends Specification {

    def 'should build simple cluster name'() {
        when:
        AutoScalingGroupNameBuilder cluster = new AutoScalingGroupNameBuilder()
        cluster.appName = "app"
        cluster.stack = "stack"
        cluster.detail = "multi-part-detail"

        then:
        "app-stack-multi-part-detail" == cluster.buildGroupName()
    }

    def 'should be able to chain builder methods'() {
        when:
        AutoScalingGroupNameBuilder cluster = new AutoScalingGroupNameBuilder()
        cluster.withAppName("app")
            .withStack("stack")
            .withDetail("detail")

        then:
        "app-stack-detail" == cluster.buildGroupName()
    }

    def 'stack cannot contain a push'() {
        when:
        AutoScalingGroupNameBuilder cluster = new AutoScalingGroupNameBuilder()
        cluster.withAppName("app").withStack("v123")
        cluster.buildGroupName(true)

        then:
        def ex = thrown(IllegalArgumentException.class)
        ex.message == "stack cannot contain a push version"
    }

    def 'detail cannot contain a push'() {
        when:
        AutoScalingGroupNameBuilder cluster = new AutoScalingGroupNameBuilder()
        cluster.withAppName("app").withDetail("v123")
        cluster.buildGroupName(true)

        then:
        def ex = thrown(IllegalArgumentException.class)
        ex.message == "detail cannot contain a push version"

        when:
        cluster = new AutoScalingGroupNameBuilder()
        cluster.withAppName("app").withDetail("abc-v123-def")
        cluster.buildGroupName(true)

        then:
        ex = thrown(IllegalArgumentException.class)
        ex.message == "detail cannot contain a push version"

        when:
        cluster = new AutoScalingGroupNameBuilder()
        cluster.withAppName("app").withDetail("abc-v12b3-def")
        cluster.buildGroupName(true)

        then:
        noExceptionThrown()
    }
}
