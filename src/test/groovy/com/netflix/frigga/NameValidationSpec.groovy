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

class NameValidationSpec extends Specification {

    def 'should validate names'() {
        expect:
        NameValidation.checkName("abha")
        NameValidation.checkName("account_batch")
        NameValidation.checkName("account.batch")
        !NameValidation.checkName("account#batch")
        !NameValidation.checkName("")
        !NameValidation.checkName(null)
    }

    def 'should validate names with hyphens'() {
        expect:
        NameValidation.checkNameWithHyphen("A")
        NameValidation.checkNameWithHyphen("0")
        NameValidation.checkNameWithHyphen("east-1c-0")
        NameValidation.checkNameWithHyphen("230CAN-next-A")
        NameValidation.checkNameWithHyphen("integration-240-USA")
        NameValidation.checkNameWithHyphen("integration-240-usa-iphone-ipad-ios5-even-numbered-days-not-weekends")
        NameValidation.checkNameWithHyphen("----")
        NameValidation.checkNameWithHyphen("__._._--_..")
        !NameValidation.checkNameWithHyphen("230CAN#next-A")
        !NameValidation.checkNameWithHyphen("")
        !NameValidation.checkNameWithHyphen(null)
    }

    def 'should validate reserved format'() {
        expect:
        NameValidation.usesReservedFormat("v248")
        NameValidation.usesReservedFormat("abcache-c0USA")
        !NameValidation.usesReservedFormat("hellojgritman")
    }
}
