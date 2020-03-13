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
package com.netflix.frigga

import spock.lang.Specification

class NamesSpec extends Specification {

    def 'should dissect name with dot'() {
        when:
        Names names = Names.parseName("chukwa.collector_1-v889")

        then:
        "chukwa.collector_1-v889" == names.group
        "chukwa.collector_1" == names.cluster
        "chukwa.collector_1" == names.app
        null == names.stack
        null == names.detail
        "v889" == names.push
        889 == names.sequence
    }

    def 'should return empty object for invalid'() {
        when:
        Names names = Names.parseName('nccp-moviecontrol%27')

        then:
        null == names.group
        null == names.cluster
        null == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence
    }

    def 'should dissect group names'() {
        when:
        Names names = Names.parseName(null)
        then:
        null == names.group
        null == names.cluster
        null == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("actiondrainer")
        then:
        "actiondrainer" == names.group
        "actiondrainer" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("actiondrainer-v003")
        then:
        "actiondrainer-v003" == names.group
        "actiondrainer" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = Names.parseName("actiondrainer--v003")
        then:
        "actiondrainer--v003" == names.group
        "actiondrainer-" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = Names.parseName("actiondrainer---v003")
        then:
        "actiondrainer---v003" == names.group
        "actiondrainer--" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = Names.parseName("api-test-A")
        then:
        "api-test-A" == names.group
        "api-test-A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        null == names.push

        when:
        names = Names.parseName("api-test-A-v406")
        then:
        "api-test-A-v406" == names.group
        "api-test-A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        "v406" == names.push
        406 == names.sequence

        when:
        names = Names.parseName("api-test-~A-v004")
        then:
        "api-test-~A-v004" == names.group
        "api-test-~A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "~A" == names.detail
        "v004" == names.push
        4 == names.sequence

        when:
        names = Names.parseName("api-test-^A-v004")
        then:
        "api-test-^A-v004" == names.group
        "api-test-^A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "^A" == names.detail
        "v004" == names.push
        4 == names.sequence

        when:
        names = Names.parseName("api-^test-A-v004")
        then:
        null == names.group
        null == names.cluster
        null == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("api-~test-A-v004")
        then:
        null == names.group
        null == names.cluster
        null == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("api--~A-v004")
        then:
        "api--~A-v004" == names.group
        "api--~A" == names.cluster
        "api" == names.app
        null == names.stack
        "~A" == names.detail
        "v004" == names.push
        4 == names.sequence

        when:
        names = Names.parseName("api--^A-v004")
        then:
        "api--^A-v004" == names.group
        "api--^A" == names.cluster
        "api" == names.app
        null == names.stack
        "^A" == names.detail
        "v004" == names.push
        4 == names.sequence

        when:
        names = Names.parseName("api-test101")
        then:
        "api-test101" == names.group
        "api-test101" == names.cluster
        "api" == names.app
        "test101" == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("chukwacollector_1")
        then:
        "chukwacollector_1" == names.group
        "chukwacollector_1" == names.cluster
        "chukwacollector_1" == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("chukwacollector_1-v889")
        then:
        "chukwacollector_1-v889" == names.group
        "chukwacollector_1" == names.cluster
        "chukwacollector_1" == names.app
        null == names.stack
        null == names.detail
        "v889" == names.push
        889 == names.sequence

        when:
        names = Names.parseName("api-test-A")
        then:
        "api-test-A" == names.group
        "api-test-A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("discovery-dev")
        then:
        "discovery-dev" == names.group
        "discovery-dev" == names.cluster
        "discovery" == names.app
        "dev" == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("discovery-us-east-1d")
        then:
        "discovery-us-east-1d" == names.group
        "discovery-us-east-1d" == names.cluster
        "discovery" == names.app
        "us" == names.stack
        "east-1d" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("evcache-us-east-1d-0")
        then:
        "evcache-us-east-1d-0" == names.group
        "evcache-us-east-1d-0" == names.cluster
        "evcache" == names.app
        "us" == names.stack
        "east-1d-0" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("evcache-us-east-1d-0-v223")
        then:
        "evcache-us-east-1d-0-v223" == names.group
        "evcache-us-east-1d-0" == names.cluster
        "evcache" == names.app
        "us" == names.stack
        "east-1d-0" == names.detail
        "v223" == names.push
        223 == names.sequence

        when:
        names = Names.parseName("videometadata-navigator-integration-240-CAN")
        then:
        "videometadata-navigator-integration-240-CAN" == names.group
        "videometadata-navigator-integration-240-CAN" == names.cluster
        "videometadata" == names.app
        "navigator" == names.stack
        "integration-240-CAN" == names.detail
        null == names.push
        null == names.sequence
    }


