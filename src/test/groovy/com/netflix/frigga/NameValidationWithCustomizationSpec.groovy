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

class NameValidationWithCustomizationSpec extends Specification {

    def 'should validate names'() {
        expect:
        NameValidation.checkName("abha")
        NameValidation.checkName("account-batch")
        NameValidation.checkName("account.batch")
        !NameValidation.checkName("account#batch")
        !NameValidation.checkName("")
        !NameValidation.checkName(null)
    }

    def 'should validate names with hyphens'() {
        expect:
        NameValidation.checkNameWithDelimiter("A")
        NameValidation.checkNameWithDelimiter("0")
        NameValidation.checkNameWithDelimiter("east_1c_0")
        NameValidation.checkNameWithDelimiter("230CAN_next_A")
        NameValidation.checkNameWithDelimiter("integration_240_USA")
        NameValidation.checkNameWithDelimiter("integration_240_usa_iphone_ipad_ios5_even_numbered_days_not_weekends")
        NameValidation.checkNameWithDelimiter("____")
        NameValidation.checkNameWithDelimiter("--.-.-__-..")
        !NameValidation.checkNameWithDelimiter("230CAN#next_A")
        !NameValidation.checkNameWithDelimiter("")
        !NameValidation.checkNameWithDelimiter(null)
    }

    def 'should validate reserved format'() {
        expect:
        NameValidation.usesReservedFormat("v248")
        NameValidation.usesReservedFormat("abcache_c0USA")
        !NameValidation.usesReservedFormat("hellojgritman")
    }

    def 'should validate names with carets'() {
        expect:
        NameValidation.checkNameWithDelimiter("A^")
        NameValidation.checkNameWithDelimiter("something_^1.0.0.0")
    }

}
