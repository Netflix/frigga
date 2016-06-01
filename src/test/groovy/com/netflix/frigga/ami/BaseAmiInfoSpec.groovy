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
package com.netflix.frigga.ami

import spock.lang.Specification

class BaseAmiInfoSpec extends Specification {

    def 'id tests'() {
        expect:
        null == BaseAmiInfo.parseDescription('').baseAmiId
        null == BaseAmiInfo.parseDescription(null).baseAmiId
        'ami-50886239' == BaseAmiInfo.parseDescription('base_ami_id=ami-50886239,base_ami_name=servicenet-roku-qadd.dc.81210.10.44').baseAmiId
        'ami-1eb75c77' == BaseAmiInfo.parseDescription('base_ami_id=ami-1eb75c77,base_ami_name=servicenet-roku-qadd.dc.81210.10.44').baseAmiId
        'ami-1eb75c77' == BaseAmiInfo.parseDescription('base_ami_name=servicenet-roku-qadd.dc.81210.10.44,base_ami_id=ami-1eb75c77').baseAmiId
        'ami-7b4eb912' == BaseAmiInfo.parseDescription('store=ebs,ancestor_name=ebs-centosbase-x86_64-20101124,ancestor_id=ami-7b4eb912').baseAmiId
    }

    def 'name tests'() {
        expect:
        'servicenet-roku-qadd.dc.81210.10.44' == BaseAmiInfo.parseDescription('base_ami_id=ami-50886239,base_ami_name=servicenet-roku-qadd.dc.81210.10.44').baseAmiName
        'servicenet-roku-qadd.dc.81210.10.44' == BaseAmiInfo.parseDescription('base_ami_id=ami-1eb75c77,base_ami_name=servicenet-roku-qadd.dc.81210.10.44').baseAmiName
        'servicenet-roku-qadd.dc.81210.10.44' == BaseAmiInfo.parseDescription('base_ami_name=servicenet-roku-qadd.dc.81210.10.44,base_ami_id=ami-1eb75c77').baseAmiName
        'ebs-centosbase-x86_64-20101124' == BaseAmiInfo.parseDescription('store=ebs,ancestor_name=ebs-centosbase-x86_64-20101124,ancestor_id=ami-7b4eb912').baseAmiName
    }

    void 'date tests'() {
        expect:
        null == BaseAmiInfo.parseDescription('base_ami_id=ami-50886239,base_ami_name=servicenet-roku-qadd.dc.81210.10.44').baseAmiDate
        null == BaseAmiInfo.parseDescription('base_ami_id=ami-1eb75c77,base_ami_name=servicenet-roku-qadd.dc.81210.10.44').baseAmiDate
        null == BaseAmiInfo.parseDescription('base_ami_name=servicenet-roku-qadd.dc.81210.10.44,base_ami_id=ami-1eb75c77').baseAmiDate
        Date.parse('yyyy-MM-dd', '2010-11-24') == BaseAmiInfo.parseDescription('store=ebs,ancestor_name=ebs-centosbase-x86_64-20101124,ancestor_id=ami-7b4eb912').baseAmiDate
    }

}
