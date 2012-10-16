/**
 * Copyright 2012 Netflix, Inc.
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
package com.netflix.frigga.ami;

import com.netflix.frigga.NameConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppVersion implements Comparable<AppVersion> {

    /**
     * All of these are valid:
     * subscriberha-1.0.0-586499
     * subscriberha-1.0.0-586499.h150
     * subscriberha-1.0.0-586499.h150/WE-WAPP-subscriberha/150
     */
    private static final Pattern APP_VERSION_PATTERN = Pattern.compile(
            "([" + NameConstants.NAME_HYPHEN_CHARS +
            "]+)-([0-9.]+)-([0-9]{5,7})(?:[.]h([0-9]+))?(?:\\/([-a-zA-z0-9]+)\\/([0-9]+))?");


    private String packageName;
    private String version;
    private String buildJobName;
    private String buildNumber;
    private String changelist;

    private AppVersion() {
    }

    public static AppVersion parseName(String amiName) {
        if (amiName == null) {
            return null;
        }
        Matcher matcher = APP_VERSION_PATTERN.matcher(amiName);
        if (!matcher.matches()) {
            return null;
        }
        AppVersion parsedName = new AppVersion();
        parsedName.packageName = matcher.group(1);
        parsedName.version = matcher.group(2);
        parsedName.changelist = matcher.group(3);
        parsedName.buildNumber = matcher.group(4);
        parsedName.buildJobName = matcher.group(5);
        return parsedName;
    }

    public int compareTo(AppVersion other) {
        if (this == other) { // if x.equals(y), then x.compareTo(y) should be 0
            return 0;
        }

        if (other == null) {
            return 1; // equals(null) can never be true, so compareTo(null) should never be 0
        }

        int comparison = nullSafeStringComparator(packageName, other.packageName);
        if (comparison != 0) {
            return comparison;
        }
        comparison = nullSafeStringComparator(version, other.version);
        if (comparison != 0) {
            return comparison;
        }
        comparison = nullSafeStringComparator(buildJobName, other.buildJobName);
        if (comparison != 0) {
            return comparison;
        }
        comparison = nullSafeStringComparator(buildNumber, other.buildNumber);
        if (comparison != 0) {
            return comparison;
        }
        comparison = nullSafeStringComparator(changelist, other.changelist);
        return comparison;
    }

    private int nullSafeStringComparator(final String one, final String two) {
        if (one == null && two == null) {
            return 0;
        }

        if (one == null || two == null) {
            return (one == null) ? -1 : 1;
        }

        return one.compareTo(two);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AmiName [packageName=").append(packageName).append(", version=").append(version)
                .append(", buildJobName=").append(buildJobName).append(", buildNumber=").append(buildNumber)
                .append(", changelist=").append(changelist).append("]");
        return builder.toString();
    }

    public static Pattern getAppVersionPattern() {
        return APP_VERSION_PATTERN;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildJobName() {
        return buildJobName;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getChangelist() {
        return changelist;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((buildJobName == null) ? 0 : buildJobName.hashCode());
        result = prime * result + ((buildNumber == null) ? 0 : buildNumber.hashCode());
        result = prime * result + ((changelist == null) ? 0 : changelist.hashCode());
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppVersion other = (AppVersion) obj;
        if (buildJobName == null) {
            if (other.buildJobName != null)
                return false;
        } else if (!buildJobName.equals(other.buildJobName))
            return false;
        if (buildNumber == null) {
            if (other.buildNumber != null)
                return false;
        } else if (!buildNumber.equals(other.buildNumber))
            return false;
        if (changelist == null) {
            if (other.changelist != null)
                return false;
        } else if (!changelist.equals(other.changelist))
            return false;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

}
