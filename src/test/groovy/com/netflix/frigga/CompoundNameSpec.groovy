package com.netflix.frigga

import com.netflix.frigga.CompoundName;

import spock.lang.Specification

class CompoundNameSpec extends Specification {

    def 'should dissect name with dot'() {
        when:
        CompoundName names = CompoundName.dissectCompoundName("chukwa.collector_1-v889")

        then:
        "chukwa.collector_1-v889" == names.group
        "chukwa.collector_1" == names.cluster
        "chukwa.collector_1" == names.app
        null == names.stack
        null == names.detail
        "v889" == names.push
        889 == names.sequence
    }

    def 'should return empty obejct for invalid'() {
        when:
        CompoundName names = CompoundName.dissectCompoundName('nccp-moviecontrol%27')

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
        CompoundName names = CompoundName.dissectCompoundName(null)
        then:
        null == names.group
        null == names.cluster
        null == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("actiondrainer")
        then:
        "actiondrainer" == names.group
        "actiondrainer" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("actiondrainer-v003")
        then:
        "actiondrainer-v003" == names.group
        "actiondrainer" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = CompoundName.dissectCompoundName("actiondrainer--v003")
        then:
        "actiondrainer--v003" == names.group
        "actiondrainer-" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = CompoundName.dissectCompoundName("actiondrainer---v003")
        then:
        "actiondrainer---v003" == names.group
        "actiondrainer--" == names.cluster
        "actiondrainer" == names.app
        null == names.stack
        null == names.detail
        "v003" == names.push
        3 == names.sequence

        when:
        names = CompoundName.dissectCompoundName("api-test-A")
        then:
        "api-test-A" == names.group
        "api-test-A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        null == names.push

        when:
        names = CompoundName.dissectCompoundName("api-test-A-v406")
        then:
        "api-test-A-v406" == names.group
        "api-test-A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        "v406" == names.push
        406 == names.sequence

        when:
        names = CompoundName.dissectCompoundName("api-test101")
        then:
        "api-test101" == names.group
        "api-test101" == names.cluster
        "api" == names.app
        "test101" == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("chukwacollector_1")
        then:
        "chukwacollector_1" == names.group
        "chukwacollector_1" == names.cluster
        "chukwacollector_1" == names.app
        null == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("chukwacollector_1-v889")
        then:
        "chukwacollector_1-v889" == names.group
        "chukwacollector_1" == names.cluster
        "chukwacollector_1" == names.app
        null == names.stack
        null == names.detail
        "v889" == names.push
        889 == names.sequence

        when:
        names = CompoundName.dissectCompoundName("api-test-A")
        then:
        "api-test-A" == names.group
        "api-test-A" == names.cluster
        "api" == names.app
        "test" == names.stack
        "A" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("discovery-dev")
        then:
        "discovery-dev" == names.group
        "discovery-dev" == names.cluster
        "discovery" == names.app
        "dev" == names.stack
        null == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("discovery-us-east-1d")
        then:
        "discovery-us-east-1d" == names.group
        "discovery-us-east-1d" == names.cluster
        "discovery" == names.app
        "us" == names.stack
        "east-1d" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("evcache-us-east-1d-0")
        then:
        "evcache-us-east-1d-0" == names.group
        "evcache-us-east-1d-0" == names.cluster
        "evcache" == names.app
        "us" == names.stack
        "east-1d-0" == names.detail
        null == names.push
        null == names.sequence

        when:
        names = CompoundName.dissectCompoundName("evcache-us-east-1d-0-v223")
        then:
        "evcache-us-east-1d-0-v223" == names.group
        "evcache-us-east-1d-0" == names.cluster
        "evcache" == names.app
        "us" == names.stack
        "east-1d-0" == names.detail
        "v223" == names.push
        223 == names.sequence

        when:
        names = CompoundName.dissectCompoundName("videometadata-navigator-integration-240-CAN")
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
        CompoundName names = CompoundName.dissectCompoundName("actiondrainer")
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
        names = CompoundName.dissectCompoundName(
                'cass-nccpintegration-random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a-v003')
        then:
        'cass-nccpintegration-random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a-v003' == names.group
        'cass-nccpintegration-random-junk-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-w0A-z0useast1a' == names.cluster
        'cass' == names.app
        'nccpintegration' == names.stack
        'random-junk' == names.detail
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
        names = CompoundName.dissectCompoundName('cass-nccpintegration-c0northamerica-d0prod')
        then:
        'cass-nccpintegration-c0northamerica-d0prod' == names.group
        'cass-nccpintegration-c0northamerica-d0prod' == names.cluster
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
        names = CompoundName.dissectCompoundName('cass--my-stuff-c0northamerica-d0prod')
        then:
        'cass--my-stuff-c0northamerica-d0prod' == names.group
        'cass--my-stuff-c0northamerica-d0prod' == names.cluster
        'cass' == names.app
        null == names.stack
        'my-stuff' == names.detail
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
        names = CompoundName.dissectCompoundName('cass-c0northamerica-d0prod')
        then:
        'cass-c0northamerica-d0prod' == names.group
        'cass-c0northamerica-d0prod' == names.cluster
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
        names = CompoundName.dissectCompoundName('cass-c0northamerica-d0prod-v102')
        then:
        'cass-c0northamerica-d0prod-v102' == names.group
        'cass-c0northamerica-d0prod' == names.cluster
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
        names = CompoundName.dissectCompoundName('cass-v102')
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

    void testExtractLabeledVariable() {
        expect:
        'sony' == CompoundName.extractLabeledVariable('-p0sony', 'p')
        'northamerica' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'c')
        'prod' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'd')
        'gamesystems' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'h')
        'vizio' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'p')
        '27' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'r')
        'nccp' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'u')
        'A' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'x')
        'useast1a' == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'z')
        null == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', 'a')
        null == CompoundName.extractLabeledVariable('', 'a')
        null == CompoundName.extractLabeledVariable(null, 'a')
        null == CompoundName.extractLabeledVariable(null, '')
        null == CompoundName.extractLabeledVariable(null, null)
        null == CompoundName.extractLabeledVariable('', null)
        null == CompoundName.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', null)
        null == CompoundName.extractLabeledVariable('-p0sony', '')
        null == CompoundName.extractLabeledVariable('-p0sony', null)
    }

}