    void testDissectGroupNameWithLabeledVariables() {
        when:
        Names names = Names.parseName("actiondrainer")
        then:
        "actiondrainer" == names.group
        "actiondrainer" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence
        null == names.countries
        null == names.devPhase
        null == names.hardware
        null == names.partners
        null == names.revision
        null == names.usedBy
        null == names.redBlackSwap
        null == names.zone

        when:
        names = Names.parseName(
                'cass-nccpintegration-random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a-v003')
        then:
        'cass-nccpintegration-random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a-v003' == names.group
        'cass-nccpintegration-random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a' == names.cluster
        'cass' == names.app
        'nccpintegration' == names.stack
        'random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a' == names.detail
        'v003' == names.push
        3 == names.sequence
        'northamerica' == names.countries
        'prod' == names.devPhase
        'gamesystems' == names.hardware
        'vizio' == names.partners
        '27' == names.revision
        'nccp' == names.usedBy
        'A' == names.redBlackSwap
        'useast1a' == names.zone

        when:
        names = Names.parseName('cass-nccpintegration-c0northamerica-d0prod')
        then:
        'cass-nccpintegration-c0northamerica-d0prod' == names.group
        'cass-nccpintegration-c0northamerica-d0prod' == names.cluster
        'cass' == names.app
        'nccpintegration' == names.stack
        'c0northamerica-d0prod' == names.detail
        null == names.push
        null == names.sequence
        'northamerica' == names.countries
        'prod' == names.devPhase
        null == names.hardware
        null == names.partners
        null == names.revision
        null == names.usedBy
        null == names.redBlackSwap
        null == names.zone

        when:
        names = Names.parseName('cass--my-stuff-c0northamerica-d0prod')
        then:
        'cass--my-stuff-c0northamerica-d0prod' == names.group
        'cass--my-stuff-c0northamerica-d0prod' == names.cluster
        'cass' == names.app
        null == names.stack
        'my-stuff-c0northamerica-d0prod' == names.detail
        null == names.push
        null == names.sequence
        'northamerica' == names.countries
        'prod' == names.devPhase
        null == names.hardware
        null == names.partners
        null == names.revision
        null == names.usedBy
        null == names.redBlackSwap
        null == names.zone

        when:
        names = Names.parseName('cass-c0northamerica-d0prod')
        then:
        'cass-c0northamerica-d0prod' == names.group
        'cass-c0northamerica-d0prod' == names.cluster
        'cass' == names.app
        'c0northamerica' == names.stack // breaking - app/stack/detail takes priority
        'd0prod' == names.detail
        null == names.push
        null == names.sequence
        'northamerica' == names.countries
        'prod' == names.devPhase
        null == names.hardware
        null == names.partners
        null == names.revision
        null == names.usedBy
        null == names.redBlackSwap
        null == names.zone

        when:
        names = Names.parseName('cass-c0northamerica-d0prod-v102')
        then:
        'cass-c0northamerica-d0prod-v102' == names.group
        'cass-c0northamerica-d0prod' == names.cluster
        'cass' == names.app
        'c0northamerica' == names.stack
        'd0prod' == names.detail
        'v102' == names.push
        102 == names.sequence
        'northamerica' == names.countries
        'prod' == names.devPhase
        null == names.hardware
        null == names.partners
        null == names.revision
        null == names.usedBy
        null == names.redBlackSwap
        null == names.zone

        when:
        names = Names.parseName('cass-v102')
        then:
        'cass-v102' == names.group
        'cass' == names.cluster
        'cass' == names.app
        null == names.stack
        null == names.detail
        'v102' == names.push
        102 == names.sequence
        null == names.countries
        null == names.devPhase
        null == names.hardware
        null == names.partners
        null == names.revision
        null == names.usedBy
        null == names.redBlackSwap
        null == names.zone
    }
}
