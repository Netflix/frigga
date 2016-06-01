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
        appVersion.buildNumber == buildNumber
        appVersion.commit == commit
        appVersion.buildJobName == buildJob

        where:
        appversionString                                            | packageName | version                   | commit     | buildNumber | buildJob
        'appName-0.1-9b3bc237.h150'                                 | 'appName'   | '0.1'                     | '9b3bc237' | '150'       | null
        'appName-0.1-9b3bc237.h150'                                 | 'appName'   | '0.1'                     | '9b3bc237' | '150'       | null
        'appName-0.1b34-9b3bc237.h150'                              | 'appName'   | '0.1b34'                  | '9b3bc237' | '150'       | null
        'appName-0.1-1630379'                                       | 'appName'   | '0.1'                     | '1630379'  | null        | null
        'appName-0.1-1'                                             | 'appName'   | '0.1'                     | '1'        | null        | null
        'appName-0.1-abcd6789'                                      | 'appName'   | '0.1'                     | 'abcd6789' | null        | null
        'appName-0.1~rc.1-1630379'                                  | 'appName'   | '0.1~rc.1'                | '1630379'  | null        | null
        'appName-0.1~dev.1-9b3bc237.h150'                           | 'appName'   | '0.1~dev.1'               | '9b3bc237' | '150'       | null
        'appName-0.1~dev.3.uncommitted-h0.54f3416'                  | 'appName'   | '0.1~dev.3.uncommitted'   | '54f3416'  | '0'         | null
        'testApp-1.3.0-h196/mybuild/196'                            | 'testApp'   | '1.3.0'                   | null       | '196'       | 'mybuild'
        'testApp-1.3.0-h196.9b3bc237/mybuild/196'                   | 'testApp'   | '1.3.0'                   | '9b3bc237' | '196'       | 'mybuild'
        'testApp-1.3.0~dev.5.uncommitted-h196.9b3bc237/mybuild/196' | 'testApp'   | '1.3.0~dev.5.uncommitted' | '9b3bc237' | '196'       | 'mybuild'
        'sub-1.0.0-586499'                                          | 'sub'       | '1.0.0'                   | '586499'   | null        | null
        'sub-1.0.0-586499.h150'                                     | 'sub'       | '1.0.0'                   | '586499'   | '150'       | null
        'sub-1.0.0-586499.h150/WE-WAPP-sub/150'                     | 'sub'       | '1.0.0'                   | '586499'   | '150'       | 'WE-WAPP-sub'
        'sub-1.0.0b4-586499.h150/WE-WAPP-sub/150'                   | 'sub'       | '1.0.0b4'                 | '586499'   | '150'       | 'WE-WAPP-sub'
        'sub-1.0.0b3-586499.h150'                                   | 'sub'       | '1.0.0b3'                 | '586499'   | '150'       | null
        'sub-1.0.0B3-586499.h150'                                   | 'sub'       | '1.0.0B3'                 | '586499'   | '150'       | null
        'sub-1.2~rc.2-h78.67ee1291/WE-WAPP-sub.nxt/78'              | 'sub'       | '1.2~rc.2'                | '67ee1291' | '78'        | 'WE-WAPP-sub.nxt'
        'sub-1.2-h78.67ee1291/WE-WAPP-sub.nxt/78'                   | 'sub'       | '1.2'                     | '67ee1291' | '78'        | 'WE-WAPP-sub.nxt'
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
