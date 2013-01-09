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
