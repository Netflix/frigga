/**
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE_2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.frigga

import spock.lang.Specification

class NamesWithCustomizationSpec extends Specification {

    def 'should dissect name with dot'() {
        when: 
        Names names = Names.parseName("chukwa.collector-1_v889")

        then:
        "chukwa.collector-1_v889" == names.group
        "chukwa.collector-1" == names.cluster
        "chukwa.collector-1" == names.app
        null == names.stack
        null == names.detail
        "v889" == names.push
        889 == names.sequence
    }

    def 'should return empty object for invalid'() {
        when:
        Names names = Names.parseName('nccp_moviecontrol%27')

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
        names = Names.parseName("actiondrainer_v003")
        then:
        "actiondrainer_v003" == names.group
        "actiondrainer" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = Names.parseName("actiondrainer__v003")
        then:
        "actiondrainer__v003" == names.group
        "actiondrainer_" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = Names.parseName("actiondrainer___v003")
        then:
        "actiondrainer___v003" == names.group
        "actiondrainer__" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = Names.parseName("api_test_A")
        then:
        "api_test_A" == names.group
        "api_test_A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        null == names.push

        when:
        names = Names.parseName("api_test_A_v406")
        then:
        "api_test_A_v406" == names.group
        "api_test_A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        "v406" == names.push
        406 == names.sequence

        when:
        names = Names.parseName("api_test_A_v40600")
        then:
        "api_test_A_v40600" == names.group
        "api_test_A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        "v40600" == names.push
        40600 == names.sequence

        when:
        names = Names.parseName("api_test_A_v4")
        then:
        "api_test_A_v4" == names.group
        "api_test_A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        "v4" == names.push
        4 == names.sequence

        when:
        names = Names.parseName("api_test101")
        then:
        "api_test101" == names.group
        "api_test101" == names.cluster
        "api" == names.app
        "test101" == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("chukwacollector-1")
        then:
        "chukwacollector-1" == names.group
        "chukwacollector-1" == names.cluster
        "chukwacollector-1" == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("chukwacollector-1_v889")
        then:
        "chukwacollector-1_v889" == names.group
        "chukwacollector-1" == names.cluster
        "chukwacollector-1" == names.app
        null == names.stack
        null == names.detail
        "v889" == names.push
        889 == names.sequence

        when:
        names = Names.parseName("api_test_A")
        then:
        "api_test_A" == names.group
        "api_test_A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("discovery_dev")
        then:
        "discovery_dev" == names.group
        "discovery_dev" == names.cluster
        "discovery" == names.app
        "dev" == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("discovery_us_east_1d")
        then:
        "discovery_us_east_1d" == names.group
        "discovery_us_east_1d" == names.cluster
        "discovery" == names.app
        "us" == names.stack
        "east_1d" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("evcache_us_east_1d_0")
        then:
        "evcache_us_east_1d_0" == names.group
        "evcache_us_east_1d_0" == names.cluster
        "evcache" == names.app
        "us" == names.stack
        "east_1d_0" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = Names.parseName("evcache_us_east_1d_0_v223")
        then:
        "evcache_us_east_1d_0_v223" == names.group
        "evcache_us_east_1d_0" == names.cluster
        "evcache" == names.app
        "us" == names.stack
        "east_1d_0" == names.detail
        "v223" == names.push
        223 == names.sequence

        when:
        names = Names.parseName("videometadata_navigator_integration_240_CAN")
        then:
        "videometadata_navigator_integration_240_CAN" == names.group
        "videometadata_navigator_integration_240_CAN" == names.cluster
        "videometadata" == names.app
        "navigator" == names.stack
        "integration_240_CAN" == names.detail
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
                'cass_nccpintegration_random_junk_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_w0A_z0useast1a_v003')
        then:
        'cass_nccpintegration_random_junk_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_w0A_z0useast1a_v003' == names.group
        'cass_nccpintegration_random_junk_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_w0A_z0useast1a' == names.cluster
        'cass' == names.app
        'nccpintegration' == names.stack
        'random_junk' == names.detail
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
        names = Names.parseName('cass_nccpintegration_c0northamerica_d0prod')
        then:
        'cass_nccpintegration_c0northamerica_d0prod' == names.group
        'cass_nccpintegration_c0northamerica_d0prod' == names.cluster
        'cass' == names.app
        'nccpintegration' == names.stack
        null == names.detail
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
        names = Names.parseName('cass__my_stuff_c0northamerica_d0prod')
        then:
        'cass__my_stuff_c0northamerica_d0prod' == names.group
        'cass__my_stuff_c0northamerica_d0prod' == names.cluster
        'cass' == names.app
        null == names.stack
        'my_stuff' == names.detail
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
        names = Names.parseName('cass_c0northamerica_d0prod')
        then:
        'cass_c0northamerica_d0prod' == names.group
        'cass_c0northamerica_d0prod' == names.cluster
        'cass' == names.app
        null == names.stack
        null == names.detail
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
        names = Names.parseName('cass_c0northamerica_d0prod_v102')
        then:
        'cass_c0northamerica_d0prod_v102' == names.group
        'cass_c0northamerica_d0prod' == names.cluster
        'cass' == names.app
        null == names.stack
        null == names.detail
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
        names = Names.parseName('cass_v102')
        then:
        'cass_v102' == names.group
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

    void testExtractLabeledVariable() {
        when:
        Names names = Names.parseName("test")

        then:
        'sony' == names.extractLabeledVariable('_p0sony', 'p')
        'northamerica' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'c')
        'prod' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'd')
        'gamesystems' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'h')
        'vizio' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'p')
        '27' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'r')
        'nccp' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'u')
        'A' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'x')
        'useast1a' == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'z')
        null == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', 'a')
        null == names.extractLabeledVariable('', 'a')
        null == names.extractLabeledVariable(null, 'a')
        null == names.extractLabeledVariable(null, '')
        null == names.extractLabeledVariable(null, null)
        null == names.extractLabeledVariable('', null)
        null == names.extractLabeledVariable('_c0northamerica_d0prod_h0gamesystems_p0vizio_r027_u0nccp_x0A_z0useast1a', null)
        null == names.extractLabeledVariable('_p0sony', '')
        null == names.extractLabeledVariable('_p0sony', null)
    }

}
