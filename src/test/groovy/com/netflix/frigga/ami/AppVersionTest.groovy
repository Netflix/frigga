package com.netflix.frigga.ami

import spock.lang.Specification
import spock.lang.Unroll

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

    @Unroll("should parse #appversionString to #packageName, #version, #commit, #buildNumber, #buildJob")
    def 'test parsing'() {
        when:
        AppVersion appVersion = AppVersion.parseName(appversionString)

        then:
        appVersion.packageName == packageName
        appVersion.version == version
        appVersion.buildNumber ==  buildNumber
        appVersion.commit == commit
        appVersion.buildJobName == buildJob

        where:
        appversionString                                          | packageName    | version | commit            | buildNumber | buildJob
        'appName-0.1-9b3bc237.h150'                               | 'appName'      | '0.1'   | '9b3bc237'        | '150'       | null
        'appName-0.1-9b3bc237.h150'                               | 'appName'      | '0.1'   | '9b3bc237'        | '150'       | null
        'appName-0.1-1630379'                                     | 'appName'      | '0.1'   | '1630379'         | null        | null
        'appName-0.1-1'                                           | 'appName'      | '0.1'   | '1'               | null        | null
        'appName-0.1-abcdef123456789'                             | 'appName'      | '0.1'   | 'abcdef123456789' | null        | null
        'testApp-1.3.0-h196/buildName/196'                        | 'testApp'      | '1.3.0' | null              | '196'       | 'buildName'
        'testApp-1.3.0-h196.9b3bc237/buildName/196'               | 'testApp'      | '1.3.0' | '9b3bc237'        | '196'       | 'buildName'
        'subscriberha-1.0.0-586499'                               | 'subscriberha' | '1.0.0' | '586499'          | null        | null
        'subscriberha-1.0.0-586499.h150'                          | 'subscriberha' | '1.0.0' | '586499'          | '150'       | null
        'subscriberha-1.0.0-586499.h150/WE-WAPP-subscriberha/150' | 'subscriberha' | '1.0.0' | '586499'          | '150'       | 'WE-WAPP-subscriberha'
    }

    boolean assertIsLessThan(AppVersion lesser, AppVersion greater) {
        lesser != greater &&
            0 > lesser.compareTo(greater) &&
            0 < greater.compareTo(lesser)
    }

    AppVersion newAppVersion(packageName, version, buildJobName, buildNumber, changelist) {
        new AppVersion(packageName: packageName, version: version, buildJobName: buildJobName,
                buildNumber: buildNumber, commit: changelist)
    }


}
