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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseAmiInfo {

    private static final String IMAGE_ID = "ami-[a-z0-9]{8}";
    private static final Pattern BASE_AMI_ID_PATTERN = Pattern.compile("^.*?base_ami_id=(" + IMAGE_ID + ").*?");
    private static final Pattern ANCESTOR_ID_PATTERN = Pattern.compile("^.*?ancestor_id=(" + IMAGE_ID + ").*?$");
    private static final Pattern BASE_AMI_NAME_PATTERN = Pattern.compile("^.*?base_ami_name=([^,]+).*?$");
    private static final Pattern ANCESTOR_NAME_PATTERN = Pattern.compile("^.*?ancestor_name=([^,]+).*?$");
    private static final Pattern AMI_DATE_PATTERN = Pattern.compile(".*\\-(20[0-9]{6})(\\-.*)?");

    private String baseAmiId;
    private String baseAmiName;
    private Date baseAmiDate;

    private BaseAmiInfo() {}

    public static BaseAmiInfo parseDescription(String imageDescription) {
        BaseAmiInfo info = new BaseAmiInfo();
        if (imageDescription == null) {
            return info;
        }
        info.baseAmiId = extractBaseAmiId(imageDescription);
        info.baseAmiName = extractBaseAmiName(imageDescription);
        if (info.baseAmiName != null) {
            Matcher dateMatcher = AMI_DATE_PATTERN.matcher(info.baseAmiName);
            if (dateMatcher.matches()) {
                try {
                    // Example: 20100823
                    info.baseAmiDate = new SimpleDateFormat("yyyyMMdd").parse(dateMatcher.group(1));
                } catch (Exception ignored) {
                    // Ignore failure.
                }
            }
        }
        return info;
    }

    private static String extractBaseAmiId(String imageDescription) {
        // base_ami_id=ami-1eb75c77,base_ami_name=servicenet-roku-qadd.dc.81210.10.44
        Matcher matcher = BASE_AMI_ID_PATTERN.matcher(imageDescription);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        // store=ebs,ancestor_name=ebs-centosbase-x86_64-20101124,ancestor_id=ami-7b4eb912
        matcher = ANCESTOR_ID_PATTERN.matcher(imageDescription);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String extractBaseAmiName(String imageDescription) {
        // base_ami_id=ami-1eb75c77,base_ami_name=servicenet-roku-qadd.dc.81210.10.44
        Matcher matcher = BASE_AMI_NAME_PATTERN.matcher(imageDescription);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        // store=ebs,ancestor_name=ebs-centosbase-x86_64-20101124,ancestor_id=ami-7b4eb912
        matcher = ANCESTOR_NAME_PATTERN.matcher(imageDescription);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    public String getBaseAmiId() {
        return baseAmiId;
    }

    public String getBaseAmiName() {
        return baseAmiName;
    }

    public Date getBaseAmiDate() {
        return baseAmiDate != null ? (Date) baseAmiDate.clone() : null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baseAmiDate == null) ? 0 : baseAmiDate.hashCode());
        result = prime * result + ((baseAmiId == null) ? 0 : baseAmiId.hashCode());
        result = prime * result + ((baseAmiName == null) ? 0 : baseAmiName.hashCode());
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
        BaseAmiInfo other = (BaseAmiInfo) obj;
        if (baseAmiDate == null) {
            if (other.baseAmiDate != null)
                return false;
        } else if (!baseAmiDate.equals(other.baseAmiDate))
            return false;
        if (baseAmiId == null) {
            if (other.baseAmiId != null)
                return false;
        } else if (!baseAmiId.equals(other.baseAmiId))
            return false;
        if (baseAmiName == null) {
            if (other.baseAmiName != null)
                return false;
        } else if (!baseAmiName.equals(other.baseAmiName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BaseAmiInfo [baseAmiId=" + baseAmiId + ", baseAmiName=" + baseAmiName + ", baseAmiDate=" + baseAmiDate
                + "]";
    }

}
