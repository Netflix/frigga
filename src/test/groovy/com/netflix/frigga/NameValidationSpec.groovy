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

    def 'should validate details'() {
        expect:
        NameValidation.checkDetail("A")
        NameValidation.checkDetail("0")
        NameValidation.checkDetail("east-1c-0")
        NameValidation.checkDetail("230CAN-next-A")
        NameValidation.checkDetail("integration-240-USA")
        NameValidation.checkDetail("integration-240-usa-iphone-ipad-ios5-even-numbered-days-not-weekends")
        NameValidation.checkDetail("----")
        NameValidation.checkDetail("__._._--_..")
        !NameValidation.checkDetail("230CAN#next-A")
        !NameValidation.checkDetail("")
        !NameValidation.checkDetail(null)
    }

    def 'should validate reserved format'() {
        expect:
        NameValidation.usesReservedFormat("v248")
        NameValidation.usesReservedFormat("abcache-c0USA")
        !NameValidation.usesReservedFormat("hellojgritman")
    }
}
