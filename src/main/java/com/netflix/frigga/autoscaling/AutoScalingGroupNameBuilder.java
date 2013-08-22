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
package com.netflix.frigga.autoscaling;

import com.netflix.frigga.Names;
import com.netflix.frigga.NameBuilder;
import com.netflix.frigga.NameConstants;
import com.netflix.frigga.NameValidation;

/**
 * Logic for constructing the name of a new auto scaling group in Asgard.
 */
public class AutoScalingGroupNameBuilder extends NameBuilder {

    private String appName;
    private String stack;
    private String detail;
    private String countries;
    private String devPhase;
    private String hardware;
    private String partners;
    private String revision;
    private String usedBy;
    private String redBlackSwap;
    private String zoneVar;

    /**
     * Constructs and returns the name of the auto scaling group without validation.
     *
     * @return auto scaling group name
     */
    public String buildGroupName() {
        return buildGroupName(false);
    }

    /**
     * Construct and return the name of the auto scaling group.
     *
     * @param doValidation validate the supplied parameters before constructing the name
     * @return auto scaling group name
     */
    public String buildGroupName(Boolean doValidation) {
        NameValidation.notEmpty(appName, "appName");

        if (doValidation) {
            validateNames(appName, stack, countries, devPhase, hardware, partners, revision, usedBy, redBlackSwap,
                    zoneVar);
            if (detail != null && !detail.isEmpty() && !NameValidation.checkDetail(detail)) {
                throw new IllegalArgumentException("(Use alphanumeric characters only)");
            }
        }

        // Build the labeled variables for the end of the group name.
        String labeledVars = "";
        labeledVars += generateIfSpecified(NameConstants.COUNTRIES_KEY, countries);
        labeledVars += generateIfSpecified(NameConstants.DEV_PHASE_KEY, devPhase);
        labeledVars += generateIfSpecified(NameConstants.HARDWARE_KEY, hardware);
        labeledVars += generateIfSpecified(NameConstants.PARTNERS_KEY, partners);
        labeledVars += generateIfSpecified(NameConstants.REVISION_KEY, revision);
        labeledVars += generateIfSpecified(NameConstants.USED_BY_KEY, usedBy);
        labeledVars += generateIfSpecified(NameConstants.RED_BLACK_SWAP_KEY, redBlackSwap);
        labeledVars += generateIfSpecified(NameConstants.ZONE_KEY, zoneVar);

        String result = combineAppStackDetail(appName, stack, detail) + labeledVars;

        return result;
    }

    private static void validateNames(String... names) {
        for (String name : names) {
            if (name != null && !name.isEmpty() && !NameValidation.checkName(name)) {
                throw new IllegalArgumentException("(Use alphanumeric characters only)");
            }
        }
    }

    private static String generateIfSpecified(String key, String value) {
        if (value != null && !value.isEmpty()) {
            return "-" + key + NameConstants.LABELED_VAR_SEPARATOR + value;
        }
        return "";
    }

    public static String buildNextGroupName(String asg) {
        if (asg == null) throw new IllegalArgumentException("asg name must be specified");
        Names parsed = Names.parseName(asg);
        Integer sequence = parsed.getSequence();
        Integer nextSequence = new Integer((sequence == null) ? 0 : sequence.intValue() + 1);
        if (nextSequence.intValue() >= 1000) nextSequence = new Integer(0); // Hack
        return String.format("%s-v%03d", parsed.getCluster(), nextSequence);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getDevPhase() {
        return devPhase;
    }

    public void setDevPhase(String devPhase) {
        this.devPhase = devPhase;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public String getRedBlackSwap() {
        return redBlackSwap;
    }

    public void setRedBlackSwap(String redBlackSwap) {
        this.redBlackSwap = redBlackSwap;
    }

    public String getZoneVar() {
        return zoneVar;
    }

    public void setZoneVar(String zoneVar) {
        this.zoneVar = zoneVar;
    }

}
