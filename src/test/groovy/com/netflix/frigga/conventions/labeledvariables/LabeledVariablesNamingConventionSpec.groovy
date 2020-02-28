/**
 * Copyright 2020 Netflix, Inc.
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
package com.netflix.frigga.conventions.labeledvariables

import spock.lang.Specification
import spock.lang.Subject

class LabeledVariablesNamingConventionSpec extends Specification {
  @Subject LabeledVariablesNamingConvention subject = new LabeledVariablesNamingConvention();

  void testExtractLabeledVariable() {
    expect:
    'sony' == LabeledVariablesNamingConvention.extractLabeledVariable('-p0sony', LabeledVariablesNamingConvention.LABELED_PARTNERS_KEY_PATTERN)
    'northamerica' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_COUNTRIES_PATTERN)
    'prod' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_DEV_PHASE_KEY_PATTERN)
    'gamesystems' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_HARDWARE_KEY_PATTERN)
    'vizio' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_PARTNERS_KEY_PATTERN)
    '27' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_REVISION_KEY_PATTERN)
    'nccp' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_USED_BY_KEY_PATTERN)
    'useast1a' == LabeledVariablesNamingConvention.extractLabeledVariable('-c0northamerica-d0prod-h0gamesystems-p0vizio-r027-u0nccp-x0A-z0useast1a', LabeledVariablesNamingConvention.LABELED_ZONE_KEY_PATTERN)
  }

  def "parses labeled variables"() {
    when:
    def result = subject.extractNamingConvention(details)

    then:
    result.unprocessed == unprocessed
    result.result.isPresent()

    when:
    def vars = result.result.get()

    then:
    vars.countries == country
    vars.devPhase == devPhase
    vars.hardware == hardware
    vars.partners == partners
    vars.revision == revision
    vars.usedBy == usedBy
    vars.redBlackSwap == redBlackSwap
    vars.zone == zone


    where:
    unprocessed = 'woohoo'
    country = 'northamerica'
    devPhase = 'prod'
    hardware = 'gamesystems'
    partners = 'vizio'
    revision = '27'
    usedBy = 'nccp'
    redBlackSwap = 'A'
    zone = 'useast1a'


    details = "$unprocessed-c0$country-d0$devPhase-h0$hardware-p0$partners-r0$revision-u0$usedBy-w0$redBlackSwap-z0$zone"
  }

}
