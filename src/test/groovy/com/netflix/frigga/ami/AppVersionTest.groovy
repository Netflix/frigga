package com.netflix.frigga.ami

import spock.lang.Specification

class AppVersionTest extends Specification {

    AppVersion appVersion = newAppVersion("app", "1.2.3", "WE-WAPP-app", "456", "1234567")

    def 'should return 0 when comparing to itself'() {
        expect:
        appVersion == appVersion
        0 == (appVersion <=> appVersion)
    }

    def 'should return 0 when comparing to identical'() {
        when:
        AppVersion same = newAppVersion("app", "1.2.3", "WE-WAPP-app", "456", "1234567")

        then:
        appVersion == same
        0 == (appVersion <=> same)
        0 == (same <=> appVersion)
    }

    def 'should compare with null object properly'() {
        expect:
        appVersion != null
        1 == (appVersion <=> null)
        -1 == (null <=> appVersion)

        appVersion != "foo"
    }

    def 'should fail when comparing to string'() {
        when:
        appVersion <=> "foo"

        then:
        thrown(ClassCastException)

        when:
        "foo" <=> appVersion

        then:
        thrown(ClassCastException)
    }

    def 'should compare components with null'() {
        expect:
        assertIsLessThan(newAppVersion(null, "1.2.3", "WE-WAPP-app", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", null, "WE-WAPP-app", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", null, "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", null, "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "456", null), appVersion)
    }

    def 'should compare components with empty strings'() {
        expect:
        assertIsLessThan(newAppVersion("", "1.2.3", "WE-WAPP-app", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "", "WE-WAPP-app", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "456", ""), appVersion)
    }

    def 'should compare components'() {
        expect:
        assertIsLessThan(newAppVersion("App", "1.2.3", "WE-WAPP-app", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("ape", "1.2.3", "WE-WAPP-app", "456", "1234567"), appVersion)

        assertIsLessThan(newAppVersion("app", "0.1.2", "WE-WAPP-app", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.2", "WE-WAPP-app", "456", "1234567"), appVersion)

        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-App", "456", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-ape", "456", "1234567"), appVersion)

        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "234", "1234567"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "344", "1234567"), appVersion)

        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "456", "1000000"), appVersion)
        assertIsLessThan(newAppVersion("app", "1.2.3", "WE-WAPP-app", "456", "1234566"), appVersion)
    }

    def 'should parse changelist properly'() {
        expect:
        '9b3bc237' == AppVersion.parseName('appName-0.1-9b3bc237.h150').changelist
        '1630379' == AppVersion.parseName('appName-0.1-1630379').changelist
        '1' == AppVersion.parseName('appName-0.1-1').changelist
        'abcdef123456789' == AppVersion.parseName('appName-0.1-abcdef123456789').changelist
    }

    boolean assertIsLessThan(AppVersion lesser, AppVersion greater) {
        lesser != greater &&
            0 > lesser.compareTo(greater) &&
            0 < greater.compareTo(lesser)
    }

    AppVersion newAppVersion(packageName, version, buildJobName, buildNumber, changelist) {
        new AppVersion(packageName: packageName, version: version, buildJobName: buildJobName,
                buildNumber: buildNumber, changelist: changelist)
    }


}
